package fr.openwide.core.wicket.more.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.base.Supplier;

import fr.openwide.core.commons.util.functional.Suppliers2;
import fr.openwide.core.jpa.business.generic.model.GenericEntity;

/**
 * @deprecated use {@link CollectionCopyModel} with {@link Suppliers2} and {@link GenericEntityModel} instead : 
 * <pre>
 * {@code 
 * GenericEntityTreeSetModel.of(Class<E>[, Comparator<E>]);
 * ->
 * CollectionCopyModel.custom(Suppliers2.<E>treeSet[As[Sorted]Set]([Comparator<E>]), GenericEntityModel.<E>factory());
 * }
 * </pre>
 */
@Deprecated
public class GenericEntityTreeSetModel<K extends Serializable & Comparable<K>, E extends GenericEntity<K, ?>>
		extends AbstractGenericEntityCollectionModel<K, E, SortedSet<E>> {

	private static final long serialVersionUID = -1197465312043104726L;

	public static <K extends Serializable & Comparable<K>, E extends GenericEntity<K, ?>>
			GenericEntityTreeSetModel<K, E> of(Class<E> clazz) {
		return new GenericEntityTreeSetModel<K, E>(clazz, Suppliers2.<E>treeSet());
	}

	public static <K extends Serializable & Comparable<K>, E extends GenericEntity<K, ?>>
			GenericEntityTreeSetModel<K, E> of(Class<E> clazz, Comparator<? super E> comparator) {
		return new GenericEntityTreeSetModel<K, E>(clazz, Suppliers2.<E>treeSet(comparator));
	}

	protected GenericEntityTreeSetModel(Class<E> clazz, Supplier<? extends TreeSet<E>> newCollectionSupplier) {
		super(clazz, newCollectionSupplier);
	}

}
