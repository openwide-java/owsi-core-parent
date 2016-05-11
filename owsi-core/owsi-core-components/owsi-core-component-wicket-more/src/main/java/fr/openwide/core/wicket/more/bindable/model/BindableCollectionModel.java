package fr.openwide.core.wicket.more.bindable.model;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.wicket.model.IModel;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

import fr.openwide.core.wicket.more.markup.repeater.collection.IItemModelAwareCollectionModel;
import fr.openwide.core.wicket.more.model.CollectionCopyModel;
import fr.openwide.core.wicket.more.model.WorkingCopyCollectionModel;

public class BindableCollectionModel<T, C extends Collection<T>> extends BindableModel<C>
		implements IBindableCollectionModel<T, C> {
	private static final long serialVersionUID = 507773585338822489L;
	
	private final IItemModelAwareCollectionModel<T, C, IBindableModel<T>> mainModel;

	public BindableCollectionModel(IModel<C> referenceModel, Supplier<? extends C> newCollectionSupplier,
			Function<? super T, ? extends IModel<T>> itemModelFunction) {
		this(
				new WorkingCopyCollectionModel<>(
						referenceModel,
						CollectionCopyModel.custom(newCollectionSupplier, BindableModel.factory(itemModelFunction))
				)
		);
	}

	private BindableCollectionModel(IItemModelAwareCollectionModel<T, C, IBindableModel<T>> delegate) {
		super(delegate);
		this.mainModel = delegate;
	}

	@Override
	public boolean equals(Object obj) {
		return new EqualsBuilder().appendSuper(super.equals(obj)).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode()).hashCode();
	}
	
	@Override
	protected void onWriteAll() {
		super.onWriteAll();
		for (IBindableModel<T> itemModel : mainModel) {
			itemModel.writeAll();
		}
	}
	
	@Override
	protected void onReadAll() {
		super.onReadAll();
		for (IBindableModel<T> itemModel : mainModel) {
			itemModel.readAll();
		}
	}
	
	@Override
	public Iterator<IBindableModel<T>> iterator(long offset, long limit) {
		return mainModel.iterator(offset, limit);
	}

	@Override
	public Iterator<IBindableModel<T>> iterator() {
		return mainModel.iterator();
	}
	
	@Override
	public long size() {
		return mainModel.size();
	}

	@Override
	public void add(T item) {
		mainModel.add(item);
	}
	
	@Override
	public void remove(T item) {
		mainModel.remove(item);
	}
	
	@Override
	public void clear() {
		mainModel.clear();
	}

}