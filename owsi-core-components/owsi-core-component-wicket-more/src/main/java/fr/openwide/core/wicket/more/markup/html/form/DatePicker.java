package fr.openwide.core.wicket.more.markup.html.form;

import java.util.Date;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.validator.DateValidator;
import org.odlabs.wiquery.ui.datepicker.DateOption;

import fr.openwide.core.wicket.more.util.IDatePattern;
import fr.openwide.core.wicket.more.util.convert.converters.PatternDateConverter;

public class DatePicker extends org.odlabs.wiquery.ui.datepicker.DatePicker<Date> {

	private static final long serialVersionUID = 8051575483617364457L;
	
	private static final String AUTOCOMPLETE_ATTRIBUTE = "autocomplete";
	
	private static final String AUTOCOMPLETE_ATTRIBUTE_OFF_VALUE = "off";

	private IConverter<Date> converter;

	private IDatePattern datePattern;
	
	private boolean isAutocompleteActive;

	public DatePicker(String id, IModel<Date> model, IDatePattern datePattern) {
		this(id, model, datePattern, false);
	}
	
	public DatePicker(String id, IModel<Date> model, IDatePattern datePattern, boolean isAutocompleteActive) {
		super(id, model, Date.class);
		this.datePattern = datePattern;
		this.isAutocompleteActive = isAutocompleteActive;
		
		// Options par défaut
		setShowButtonPanel(true);
	}
	
	public DatePicker setRange(Date minDate, Date maxDate) {
		if (minDate != null) {
			setMinDate(new DateOption(minDate));
		}
		if (maxDate != null) {
			setMaxDate(new DateOption(maxDate));
		}
		add(new DateValidator(minDate, maxDate));
		return this;
	}
	
	@Override
	public void onInitialize() {
		super.onInitialize();
		this.setDateFormat(getString(datePattern.getJavascriptPatternKey()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <C> IConverter<C> getConverter(Class<C> type) {
		if (Date.class.isAssignableFrom(type)) {
			if (converter == null) {
				converter = new PatternDateConverter(datePattern, getString(datePattern.getJavaPatternKey()));
			}
			return (IConverter<C>) converter;
		} else {
			return super.getConverter(type);
		}
	}
	
	@Override
	protected void detachModel() {
		super.detachModel();
		
		converter = null;
	}

	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		
		if (!isAutocompleteActive) {
			tag.put(AUTOCOMPLETE_ATTRIBUTE, AUTOCOMPLETE_ATTRIBUTE_OFF_VALUE);
		}
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(DatePickerOverrideJavaScriptResourceReference.get()));
	}
}