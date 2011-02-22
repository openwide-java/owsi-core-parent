package fr.openwide.core.hibernate.more.business.file.model;

import java.io.File;

import fr.openwide.core.hibernate.exception.SecurityServiceException;
import fr.openwide.core.hibernate.exception.ServiceException;

public interface FileStore {
	
	String getKey();
	
	void check() throws IllegalStateException;

	void addFile(File file, String fileKey, String extension)
			throws ServiceException, SecurityServiceException;

	File getFile(String fileKey, String extension);

	void removeFile(String fileKey, String extension);

}
