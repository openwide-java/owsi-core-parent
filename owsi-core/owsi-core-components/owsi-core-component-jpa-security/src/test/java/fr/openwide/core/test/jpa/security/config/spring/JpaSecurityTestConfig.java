package fr.openwide.core.test.jpa.security.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import fr.openwide.core.jpa.more.rendering.service.EmptyRendererServiceImpl;
import fr.openwide.core.jpa.more.rendering.service.IRendererService;
import fr.openwide.core.spring.config.spring.AbstractApplicationConfig;
import fr.openwide.core.spring.config.spring.annotation.ApplicationDescription;
import fr.openwide.core.spring.config.spring.annotation.ConfigurationLocations;
import fr.openwide.core.test.jpa.security.business.JpaSecurityTestBusinessPackage;

@Configuration
@ApplicationDescription(name = "owsi-core-component-security")
@ConfigurationLocations(locations = {
		"classpath:owsi-core-component-jpa.properties",
		"classpath:jpa-security-test.properties"
})
@ComponentScan(basePackageClasses = {
		JpaSecurityTestBusinessPackage.class
})
@Import({
	JpaSecurityTestJpaConfig.class,
	JpaSecurityTestSecurityConfig.class,
	JpaSecurityTestApplicationPropertyConfig.class
})
public class JpaSecurityTestConfig extends AbstractApplicationConfig {
	
	@Bean
	public IRendererService rendererService() {
		return new EmptyRendererServiceImpl();
	}

}
