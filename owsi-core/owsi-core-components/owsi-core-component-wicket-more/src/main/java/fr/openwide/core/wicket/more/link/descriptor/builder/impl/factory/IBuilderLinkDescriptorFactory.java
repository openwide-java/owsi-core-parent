package fr.openwide.core.wicket.more.link.descriptor.builder.impl.factory;

import java.util.List;
import java.util.Map;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.javatuples.Pair;
import org.javatuples.Tuple;

import fr.openwide.core.wicket.more.link.descriptor.builder.LinkDescriptorBuilder;
import fr.openwide.core.wicket.more.link.descriptor.builder.impl.parameter.LinkParameterMappingEntryBuilder;
import fr.openwide.core.wicket.more.link.descriptor.parameter.mapping.ILinkParameterMappingEntry;
import fr.openwide.core.wicket.more.link.descriptor.parameter.validator.ILinkParameterValidator;
import fr.openwide.core.wicket.more.link.descriptor.parameter.validator.factory.ILinkParameterValidatorFactory;
import fr.openwide.core.wicket.more.markup.html.factory.IDetachableFactory;

/**
 * A factory of link descriptors to be stored in the {@link LinkDescriptorBuilder} for keeping memory of the actual
 * link descriptor type that should be built.
 * @param <TTarget> The link target type (<code>Class<? extends Page></code>, <code>ResourceReference</code>, ...)
 * @param <TLinkDescriptor> The link descriptor type.
 */
public interface IBuilderLinkDescriptorFactory<TTarget, TLinkDescriptor> extends IDetachable {

	TLinkDescriptor create(
			IModel<? extends TTarget> targetModel,
			Iterable<? extends ILinkParameterMappingEntry> parameterMappingEntries,
			Iterable<? extends ILinkParameterValidator> validators
	);
	
	IBuilderMapperLinkDescriptorFactory<TLinkDescriptor> forMapper(
			Pair<
					? extends IDetachableFactory<? extends Tuple, ? extends IModel<? extends TTarget>>,
					? extends List<Integer>
					> targetFactory,
			Map<LinkParameterMappingEntryBuilder<?>, List<Integer>> mappingEntryBuilders,
			Map<ILinkParameterValidatorFactory<?>, List<Integer>> validatorFactories
	);

}
