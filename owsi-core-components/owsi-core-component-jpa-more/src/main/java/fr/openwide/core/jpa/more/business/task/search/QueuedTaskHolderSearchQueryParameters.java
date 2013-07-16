package fr.openwide.core.jpa.more.business.task.search;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import fr.openwide.core.commons.util.CloneUtils;
import fr.openwide.core.jpa.more.business.task.util.TaskStatus;

public class QueuedTaskHolderSearchQueryParameters implements Serializable {

	private static final long serialVersionUID = 123152452275164563L;

	private String name;

	private List<TaskStatus> statuses;
	
	private String taskType;

	private Date creationDate;

	private Date startDate;

	private Date endDate;

	public QueuedTaskHolderSearchQueryParameters() {
	}

	public QueuedTaskHolderSearchQueryParameters(String name, Collection<TaskStatus> statuses, String taskType,
			Date creationDate, Date startDate, Date endDate) {
		setName(name);
		setTaskType(taskType);
		setStatuses(statuses);
		setCreationDate(creationDate);
		setStartDate(startDate);
		setEndDate(endDate);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TaskStatus> getStatuses() {
		return statuses;
	}

	public void setStatuses(Collection<TaskStatus> statuses) {
		if (statuses != null) {
			this.statuses = Lists.newArrayList(statuses);
		}
	}
	
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = CloneUtils.clone(creationDate);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = CloneUtils.clone(startDate);
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date completionDate) {
		this.endDate = CloneUtils.clone(completionDate);
	}

}