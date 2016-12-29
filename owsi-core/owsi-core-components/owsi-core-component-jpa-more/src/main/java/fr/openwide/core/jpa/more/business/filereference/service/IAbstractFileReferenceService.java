package fr.openwide.core.jpa.more.business.filereference.service;

import java.io.File;

import javax.activation.DataSource;

import fr.openwide.core.jpa.business.generic.service.IGenericEntityService;
import fr.openwide.core.jpa.exception.SecurityServiceException;
import fr.openwide.core.jpa.exception.ServiceException;
import fr.openwide.core.jpa.more.business.filereference.model.AbstractFileReference;
import fr.openwide.core.jpa.more.business.filereference.model.IFileReferenceType;

public interface IAbstractFileReferenceService<F extends AbstractFileReference<E>, E extends Enum<E> & IFileReferenceType<E>> extends IGenericEntityService<Long, F> {

	F create(DataSource dataSource, E type) throws ServiceException, SecurityServiceException;

	@Override
	void delete(F fileReference) throws ServiceException, SecurityServiceException;

	File getFile(F fileReference);

	File getFile(E type, String key, String extension);

	void removeFileReference(E type, String key, String extension);

	String getFileKey(F fileReference);
	
	F copyFileReference(final F fileReference) throws ServiceException, SecurityServiceException;
}
