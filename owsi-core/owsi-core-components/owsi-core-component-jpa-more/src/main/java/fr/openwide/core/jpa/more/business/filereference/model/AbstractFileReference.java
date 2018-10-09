package fr.openwide.core.jpa.more.business.filereference.model;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import fr.openwide.core.jpa.business.generic.model.GenericEntity;
import fr.openwide.core.spring.util.StringUtils;

@MappedSuperclass
public abstract class AbstractFileReference<E extends Enum<E>> extends GenericEntity<Long, AbstractFileReference<E>> {

	private static final long serialVersionUID = 203810980660040474L;

	@Basic(optional = false)
	private String fileName;

	@Basic(optional = false)
	private String extension;

	@Basic(optional = false)
	private long fileSize;

	protected AbstractFileReference() {
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public abstract E getType();
	
	@Transient
	public String getNameAndExtension() {
		StringBuilder builder = new StringBuilder();
		if (StringUtils.hasText(fileName)) {
			builder.append(fileName);
		}
		if (StringUtils.hasText(extension)) {
			if (builder.length() > 0) {
				builder.append(".");
			}
			builder.append(extension);
		}
		return builder.toString();
	}

	@Override
	public String getNameForToString() {
		return getNameAndExtension();
	}

	@Override
	public String getDisplayName() {
		return getNameForToString();
	}
}
