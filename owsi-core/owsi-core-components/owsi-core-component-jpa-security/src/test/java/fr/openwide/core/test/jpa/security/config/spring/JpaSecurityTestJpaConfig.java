package fr.openwide.core.test.jpa.security.config.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import fr.openwide.core.jpa.config.spring.provider.JpaPackageScanProvider;
import fr.openwide.core.jpa.security.config.spring.AbstractConfiguredJpaSecurityJpaConfig;
import fr.openwide.core.test.jpa.security.business.JpaSecurityTestBusinessPackage;

@Configuration
@EnableAspectJAutoProxy
public class JpaSecurityTestJpaConfig extends AbstractConfiguredJpaSecurityJpaConfig {

	@Override
	public JpaPackageScanProvider applicationJpaPackageScanProvider() {
		return new JpaPackageScanProvider(JpaSecurityTestBusinessPackage.class.getPackage());
	}

}
