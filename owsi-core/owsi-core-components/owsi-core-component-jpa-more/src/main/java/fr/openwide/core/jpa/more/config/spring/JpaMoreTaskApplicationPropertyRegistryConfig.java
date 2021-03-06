package fr.openwide.core.jpa.more.config.spring;

import static fr.openwide.core.jpa.more.property.JpaMoreTaskPropertyIds.*;
import static fr.openwide.core.jpa.more.property.JpaMoreTaskPropertyIds.START_MODE;
import static fr.openwide.core.jpa.more.property.JpaMoreTaskPropertyIds.STOP_TIMEOUT;

import org.springframework.context.annotation.Configuration;

import fr.openwide.core.spring.config.spring.AbstractApplicationPropertyRegistryConfig;
import fr.openwide.core.spring.config.util.TaskQueueStartMode;
import fr.openwide.core.spring.property.service.IPropertyRegistry;

@Configuration
public class JpaMoreTaskApplicationPropertyRegistryConfig extends AbstractApplicationPropertyRegistryConfig {

	@Override
	protected void register(IPropertyRegistry registry) {
		registry.registerInteger(STOP_TIMEOUT, 70000);
		registry.registerEnum(START_MODE, TaskQueueStartMode.class, TaskQueueStartMode.manual);
		registry.registerInteger(QUEUE_NUMBER_OF_THREADS_TEMPLATE, 1);
		registry.registerLong(QUEUE_START_DELAY_TEMPLATE, 0l);
		registry.registerBoolean(QUEUE_START_EXECUTION_CONTEXT_WAIT_READY_TEMPLATE, true);
	}

}
