package fr.openwide.core.jpa.more.business.filereference.util;


import fr.openwide.core.jpa.more.business.filereference.model.AbstractFileReference;
import fr.openwide.core.jpa.more.business.filereference.model.IFileReferenceType;
import fr.openwide.core.jpa.more.util.transaction.model.ITransactionSynchronizationTaskRollbackAware;

public class AbstractFileReferenceCreateRollbackTask<F extends AbstractFileReference<E>, E extends Enum<E> & IFileReferenceType<E>> extends AbstractFileReferenceTransactionSynchronizationTask<F, E>
		implements ITransactionSynchronizationTaskRollbackAware {

	private String key;

	private String extension;
	
	private E type;

	public AbstractFileReferenceCreateRollbackTask(String key, String extension, E type) {
		this.key = key;
		this.extension = extension;
		this.type = type;
	}
	
	@Override
	public void run() throws Exception {
		// Nothing to do, see afterRollback()
	}
	
	@Override
	public void afterRollback() throws Exception {
		removeFileIfPresent(key, extension, type);
	}
}
