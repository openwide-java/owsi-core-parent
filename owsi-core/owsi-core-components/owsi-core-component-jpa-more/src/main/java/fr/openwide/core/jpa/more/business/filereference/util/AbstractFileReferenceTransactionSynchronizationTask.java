package fr.openwide.core.jpa.more.business.filereference.util;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import fr.openwide.core.jpa.more.business.filereference.model.AbstractFileReference;
import fr.openwide.core.jpa.more.business.filereference.model.IFileReferenceType;
import fr.openwide.core.jpa.more.business.filereference.service.IAbstractFileReferenceService;
import fr.openwide.core.jpa.more.util.transaction.model.ITransactionSynchronizationAfterCommitTask;
import fr.openwide.core.spring.util.StringUtils;

public abstract class AbstractFileReferenceTransactionSynchronizationTask<F extends AbstractFileReference<E>, E extends Enum<E> & IFileReferenceType<E>> implements ITransactionSynchronizationAfterCommitTask {

	@Autowired
	private IAbstractFileReferenceService<F, E> fileReferenceService;

	protected final void removeFileIfPresent(String key, String extension, E type) {
		if (StringUtils.hasText(key) && StringUtils.hasText(extension)) {
			File file = fileReferenceService.getFile(type, key, extension);
			if (file != null && file.exists()) {
				fileReferenceService.removeFileReference(type, key, extension);
			}
		}
	}
}
