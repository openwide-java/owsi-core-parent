package fr.openwide.core.basicapp.web.application.common.form.impl;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import fr.openwide.core.basicapp.core.business.user.model.UserGroup;
import fr.openwide.core.basicapp.web.application.common.renderer.UserGroupRenderer;
import fr.openwide.core.wicket.more.rendering.GenericEntityIdStringRenderer;

public final class UserGroupChoiceRenderer extends ChoiceRenderer<UserGroup> {
	
	private static final long serialVersionUID = 8473460282352945231L;
	
	private static final UserGroupChoiceRenderer INSTANCE = new UserGroupChoiceRenderer();
	public static UserGroupChoiceRenderer get() {
		return INSTANCE;
	}
	
	private UserGroupChoiceRenderer() {
	}
	
	@Override
	public Object getDisplayValue(UserGroup user) {
		return user != null ? UserGroupRenderer.get().render(user, null) : "";
	}
	
	@Override
	public String getIdValue(UserGroup user, int index) {
		return GenericEntityIdStringRenderer.get().render(user);
	}
}