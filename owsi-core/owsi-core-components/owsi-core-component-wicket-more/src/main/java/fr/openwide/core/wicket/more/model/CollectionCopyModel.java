package fr.openwide.core.wicket.more.model;

import java.io.Serializable;
import java.util.Collection;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

import fr.openwide.core.commons.util.functional.SerializableFunction;
import fr.openwide.core.commons.util.functional.Suppliers2;
import fr.openwide.core.wicket.more.markup.repeater.collection.ICollectionModel;
import fr.openwide.core.wicket.more.util.model.Models;

/**
 * A {@link ICollectionModel} whose content is to be "cloned" (i.e. copied to a new
 * collection) each time {@link #setObject(Collection)} is called.
 * 
 * <p>This is typically what you want when editing a collection in a form.
 * 
 * <p>Instances of this class are guaranteed to always return the same model for a given element, up to this element's
 * removal from the collection.
 * 
 * <p><strong>WARNING:</strong> this model is only intended to contain small collections. It is absolutely not optimized
 * for large collections (say, more than just one or two dozens of items). Performance issues may arise when dealing
 * with large collections.
 * 
 * @see AbstractCollectionCopyModel
 */
public final class CollectionCopyModel<T, C extends Collection<T>, M extends IModel<T>>
		extends AbstractCollectionCopyModel<T, C, M> {

	private static final long serialVersionUID = -1768835911782930879L;
	
	private final Supplier<? extends C> newCollectionSupplier;
	
	private final Function<? super T, ? extends M> itemModelFunction;
	
	/**
	 * Creates a collection copy model suitable for elements that may be safely serialized as is, such as enums.
	 * <p>This <strong>should not</strong> be used when your elements are database entities.
	 * <p><strong>Be aware</strong> that you probably won't need to implement the supplier yourself,
	 * as {@link Suppliers2} provides a wide range of collection suppliers.
	 */
	public static <T extends Serializable, C extends Collection<T>, M extends IModel<T>> CollectionCopyModel<T, C, Model<T>>
			serializable(Supplier<? extends C> newCollectionSupplier) {
		return new CollectionCopyModel<>(newCollectionSupplier, Models.<T>serializableModelFactory());
	}

	/**
	 * Creates a collection copy model suitable for elements that must be serialized through a custom model, such as entities.
	 * <p><strong>Be aware</strong> that you probably won't need to implement the supplier and functions yourself,
	 * as {@link Suppliers2} provides a wide range of collection suppliers, and several models have pre-existing
	 * factory functions ({@link GenericEntityModel#factory()} or {@link Models#serializableModelFactory()}, most notably).
	 */
	public static <T, C extends Collection<T>, M extends IModel<T>> CollectionCopyModel<T, C, M>
			custom(Supplier<? extends C> newCollectionSupplier, Function<? super T, ? extends M> itemModelFunction) {
		return new CollectionCopyModel<>(newCollectionSupplier, itemModelFunction);
	}
	
	/**
	 * @return A factory that will call {@link #custom(Supplier, Function) and put the input object in it.
	 */
	public static <T, C extends Collection<T>, M extends IModel<T>> Function<C, CollectionCopyModel<T, C, M>>
			factory(final Supplier<? extends C> newCollectionSupplier,
					final Function<? super T, ? extends M> itemModelFunction) {
		return new SerializableFunction<C, CollectionCopyModel<T, C, M>>() {
			private static final long serialVersionUID = 1L;
			@Override
			public CollectionCopyModel<T, C, M> apply(C input) {
				CollectionCopyModel<T, C, M> result = custom(newCollectionSupplier, itemModelFunction);
				result.setObject(input);
				return result;
			}
		};
	}
	
	private CollectionCopyModel(Supplier<? extends C> newCollectionSupplier, Function<? super T, ? extends M> itemModelFunction) {
		super();
		this.newCollectionSupplier = newCollectionSupplier;
		this.itemModelFunction = itemModelFunction;
		setObject(null); // Sets to an empty collection
	}

	@Override
	protected C createCollection() {
		return newCollectionSupplier.get();
	}

	@Override
	protected M createModel(T item) {
		return itemModelFunction.apply(item);
	}

}
