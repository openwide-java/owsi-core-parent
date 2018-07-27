package fr.openwide.core.jpa.more.infinispan.service;

import java.util.List;

import fr.openwide.core.infinispan.model.INode;
import fr.openwide.core.jpa.more.infinispan.action.SwitchStatusQueueTaskManagerResult;
import fr.openwide.core.jpa.more.infinispan.model.QueueTaskManagerStatus;

public interface IInfinispanQueueTaskManagerService {
	
	Boolean isOneQueueTaskManagerUp();

	QueueTaskManagerStatus getQueueTaskManagerStatus(INode node);

	QueueTaskManagerStatus createQueueTaskManagerStatus();

	SwitchStatusQueueTaskManagerResult start();

	SwitchStatusQueueTaskManagerResult stop();

	SwitchStatusQueueTaskManagerResult startQueueManager(INode node);

	SwitchStatusQueueTaskManagerResult stopQueueManager(INode node);
	
	Integer clearCache();

}
