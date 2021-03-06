package fr.openwide.core.wicket.more.markup.html.template.js.jquery.plugins.bootstrap.confirm.fluid;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;

public interface IConfirmLinkBuilderStepNo<L extends AbstractLink, O> {

	IConfirmLinkBuilderStepOnclick<L, O> no(IModel<String> noLabelModel);
	
	IConfirmLinkBuilderStepOnclick<L, O> no(IModel<String> noLabelModel, IModel<String> noIconModel);
	
	IConfirmLinkBuilderStepOnclick<L, O> no(IModel<String> noLabelModel, IModel<String> noIconModel, IModel<String> noButtonModel);
	
}
