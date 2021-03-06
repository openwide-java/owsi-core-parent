package fr.openwide.core.wicket.more.link.descriptor.impl;

import org.apache.wicket.Component;

import com.google.common.collect.ImmutableList;

import fr.openwide.core.wicket.more.link.descriptor.generator.ILinkGenerator;

public class ChainedLinkGeneratorImpl extends AbstractChainedLinkGenerator<ILinkGenerator> {

	private static final long serialVersionUID = 1L;

	public ChainedLinkGeneratorImpl(Iterable<? extends ILinkGenerator> chain) {
		super(chain);
	}

	@Override
	public ILinkGenerator chain(ILinkGenerator other) {
		return new ChainedLinkGeneratorImpl(
				ImmutableList.<ILinkGenerator>builder().addAll(getChain()).add(other).build()
		);
	}

	@Override
	public ILinkGenerator wrap(Component component) {
		ImmutableList.Builder<ILinkGenerator> builder = ImmutableList.builder();
		for (ILinkGenerator element : getChain()) {
			builder.add(element.wrap(component));
		}
		return new ChainedLinkGeneratorImpl(builder.build());
	}

}
