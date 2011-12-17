package fr.openwide.core.wicket.more.markup.html.pages.monitoring;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.openwide.core.jpa.util.IDatabaseConsistencyCheckService;

public class DatabaseMonitoringPage extends AbstractMonitoringPage {

	private static final long serialVersionUID = -2405917274441638671L;

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseMonitoringPage.class);

	@SpringBean
	private IDatabaseConsistencyCheckService databaseConsistencyCheckService;

	private boolean success;

	private List<String> details = new ArrayList<String>();

	private String message;

	public DatabaseMonitoringPage() {
		super();
		
		try {
			databaseConsistencyCheckService.checkDatabaseAccess();
			success = true;
			message = "";
		} catch (Exception e) {
			success = false;
			details.add("Error connecting to database.");
			message = "Stack trace added in application logs.";
			LOGGER.error("Error in database monitoring.", e);
		}
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

	@Override
	public List<String> getDetails() {
		return details;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
