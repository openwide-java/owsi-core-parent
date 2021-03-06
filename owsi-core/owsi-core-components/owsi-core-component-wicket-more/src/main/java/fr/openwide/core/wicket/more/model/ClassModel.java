package fr.openwide.core.wicket.more.model;

import org.apache.wicket.core.util.lang.WicketObjects;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * Seems to be the "clean" Wicket way to serialize a class object. See BookmarkablePageLink.
 * @deprecated Do not use this. This model useless and potentially harmful in a multithreaded context. Use Model.of() instead. 
 */
@Deprecated
public class ClassModel<T> extends LoadableDetachableModel<Class<T>> {
	private static final long serialVersionUID = 1L;
	
	private String className;

	/**
	 * @deprecated Do not use this. This model is useless and potentially harmful in a multithreaded context. Use Model.of() instead. 
	 */
	@Deprecated
	public static <T> ClassModel<T> of(Class<T> clazz) {
		return new ClassModel<T>(clazz);
	}
	
	public ClassModel() {
	}
	
	public ClassModel(Class<T> clazz) {
		super(clazz);
	}

	@Override
	protected Class<T> load() {
		return className == null ? null : WicketObjects.<T>resolveClass(className);
	}
	
	@Override
	public void onDetach() {
		Class<? extends T> clazz = getObject();
		className = (clazz == null) ? null : clazz.getName();
		super.onDetach();
	}
}
