package fr.openwide.core.wicket.more.markup.html.bootstrap.label.renderer;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Locale;
import java.util.Objects;

import org.apache.wicket.model.IModel;

import fr.openwide.core.wicket.more.markup.html.bootstrap.label.model.IBootstrapColor;
import fr.openwide.core.wicket.more.model.LocaleAwareReadOnlyModel;
import fr.openwide.core.wicket.more.rendering.Renderer;
import fr.openwide.core.wicket.more.util.model.Detachables;

public abstract class BootstrapRenderer<T> extends Renderer<T> {

	private static final long serialVersionUID = -715674989551497434L;

	public static <T> BootstrapRenderer<T> constant(final String resourceKey, final String icon, final IBootstrapColor color) {
		return constant(resourceKey, resourceKey, icon, color);
	}

	public static <T> BootstrapRenderer<T> constant(final String labelResourceKey, final String tooltipResourceKey, final String icon, final IBootstrapColor color) {
		return new BootstrapRenderer<T>() {
			private static final long serialVersionUID = 1L;
			@Override
			protected BootstrapRendererInformation doRender(T value, Locale locale) {
				return BootstrapRendererInformation.builder()
						.label(getString(labelResourceKey, locale))
						.tooltip(getString(tooltipResourceKey, locale))
						.icon(icon)
						.color(color)
						.build();
			}
		};
	}

	public BootstrapRenderer() {
		super();
	}

	protected abstract BootstrapRendererInformation doRender(T value, Locale locale);

	@Override
	public String render(T value, Locale locale) {
		BootstrapRendererInformation information = doRender(value, locale);
		return information == null ? null : information.getLabel();
	}

	public IBootstrapColor renderColor(T value, Locale locale) {
		BootstrapRendererInformation information = doRender(value, locale);
		return information == null ? null : information.getColor();
	}

	public String renderIconCssClass(T value, Locale locale) {
		BootstrapRendererInformation information = doRender(value, locale);
		return information == null ? null : information.getIconCssClass();
	}

	public String renderTooltip(T value, Locale locale) {
		BootstrapRendererInformation information = doRender(value, locale);
		return information == null ? null : information.getTooltip();
	}
	
	@Override
	public IBootstrapRendererModel asModel(IModel<? extends T> valueModel) {
		return new BootstrapRendererModel(valueModel);
	}
	
	private final class BootstrapRendererModel extends LocaleAwareReadOnlyModel<String>
			implements IBootstrapRendererModel {
		private static final long serialVersionUID = -1080017215611311157L;
		
		private final IModel<? extends T> valueModel;
		
		/** Cache the BootstrapInformation so as not to render it three times for each BootstrapLabel
		 */
		private transient BootstrapRendererInformation lastInformation;
		private transient Locale lastLocale;
		private transient T lastValue;
		
		public BootstrapRendererModel(IModel<? extends T> valueModel) {
			super();
			this.valueModel = checkNotNull(valueModel);
		}
		
		@Override
		public String getObject(Locale locale) {
			BootstrapRendererInformation information = getInformation(locale);
			return information == null ? null : information.getLabel();
		}
		
		@Override
		public IModel<String> getIconCssClassModel() {
			return new LocaleAwareReadOnlyModel<String>() {
				private static final long serialVersionUID = 1L;
				@Override
				public String getObject(Locale locale) {
					BootstrapRendererInformation information = getInformation(locale);
					return information == null ? null : information.getIconCssClass();
				}
				@Override
				public void detach() {
					super.detach();
					BootstrapRendererModel.this.detach();
				}
			};
		}
		
		@Override
		public IModel<IBootstrapColor> getColorModel() {
			return new LocaleAwareReadOnlyModel<IBootstrapColor>() {
				private static final long serialVersionUID = 1L;
				@Override
				public IBootstrapColor getObject(Locale locale) {
					BootstrapRendererInformation information = getInformation(locale);
					return information == null ? null : information.getColor();
				}
				@Override
				public void detach() {
					super.detach();
					BootstrapRendererModel.this.detach();
				}
			};
		}
		
		@Override
		public IModel<String> getTooltipModel() {
			return new LocaleAwareReadOnlyModel<String>() {
				private static final long serialVersionUID = 1L;
				@Override
				public String getObject(Locale locale) {
					BootstrapRendererInformation information = getInformation(locale);
					return information == null ? null : information.getTooltip();
				}
				@Override
				public void detach() {
					super.detach();
					BootstrapRendererModel.this.detach();
				}
			};
		}
		
		private BootstrapRendererInformation getInformation(Locale locale) {
			T value = valueModel.getObject();
			if (lastInformation != null && Objects.equals(locale, lastLocale) && Objects.equals(value, lastValue)) {
				// Use the cache
				return lastInformation;
			} else {
				lastInformation = doRender(value, locale);
				lastLocale = locale;
				lastValue = value;
				return lastInformation;
			}
		}
		
		@Override
		public void detach() {
			super.detach();
			Detachables.detach(valueModel);
			
			/* Force re-rendering, even if no serialization/deserialization occurs (which would make
			 * these transient fields null anyway)
			 */
			lastInformation = null;
			lastLocale = null;
			lastValue = null;
		}
	}

}
