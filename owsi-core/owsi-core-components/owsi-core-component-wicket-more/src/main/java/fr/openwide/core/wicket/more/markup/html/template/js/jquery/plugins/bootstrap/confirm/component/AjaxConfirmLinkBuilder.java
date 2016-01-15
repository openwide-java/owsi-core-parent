package fr.openwide.core.wicket.more.markup.html.template.js.jquery.plugins.bootstrap.confirm.component;

import java.util.Objects;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;

import fr.openwide.core.commons.util.functional.SerializableFunction;
import fr.openwide.core.jpa.business.generic.model.GenericEntity;
import fr.openwide.core.wicket.more.markup.html.action.AbstractOneParameterAjaxAction;
import fr.openwide.core.wicket.more.markup.html.action.IOneParameterAjaxAction;
import fr.openwide.core.wicket.more.markup.html.factory.AbstractOneParameterModelFactory;
import fr.openwide.core.wicket.more.markup.html.factory.IOneParameterModelFactory;
import fr.openwide.core.wicket.more.markup.html.factory.OneParameterModelFactory;
import fr.openwide.core.wicket.more.markup.html.template.js.jquery.plugins.bootstrap.confirm.fluid.IAjaxConfirmLinkBuilderStepContent;
import fr.openwide.core.wicket.more.markup.html.template.js.jquery.plugins.bootstrap.confirm.fluid.IAjaxConfirmLinkBuilderStepEndContent;
import fr.openwide.core.wicket.more.markup.html.template.js.jquery.plugins.bootstrap.confirm.fluid.IAjaxConfirmLinkBuilderStepNo;
import fr.openwide.core.wicket.more.markup.html.template.js.jquery.plugins.bootstrap.confirm.fluid.IAjaxConfirmLinkBuilderStepOnclick;
import fr.openwide.core.wicket.more.markup.html.template.js.jquery.plugins.bootstrap.confirm.fluid.IAjaxConfirmLinkBuilderStepStart;
import fr.openwide.core.wicket.more.markup.html.template.js.jquery.plugins.bootstrap.confirm.fluid.IAjaxConfirmLinkBuilderStepTerminal;
import fr.openwide.core.wicket.more.markup.html.template.js.jquery.plugins.bootstrap.confirm.util.AjaxResponseAction;
import fr.openwide.core.wicket.more.util.model.Detachables;

public class AjaxConfirmLinkBuilder<O> implements IAjaxConfirmLinkBuilderStepStart<O>, IAjaxConfirmLinkBuilderStepContent<O>,
		IAjaxConfirmLinkBuilderStepEndContent<O>, IAjaxConfirmLinkBuilderStepNo<O>, IAjaxConfirmLinkBuilderStepOnclick<O>,
		IAjaxConfirmLinkBuilderStepTerminal<O> {

	private static final long serialVersionUID = 365949870142796149L;

	@Deprecated
	private String wicketId;
	
	@Deprecated
	private IModel<O> model;
	
	private IOneParameterModelFactory<IModel<O>, String> titleModelFactory;
	
	private IOneParameterModelFactory<IModel<O>, String> contentModelFactory;
	
	private IModel<String> yesLabelModel;
	
	private IModel<String> noLabelModel;
	
	private IModel<String> cssClassNamesModel;
	
	private boolean keepMarkup = false;
	
	private IOneParameterAjaxAction<IModel<O>> onClick;
	
	protected AjaxConfirmLinkBuilder() {
	}
	
	/**
	 * @deprecated Use {@link #AjaxConfirmLinkBuilder()} instead.
	 */
	@Deprecated
	protected AjaxConfirmLinkBuilder(String wicketId, IModel<O> model) {
		this.wicketId = wicketId;
		this.model = model;
	}
	
	@Override
	public IAjaxConfirmLinkBuilderStepContent<O> title(IModel<String> titleModel) {
		return title(OneParameterModelFactory.<IModel<O>, String>identity(titleModel));
	}
	
	@Override
	public IAjaxConfirmLinkBuilderStepContent<O> title(IOneParameterModelFactory<IModel<O>, String> titleModelFactory) {
		this.titleModelFactory = titleModelFactory;
		return this;
	}
	
	@Override
	public IAjaxConfirmLinkBuilderStepEndContent<O> content(IModel<String> contentModel) {
		return content(OneParameterModelFactory.<IModel<O>, String>identity(contentModel));
	}
	
	@Override
	public IAjaxConfirmLinkBuilderStepEndContent<O> content(IOneParameterModelFactory<IModel<O>, String> contentModelFactory) {
		this.contentModelFactory = contentModelFactory;
		return this;
	}
	
	@Override
	public IAjaxConfirmLinkBuilderStepEndContent<O> cssClassNamesModel(IModel<String> cssClassNamesModel) {
		this.cssClassNamesModel = cssClassNamesModel;
		return this;
	}
	
	@Override
	public IAjaxConfirmLinkBuilderStepOnclick<O> deleteConfirmation() {
		confirm();
		title(new ResourceModel("common.confirmTitle"));
		content(new AbstractOneParameterModelFactory<IModel<O>, String>() {
			private static final long serialVersionUID = 1L;
			@Override
			public IModel<String> create(IModel<O> parameter) {
				if (parameter != null && parameter.getObject() instanceof GenericEntity<?, ?>) {
					GenericEntity<?, ?> genericEntity = (GenericEntity<?, ?>) model.getObject();
					return new StringResourceModel("common.deleteConfirmation.object").setParameters(genericEntity.getDisplayName());
				} else {
					return new ResourceModel("common.deleteConfirmation");
				}
			}
		});
		return this;
	}

	@Override
	public IAjaxConfirmLinkBuilderStepEndContent<O> keepMarkup() {
		this.keepMarkup = true;
		return this;
	}

	@Override
	public IAjaxConfirmLinkBuilderStepNo<O> yes(IModel<String> yesLabelModel) {
		this.yesLabelModel = yesLabelModel;
		return this;
	}
	
	@Override
	public IAjaxConfirmLinkBuilderStepOnclick<O> no(IModel<String> noLabelModel) {
		this.noLabelModel = noLabelModel;
		return this;
	}

	@Override
	public IAjaxConfirmLinkBuilderStepOnclick<O> yesNo() {
		this.yesLabelModel = new ResourceModel("common.yes");
		this.noLabelModel = new ResourceModel("common.no");
		return this;
	}

	@Override
	public IAjaxConfirmLinkBuilderStepOnclick<O> confirm() {
		this.yesLabelModel = new ResourceModel("common.confirm");
		this.noLabelModel = new ResourceModel("common.cancel");
		return this;
	}

	@Override
	public IAjaxConfirmLinkBuilderStepOnclick<O> validate() {
		this.yesLabelModel = new ResourceModel("common.validate");
		this.noLabelModel = new ResourceModel("common.cancel");
		return this;
	}

	@Override
	public IAjaxConfirmLinkBuilderStepOnclick<O> save() {
		this.yesLabelModel = new ResourceModel("common.save");
		this.noLabelModel = new ResourceModel("common.cancel");
		return this;
	}

	/**
	 * @deprecated Use {@link #onClick(IOneParameterAjaxAction)} instead.
	 */
	@Deprecated
	@Override
	public IAjaxConfirmLinkBuilderStepTerminal<O> onClick(final SerializableFunction<AjaxRequestTarget, Void> onClick) {
		this.onClick = new FunctionAjaxOneParameterAction<>(onClick);
		return this;
	}
	
	@Deprecated
	private static final class FunctionAjaxOneParameterAction<O> extends AbstractOneParameterAjaxAction<IModel<O>> {
		private static final long serialVersionUID = 1L;
		
		private final SerializableFunction<AjaxRequestTarget, Void> function;
		
		public FunctionAjaxOneParameterAction(SerializableFunction<AjaxRequestTarget, Void> function) {
			super();
			this.function = function;
		}
		
		@Override
		public void execute(AjaxRequestTarget target, IModel<O> parameter) {
			function.apply(target);
		}
	}

	@Override
	public IAjaxConfirmLinkBuilderStepTerminal<O> onClick(final AjaxResponseAction onClick) {
		Objects.requireNonNull(onClick);
		this.onClick = new AjaxResponseActionAjaxOneParameterAction<>(onClick);
		return this;
	}

	@Deprecated
	private static final class AjaxResponseActionAjaxOneParameterAction<O> extends AbstractOneParameterAjaxAction<IModel<O>> {
		private static final long serialVersionUID = 1L;
		
		private final AjaxResponseAction onClick;
		
		public AjaxResponseActionAjaxOneParameterAction(AjaxResponseAction onClick) {
			super();
			this.onClick = Objects.requireNonNull(onClick);
		}
		
		@Override
		public void execute(AjaxRequestTarget target, IModel<O> parameter) {
			onClick.execute(target);
		}
		
		@Override
		public void detach() {
			super.detach();
			onClick.detach();
		}
	}

	@Override
	public IAjaxConfirmLinkBuilderStepTerminal<O> onClick(IOneParameterAjaxAction<IModel<O>> onClick) {
		this.onClick = onClick;
		return this;
	}

	/**
	 * @deprecated Use {@link #create(String, IModel)} instead.
	 */
	@Deprecated
	@Override
	public AjaxConfirmLink<O> create() {
		return create(wicketId, model);
	}
	
	@Override
	public AjaxConfirmLink<O> create(String wicketId, IModel<O> model) {
		return new FunctionalAjaxConfirmLink<O>(wicketId, model, titleModelFactory, contentModelFactory,
				yesLabelModel, noLabelModel, cssClassNamesModel, keepMarkup, onClick);
	}
	
	private static class FunctionalAjaxConfirmLink<O> extends AjaxConfirmLink<O> {
		private static final long serialVersionUID = -2098954474307467112L;
		
		private final IOneParameterAjaxAction<IModel<O>> onClick;
		
		public FunctionalAjaxConfirmLink(String id, IModel<O> model,
				IOneParameterModelFactory<IModel<O>, String> titleModelFactory,
				IOneParameterModelFactory<IModel<O>, String> textModelFactory, IModel<String> yesLabelModel,
				IModel<String> noLabelModel, IModel<String> cssClassNamesModel, boolean textNoEscape,
				IOneParameterAjaxAction<IModel<O>> onClick) {
			super(id, model, titleModelFactory.create(model), textModelFactory.create(model), yesLabelModel,
					noLabelModel, cssClassNamesModel, textNoEscape);
			this.onClick = onClick;
		}
		
		@Override
		public void onClick(AjaxRequestTarget target) {
			this.onClick.execute(target, getModel());
		}
		
		@Override
		protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
			super.updateAjaxAttributes(attributes);
			this.onClick.updateAjaxAttributes(attributes, getModel());
		}
		
		@Override
		protected void onDetach() {
			super.onDetach();
			Detachables.detach(onClick);
		}
	
	}

	@Override
	public void detach() {
		Detachables.detach(model, titleModelFactory, contentModelFactory, yesLabelModel, noLabelModel, cssClassNamesModel, onClick);
	}

}