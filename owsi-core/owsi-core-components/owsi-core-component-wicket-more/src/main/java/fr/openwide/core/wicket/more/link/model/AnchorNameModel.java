package fr.openwide.core.wicket.more.link.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import fr.openwide.core.spring.util.StringUtils;

public class AnchorNameModel extends LoadableDetachableModel<String> {
	
	private static final long serialVersionUID = 1L;
	
	public static enum AnchorType {
		HREF,
		ID;
	}
	
	private final IModel<String> unencodedAnchorNameModel;

	private final AnchorType anchorType;

	public AnchorNameModel(IModel<String> unencodedAnchorNameModel, AnchorType anchorType) {
		super();
		this.unencodedAnchorNameModel = unencodedAnchorNameModel;
		this.anchorType = anchorType;
	}

	@Override
	protected String load() {
		StringBuilder builder = new StringBuilder();
		if (AnchorType.HREF.equals(anchorType)) {
			builder.append("#");
		}
		builder.append(StringUtils.urlize(unencodedAnchorNameModel.getObject()));
		return builder.toString();
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		if (unencodedAnchorNameModel != null) {
			unencodedAnchorNameModel.detach();
		}
	}

}