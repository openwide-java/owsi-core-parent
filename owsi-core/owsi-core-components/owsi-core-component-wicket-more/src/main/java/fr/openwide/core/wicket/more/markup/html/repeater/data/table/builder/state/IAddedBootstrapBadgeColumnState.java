package fr.openwide.core.wicket.more.markup.html.repeater.data.table.builder.state;

import org.apache.wicket.behavior.Behavior;

import com.google.common.base.Function;

import fr.openwide.core.commons.util.binding.AbstractCoreBinding;
import fr.openwide.core.jpa.more.business.sort.ISort;
import fr.openwide.core.wicket.more.condition.Condition;
import fr.openwide.core.wicket.more.link.descriptor.generator.ILinkGenerator;
import fr.openwide.core.wicket.more.link.descriptor.mapper.IOneParameterLinkDescriptorMapper;
import fr.openwide.core.wicket.more.markup.html.sort.ISortIconStyle;
import fr.openwide.core.wicket.more.markup.html.sort.TableSortLink.CycleMode;

public interface IAddedBootstrapBadgeColumnState<T, S extends ISort<?>, C> extends IAddedCoreColumnState<T, S> {

	@Override
	IAddedBootstrapBadgeColumnState<T, S, C> when(Condition condition);
	
	@Override
	IAddedBootstrapBadgeColumnState<T, S, C> withClass(String cssClass);
	
	@Override
	IAddedBootstrapBadgeColumnState<T, S, C> withSort(S sort);

	@Override
	IAddedBootstrapBadgeColumnState<T, S, C> withSort(S sort, ISortIconStyle sortIconStyle);

	@Override
	IAddedBootstrapBadgeColumnState<T, S, C> withSort(S sort, ISortIconStyle sortIconStyle, CycleMode cycleMode);

	IAddedBootstrapBadgeColumnState<T, S, C> withLink(IOneParameterLinkDescriptorMapper<? extends ILinkGenerator, T> linkGeneratorMapper);
	
	<E> IAddedBootstrapBadgeColumnState<T, S, C> withLink(Function<? super T, E> binding, IOneParameterLinkDescriptorMapper<? extends ILinkGenerator, E> linkGeneratorMapper);

	<E> IAddedBootstrapBadgeColumnState<T, S, C> withLink(AbstractCoreBinding<? super T, E> binding, IOneParameterLinkDescriptorMapper<? extends ILinkGenerator, E> linkGeneratorMapper);

	IAddedBootstrapBadgeColumnState<T, S, C> withSideLink(IOneParameterLinkDescriptorMapper<? extends ILinkGenerator, T> linkGeneratorMapper);
	
	<E> IAddedBootstrapBadgeColumnState<T, S, C> withSideLink(Function<? super T, E> binding, IOneParameterLinkDescriptorMapper<? extends ILinkGenerator, E> linkGeneratorMapper);

	<E> IAddedBootstrapBadgeColumnState<T, S, C> withSideLink(AbstractCoreBinding<? super T, E> binding, IOneParameterLinkDescriptorMapper<? extends ILinkGenerator, E> linkGeneratorMapper);

	IAddedBootstrapBadgeColumnState<T, S, C> disableIfInvalid();

	IAddedBootstrapBadgeColumnState<T, S, C> hideIfInvalid();

	IAddedBootstrapBadgeColumnState<T, S, C> linkBehavior(Behavior linkBehavior);

	IAddedBootstrapBadgeColumnState<T, S, C> targetBlank();

}
