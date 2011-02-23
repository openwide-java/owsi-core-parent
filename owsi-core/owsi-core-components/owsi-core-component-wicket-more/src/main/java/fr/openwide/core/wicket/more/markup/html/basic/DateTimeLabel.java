/*
 * Copyright (C) 2009-2011 Open Wide
 * Contact: contact@openwide.fr
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.openwide.core.wicket.more.markup.html.basic;

import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

import fr.openwide.core.wicket.more.util.DatePattern;
import fr.openwide.core.wicket.more.util.convert.converters.PatternDateConverter;

public class DateTimeLabel extends Label {
	private static final long serialVersionUID = 7214422620839758144L;

	private IConverter converter;
	
	private DatePattern datePattern;
	
	public DateTimeLabel(String id, IModel<Date> model, DatePattern dateFormat) {
		super(id, model);
		
		this.datePattern = dateFormat;
	}
	
	@Override
	public void onInitialize() {
		super.onInitialize();
		
		this.converter = new PatternDateConverter(getString(datePattern.getJavaPatternKey()));
	}
	
	@Override
	public IConverter getConverter(Class<?> type) {
		return converter;
	}
	
	@Override
	protected void detachModel() {
		super.detachModel();
	}

}
