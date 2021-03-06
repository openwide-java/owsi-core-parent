package fr.openwide.core.wicket.more.export.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.Application;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import fr.openwide.core.commons.util.mime.MediaType;
import fr.openwide.core.wicket.more.model.BindingModel;
import fr.openwide.core.wicket.more.util.binding.CoreWicketMoreBindings;

public class ExportFileUtils {

	public static final String FILE_NAME_DATE_PATTERN_KEY = "common.action.export.file.datePattern";

	public static IModel<String> getFileNameMediaTypeModel(IModel<String> fileNamePrefixModel, IModel<MediaType> mediaTypeModel) {
		return getFileNameExtensionModel(fileNamePrefixModel, BindingModel.of(mediaTypeModel, CoreWicketMoreBindings.mediaType().extension()));
	}

	public static IModel<String> getFileNameExtensionModel(final IModel<String> fileNamePrefixModel, final IModel<String> extensionModel) {
		return new AbstractReadOnlyModel<String>() {
			private static final long serialVersionUID = 1L;
			@Override
			public String getObject() {
				return new StringBuilder(fileNamePrefixModel.getObject())
						.append(new SimpleDateFormat(Application.get().getResourceSettings().getLocalizer().getString(FILE_NAME_DATE_PATTERN_KEY, null)).format(new Date()))
						.append(".")
						.append(extensionModel.getObject())
						.toString();
			}
		};
	}

	private ExportFileUtils() {
	}

}
