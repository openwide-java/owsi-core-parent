package fr.openwide.core.jpa.security.business.person.model;

import org.bindgen.Bindable;

@Bindable
public interface ISimpleUser extends IUser {

	String getLastName();

	String getFirstName();

}
