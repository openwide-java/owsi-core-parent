package fr.openwide.core.basicapp.web.application.security.password.template;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import fr.openwide.core.basicapp.web.application.common.template.ApplicationAccessTemplate;

public abstract class SecurityPasswordTemplate extends ApplicationAccessTemplate {

	private static final long serialVersionUID = -4350860041946569108L;

	public SecurityPasswordTemplate(PageParameters parameters) {
		super(parameters);
	}

}
