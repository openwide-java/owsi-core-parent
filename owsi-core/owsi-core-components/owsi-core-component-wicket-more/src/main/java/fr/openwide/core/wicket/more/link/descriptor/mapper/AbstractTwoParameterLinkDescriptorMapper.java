package fr.openwide.core.wicket.more.link.descriptor.mapper;

import org.apache.wicket.model.IModel;

import com.google.common.base.Function;

import fr.openwide.core.wicket.more.model.ReadOnlyModel;
import fr.openwide.core.wicket.more.util.model.Models;

public abstract class AbstractTwoParameterLinkDescriptorMapper<L, T1, T2>
		implements ITwoParameterLinkDescriptorMapper<L, T1, T2> {
	private static final long serialVersionUID = 1993813798185549585L;
	
	@Override
	public void detach() { }
	
	@Override
	public IOneParameterLinkDescriptorMapper<L, T2> setParameter1(final IModel<T1> model1) {
		return new AbstractOneParameterLinkDescriptorMapper<L, T2>() {
			private static final long serialVersionUID = 1L;
			@Override
			public L map(IModel<T2> model2) {
				return AbstractTwoParameterLinkDescriptorMapper.this.map(model1, model2);
			}
			@Override
			public void detach() {
				super.detach();
				model1.detach();
				AbstractTwoParameterLinkDescriptorMapper.this.detach();
			}
		};
	}
	
	@Override
	public IOneParameterLinkDescriptorMapper<L, T2> setParameter1(final Function<T2, T1> function) {
		return new AbstractOneParameterLinkDescriptorMapper<L, T2>() {
			private static final long serialVersionUID = 1L;
			@Override
			public L map(IModel<T2> model2) {
				return AbstractTwoParameterLinkDescriptorMapper.this.map(ReadOnlyModel.of(model2, function), model2);
			}
			@Override
			public void detach() {
				super.detach();
				AbstractTwoParameterLinkDescriptorMapper.this.detach();
			}
		};
	}
	
	@Override
	public <U1 extends T1> ITwoParameterLinkDescriptorMapper<L, U1, T2> castParameter1() {
		return new AbstractTwoParameterLinkDescriptorMapper<L, U1, T2>() {
			private static final long serialVersionUID = 1L;
			@Override
			public L map(IModel<U1> model1, IModel<T2> model2) {
				return AbstractTwoParameterLinkDescriptorMapper.this.map(ReadOnlyModel.<T1>of(model1), model2);
			}
			@Override
			public void detach() {
				super.detach();
				AbstractTwoParameterLinkDescriptorMapper.this.detach();
			}
		};
	}
	
	@Override
	public IOneParameterLinkDescriptorMapper<L, T2> ignoreParameter1() {
		return setParameter1(Models.<T1>placeholder());
	}

	@Override
	public IOneParameterLinkDescriptorMapper<L, T1> setParameter2(final IModel<T2> model2) {
		return new AbstractOneParameterLinkDescriptorMapper<L, T1>() {
			private static final long serialVersionUID = 1L;
			@Override
			public L map(IModel<T1> model1) {
				return AbstractTwoParameterLinkDescriptorMapper.this.map(model1, model2);
			}
			@Override
			public void detach() {
				super.detach();
				model2.detach();
				AbstractTwoParameterLinkDescriptorMapper.this.detach();
			}
		};
	}
	
	@Override
	public IOneParameterLinkDescriptorMapper<L, T1> setParameter2(final Function<T1, T2> function) {
		return new AbstractOneParameterLinkDescriptorMapper<L, T1>() {
			private static final long serialVersionUID = 1L;
			@Override
			public L map(IModel<T1> model1) {
				return AbstractTwoParameterLinkDescriptorMapper.this.map(model1, ReadOnlyModel.of(model1, function));
			}
			@Override
			public void detach() {
				super.detach();
				AbstractTwoParameterLinkDescriptorMapper.this.detach();
			}
		};
	}
	
	@Override
	public <U2 extends T2> ITwoParameterLinkDescriptorMapper<L, T1, U2> castParameter2() {
		return new AbstractTwoParameterLinkDescriptorMapper<L, T1, U2>() {
			private static final long serialVersionUID = 1L;
			@Override
			public L map(IModel<T1> model1, IModel<U2> model2) {
				return AbstractTwoParameterLinkDescriptorMapper.this.map(model1, ReadOnlyModel.<T2>of(model2));
			}
			@Override
			public void detach() {
				super.detach();
				AbstractTwoParameterLinkDescriptorMapper.this.detach();
			}
		};
	}
	
	@Override
	public IOneParameterLinkDescriptorMapper<L, T1> ignoreParameter2() {
		return setParameter2(Models.<T2>placeholder());
	}

}
