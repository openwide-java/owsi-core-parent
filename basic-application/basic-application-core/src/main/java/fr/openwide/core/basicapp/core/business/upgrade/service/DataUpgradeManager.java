package fr.openwide.core.basicapp.core.business.upgrade.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Service;

@Service("dataUpgradeManager")
public class DataUpgradeManager implements ApplicationListener<ContextRefreshedEvent> {
	
  	private static final Logger LOGGER = LoggerFactory.getLogger(DataUpgradeManager.class);
	
	@Autowired
	private IDataUpgradeService dataUpgradeService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event != null && event.getSource() != null
				&& AbstractApplicationContext.class.isAssignableFrom(event.getSource().getClass())
				&& ((AbstractApplicationContext) event.getSource()).getParent() == null) {
			init();
		}		
	}
	
	private void init() {
		LOGGER.trace("Executing data upgrades (starting)");
		try {
			dataUpgradeService.autoPerformDataUpgrades();
		} catch (Throwable e) {
			LOGGER.error("Error executing data upgrades ",e);
		}
		LOGGER.trace("Executing data upgrades (success)");
	}


}
