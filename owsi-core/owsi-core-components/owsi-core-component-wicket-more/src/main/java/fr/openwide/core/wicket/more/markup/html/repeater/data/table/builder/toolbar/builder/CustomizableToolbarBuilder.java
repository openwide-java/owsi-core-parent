package fr.openwide.core.wicket.more.markup.html.repeater.data.table.builder.toolbar.builder;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import com.google.common.collect.Lists;

import fr.openwide.core.jpa.more.business.sort.ISort;
import fr.openwide.core.wicket.markup.html.basic.CoreLabel;
import fr.openwide.core.wicket.more.condition.Condition;
import fr.openwide.core.wicket.more.markup.html.factory.AbstractParameterizedComponentFactory;
import fr.openwide.core.wicket.more.markup.html.factory.IOneParameterComponentFactory;
import fr.openwide.core.wicket.more.markup.html.repeater.data.table.CoreDataTable;
import fr.openwide.core.wicket.more.markup.html.repeater.data.table.builder.DataTableBuilder;
import fr.openwide.core.wicket.more.markup.html.repeater.data.table.builder.toolbar.CoreCustomizableToolbar;
import fr.openwide.core.wicket.more.markup.html.repeater.data.table.builder.toolbar.CustomizableToolbarElementFactory;
import fr.openwide.core.wicket.more.markup.html.repeater.data.table.builder.toolbar.state.IAddedToolbarCoreElementState;
import fr.openwide.core.wicket.more.markup.html.repeater.data.table.builder.toolbar.state.IAddedToolbarElementState;
import fr.openwide.core.wicket.more.markup.html.repeater.data.table.builder.toolbar.state.IAddedToolbarLabelElementState;
import fr.openwide.core.wicket.more.markup.html.repeater.data.table.builder.toolbar.state.IToolbarElementState;
import fr.openwide.core.wicket.more.util.model.Detachables;

public class CustomizableToolbarBuilder<T, S extends ISort<?>> implements IToolbarElementState<T, S> {

	private final DataTableBuilder<T, S> dataTableBuilder;

	private final List<CustomizableToolbarElementFactory<T, S>> factories = Lists.newArrayList();

	public CustomizableToolbarBuilder(DataTableBuilder<T, S> dataTableBuilder) {
		super();
		this.dataTableBuilder = dataTableBuilder;
	}

	private abstract class CustomizableToolbarBuilderWrapper implements IToolbarElementState<T, S> {
		@Override
		public IAddedToolbarLabelElementState<T, S> addLabel(IModel<String> model) {
			return CustomizableToolbarBuilder.this.addLabel(model);
		}
		@Override
		public IAddedToolbarCoreElementState<T, S> addComponent(IOneParameterComponentFactory<Component, CoreDataTable<T, S>> delegateFactory) {
			return CustomizableToolbarBuilder.this.addComponent(delegateFactory);
		}
		@Override
		public DataTableBuilder<T, S> end() {
			return CustomizableToolbarBuilder.this.end();
		}
	}

	private abstract class AddedToolbarElementState<NextState extends IAddedToolbarElementState<T, S>> extends CustomizableToolbarBuilderWrapper implements IAddedToolbarElementState<T, S> {
		
		protected abstract CustomizableToolbarElementFactory<T, S> getFactory();
		
		protected abstract NextState getNextState();
		
		@Override
		public NextState colspan(long colspan) {
			getFactory().colspan(colspan);
			return getNextState();
		}
		
		@Override
		public NextState full() {
			getFactory().full();
			return getNextState();
		}
		
		@Override
		public NextState when(Condition condition) {
			getFactory().when(condition);
			return getNextState();
		}
	}

	private abstract class AddedToolbarCoreElementState<NextState extends IAddedToolbarCoreElementState<T, S>> extends AddedToolbarElementState<NextState>
			implements IAddedToolbarCoreElementState<T, S> {
		@Override
		public NextState withClass(String cssClass) {
			getFactory().withClass(cssClass);
			return getNextState();
		}
	}
	
	private class AddedToolbarLabelElementState extends AddedToolbarCoreElementState<IAddedToolbarLabelElementState<T, S>> implements IAddedToolbarLabelElementState<T, S> {
		
		private final CustomizableToolbarElementFactory<T, S> factory;
		
		public AddedToolbarLabelElementState(CustomizableToolbarElementFactory<T, S> factory) {
			super();
			this.factory = factory;
		}
		
		@Override
		protected CustomizableToolbarElementFactory<T, S> getFactory() {
			return factory;
		}
		
		@Override
		protected IAddedToolbarLabelElementState<T, S> getNextState() {
			return this;
		}
	}
	
	@Override
	public IAddedToolbarLabelElementState<T, S> addLabel(final IModel<String> model) {
		CustomizableToolbarElementFactory<T, S> factory = new CustomizableToolbarElementFactory<T, S>(
				new CustomizableToolbarLabelElementDelegateFactory<T, S>(model)
		);
		factories.add(factory);
		return new AddedToolbarLabelElementState(factory);
	}

	public static class CustomizableToolbarLabelElementDelegateFactory<T, S extends ISort<?>> extends AbstractParameterizedComponentFactory<Component, CoreDataTable<T, S>> {
		private static final long serialVersionUID = 1L;
		
		private final IModel<String> model;
		
		public CustomizableToolbarLabelElementDelegateFactory(IModel<String> model) {
			super();
			this.model = model;
		}
		
		@Override
		public Component create(String wicketId, CoreDataTable<T, S> parameter) {
			return new CoreLabel(wicketId, model);
		}
		
		@Override
		public void detach() {
			super.detach();
			Detachables.detach(model);
		}
	}

	@Override
	public IAddedToolbarCoreElementState<T, S> addComponent(IOneParameterComponentFactory<Component, CoreDataTable<T, S>> delegateFactory) {
		final CustomizableToolbarElementFactory<T, S> factory = new CustomizableToolbarElementFactory<T, S>(delegateFactory);
		factories.add(factory);
		return new AddedToolbarCoreElementState<IAddedToolbarCoreElementState<T, S>>() {
			@Override
			protected CustomizableToolbarElementFactory<T, S> getFactory() {
				return factory;
			}
			@Override
			protected IAddedToolbarCoreElementState<T, S> getNextState() {
				return this;
			}
		};
	}

	@Override
	public DataTableBuilder<T, S> end() {
		return dataTableBuilder;
	}

	public CoreCustomizableToolbar<T, S> build(CoreDataTable<T, S> dataTable) {
		return new CoreCustomizableToolbar<T, S>(dataTable, factories);
	}

}