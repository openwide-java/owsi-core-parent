package fr.openwide.core.wicket.more.console.maintenance.queuemanager.component;

import java.util.Collection;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Functions;

import fr.openwide.core.commons.util.functional.Predicates2;
import fr.openwide.core.infinispan.model.INode;
import fr.openwide.core.infinispan.service.IInfinispanClusterService;
import fr.openwide.core.jpa.more.business.sort.ISort;
import fr.openwide.core.jpa.more.business.task.service.IQueuedTaskHolderManager;
import fr.openwide.core.jpa.more.infinispan.action.SwitchStatusQueueTaskManagerResult;
import fr.openwide.core.jpa.more.infinispan.model.QueueTaskManagerStatus;
import fr.openwide.core.jpa.more.infinispan.model.TaskQueueStatus;
import fr.openwide.core.jpa.more.infinispan.service.IInfinispanQueueTaskManagerService;
import fr.openwide.core.wicket.markup.html.basic.CoreLabel;
import fr.openwide.core.wicket.more.condition.Condition;
import fr.openwide.core.wicket.more.console.maintenance.infinispan.renderer.INodeRenderer;
import fr.openwide.core.wicket.more.console.maintenance.queuemanager.renderer.QueueManagerRenderer;
import fr.openwide.core.wicket.more.console.maintenance.queuemanager.renderer.QueueTaskRenderer;
import fr.openwide.core.wicket.more.markup.html.bootstrap.label.component.BootstrapBadge;
import fr.openwide.core.wicket.more.markup.html.bootstrap.label.component.BootstrapLabel;
import fr.openwide.core.wicket.more.markup.html.factory.AbstractComponentFactory;
import fr.openwide.core.wicket.more.markup.html.feedback.FeedbackUtils;
import fr.openwide.core.wicket.more.markup.repeater.collection.CollectionView;
import fr.openwide.core.wicket.more.markup.repeater.table.DecoratedCoreDataTablePanel.AddInPlacement;
import fr.openwide.core.wicket.more.markup.repeater.table.builder.DataTableBuilder;
import fr.openwide.core.wicket.more.markup.repeater.table.column.AbstractCoreColumn;
import fr.openwide.core.wicket.more.model.BindingModel;
import fr.openwide.core.wicket.more.model.ReadOnlyCollectionModel;
import fr.openwide.core.wicket.more.util.binding.CoreWicketMoreBindings;
import fr.openwide.core.wicket.more.util.model.Detachables;
import fr.openwide.core.wicket.more.util.model.Models;

public class ConsoleMaintenanceQueueManagerCacheInfinispanPanel extends Panel {

	private static final long serialVersionUID = -8384901751717369676L;

	public static final Logger LOGGER = LoggerFactory.getLogger(ConsoleMaintenanceQueueManagerCacheInfinispanPanel.class);

	@SpringBean
	private IInfinispanClusterService infinispanClusterService;

	@SpringBean
	private IInfinispanQueueTaskManagerService infinispanQueueTaskManagerService;

	private final IModel<Boolean> queueTaskManagerStatusModel;

	public ConsoleMaintenanceQueueManagerCacheInfinispanPanel(String id) {
		super(id);
		setOutputMarkupId(true);
		
		queueTaskManagerStatusModel = new LoadableDetachableModel<Boolean>() {
			private static final long serialVersionUID = 1L;
			@Override
			protected Boolean load() {
				return infinispanQueueTaskManagerService.isOneQueueTaskManagerUp();
			}
		};
		
		add(
				new AjaxLink<Void>("emptyCache") {
					private static final long serialVersionUID = 1L;
					
					@Override
					public void onClick(AjaxRequestTarget target) {
						Integer nbTasksCleared = infinispanQueueTaskManagerService.clearCache();
						getSession().success(new StringResourceModel("console.maintenance.queuemanager.actions.emptyCache.confirm")
								.setParameters(nbTasksCleared).getObject());
						FeedbackUtils.refreshFeedback(target, getPage());
					}
					
				}.add(Condition.predicate(queueTaskManagerStatusModel, Predicates2.isTrue()).thenDisable())
		);

	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		Detachables.detach(queueTaskManagerStatusModel);
	}
}
