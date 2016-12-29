package fr.openwide.core.jpa.more.business.filereference.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.activation.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.google.common.collect.Maps;

import fr.openwide.core.commons.util.mime.MediaType;
import fr.openwide.core.jpa.business.generic.dao.IGenericEntityDao;
import fr.openwide.core.jpa.business.generic.service.GenericEntityServiceImpl;
import fr.openwide.core.jpa.exception.SecurityServiceException;
import fr.openwide.core.jpa.exception.ServiceException;
import fr.openwide.core.jpa.more.business.file.model.FileInformation;
import fr.openwide.core.jpa.more.business.file.model.IFileStore;
import fr.openwide.core.jpa.more.business.file.model.SimpleFileStoreImpl;
import fr.openwide.core.jpa.more.business.file.model.path.HashTableFileStorePathGeneratorImpl;
import fr.openwide.core.jpa.more.business.file.model.path.SimpleFileStorePathGeneratorImpl;
import fr.openwide.core.jpa.more.business.filereference.model.AbstractFileReference;
import fr.openwide.core.jpa.more.business.filereference.model.IFileReferenceType;
import fr.openwide.core.jpa.more.business.filereference.util.AbstractFileReferenceCreateRollbackTask;
import fr.openwide.core.jpa.more.business.filereference.util.AbstractFileReferenceDeleteAfterCommitTask;
import fr.openwide.core.jpa.more.util.transaction.service.ITransactionSynchronizationTaskManagerService;
import fr.openwide.core.spring.property.SpringPropertyIds;
import fr.openwide.core.spring.property.service.IPropertyService;
import fr.openwide.core.spring.util.SpringBeanUtils;

public abstract class AbstractFileReferenceServiceImpl<F extends AbstractFileReference<E>, E extends Enum<E> & IFileReferenceType<E>> extends GenericEntityServiceImpl<Long, F> implements IAbstractFileReferenceService<F, E> {
	
	private static final int FILESTORES_HASH_SIZE_IN_BYTES = 1;
	
	@Autowired
	private ITransactionSynchronizationTaskManagerService transactionSynchronizationTaskManagerService;
	
	private Map<E, IFileStore> filesStore = Maps.newHashMap();
	
	@Autowired
	private IPropertyService propertyService;
	
	public AbstractFileReferenceServiceImpl(IGenericEntityDao<Long, F> dao, IPropertyService propertyService, ApplicationContext context, Class<E> enumClass) {
		super(dao);
		init(propertyService, context, enumClass);
	}
	
	protected int getFilestoreHashSizeInBytes() {
		return FILESTORES_HASH_SIZE_IN_BYTES;
	}
	
	protected void init(IPropertyService propertyService, ApplicationContext context, Class<E> enumClass) {
		SimpleFileStorePathGeneratorImpl fileStorePathGenerator = new HashTableFileStorePathGeneratorImpl(getFilestoreHashSizeInBytes());
		
		for (E type : enumClass.getEnumConstants()) {
			getFilesStore().put(type, new SimpleFileStoreImpl(
					type.name(),
					FilenameUtils.concat(getRootPathDirectory(), type.getDirectoryName()),
					fileStorePathGenerator, true
			));
		}
		
		for (IFileStore fichierFileStore : getFilesStore().values()) {
			SpringBeanUtils.autowireBean(context, fichierFileStore);
			fichierFileStore.check();
		}
	}
	
	protected Map<E, IFileStore> getFilesStore() {
		return filesStore;
	}
	
	protected abstract String getRootPathDirectory();
	
	protected abstract F getNewInstance(E type);
	
	protected abstract void checkMediaType(E type, DataSource dataSource);
	
	@Override
	public F create(DataSource dataSource, E type) throws ServiceException, SecurityServiceException {
		checkMediaType(type, dataSource);
		
		F fileReference = getNewInstance(type);
		fileReference.setFileName(FilenameUtils.getBaseName(dataSource.getName()));
		fileReference.setExtension(FilenameUtils.getExtension(dataSource.getName()));
		create(fileReference);
		
		IFileStore fileStore = filesStore.get(type);
		
		try (InputStream inputStream = dataSource.getInputStream()) {
			FileInformation fileInformation = fileStore.addFile(
					inputStream,
					getFileKey(fileReference),
					fileReference.getExtension()
			);
			fileReference.setFileSize(fileInformation.getSize());
			update(fileReference);
		} catch (IOException e) {
			throw new ServiceException(String.format("Error creating file '%1$s'", dataSource.getName()), e);
		}
		
		transactionSynchronizationTaskManagerService.push(new AbstractFileReferenceCreateRollbackTask<F, E>(getFileKey(fileReference),
				fileReference.getExtension(), type));
		
		return fileReference;
	}

	@Override
	public void delete(F fileReference) throws ServiceException, SecurityServiceException {
		String key = getFileKey(fileReference);
		String extension = fileReference.getExtension();
		E type = fileReference.getType();
		
		super.delete(fileReference);
		
		transactionSynchronizationTaskManagerService.push(new AbstractFileReferenceDeleteAfterCommitTask<F, E>(key,
				extension, type));
	}

	@Override
	public File getFile(F fileReference) {
		if (fileReference != null) {
			IFileStore fileStore = filesStore.get(fileReference.getType());
			return fileStore.getFile(getFileKey(fileReference), fileReference.getExtension());
		} else {
			return null;
		}
	}

	@Override
	public File getFile(E type, String key, String extension) {
		IFileStore fileStore = filesStore.get(type);
		
		return fileStore.getFile(key, extension);
	}

	@Override
	public void removeFileReference(E type, String key, String extension) {
		IFileStore fileStore = filesStore.get(type);
		
		fileStore.removeFile(key, extension);
	}

	@Override
	public String getFileKey(F fileReference) {
		return fileReference.getId() != null ? String.valueOf(fileReference.getId()) : null;
	}
	
	@Override
	public F copyFileReference(final F fileReference) throws ServiceException, SecurityServiceException {
		F newMKPFile = null;
		final String mkpFileName;
		final String mkpFileExtension;
		final File file = getFile(fileReference);
		final File newFile;
		
		if (fileReference != null) {
			try {
				newFile = File.createTempFile(fileReference.getFileName(), fileReference.getExtension(), propertyService.get(SpringPropertyIds.TMP_PATH));
				FileUtils.copyFile(file, newFile);
			} catch (IOException e) {
				throw new ServiceException("Error cloning file", e);
			}
		
			mkpFileName = fileReference.getNameAndExtension();
			mkpFileExtension = fileReference.getExtension();
			
			DataSource dataSource = new DataSource() {
				@Override
				public OutputStream getOutputStream() throws IOException {
					return new FileOutputStream(newFile);
				}
				@Override
				public String getName() {
					return mkpFileName;
				}
				@Override
				public InputStream getInputStream() throws IOException {
					return new FileInputStream(newFile);
				}
				@Override
				public String getContentType() {
					return MediaType.fromExtension(mkpFileExtension).mime();
				}
			};
			newMKPFile = create(dataSource, fileReference.getType());
		}
		
		return newMKPFile;
	}
}
