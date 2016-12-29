package fr.openwide.core.jpa.more.business.filereference.util;

import fr.openwide.core.jpa.more.business.filereference.model.AbstractFileReference;
import fr.openwide.core.jpa.more.business.filereference.model.IFileReferenceType;


public class AbstractFileReferenceDeleteAfterCommitTask<F extends AbstractFileReference<E>, E extends Enum<E> & IFileReferenceType<E>> extends AbstractFileReferenceTransactionSynchronizationTask<F, E> {

	private String key;

	private String extension;
	
	private E type;

	public AbstractFileReferenceDeleteAfterCommitTask(String key, String extension, E type) {
		this.key = key;
		this.extension = extension;
		this.type = type;
	}

	@Override
	public void run() throws Exception {
		removeFileIfPresent(key, extension, type);
	}
}
