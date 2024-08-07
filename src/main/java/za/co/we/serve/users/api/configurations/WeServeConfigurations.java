package za.co.we.serve.users.api.configurations;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeServeConfigurations {

	@Bean
	PhysicalNamingStrategy physical() {
		return new PhysicalNamingStrategyStandardImpl();
	}

	@Bean
	ImplicitNamingStrategy implicit() {
		return new ImplicitNamingStrategyLegacyJpaImpl();
	}
}
