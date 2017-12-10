package io.junq.examples.usercenter.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
	UserCenterContextConfiguration.class,
	UserCenterPersistenceJpaConfiguration.class,
	UserCenterServiceConfiguration.class,
	UserCenterWebConfiguration.class,
	UserCenterServletConfiguration.class,
	UserCenterSecurityConfiguration.class
})
public class UserCenterApplication extends SpringBootServletInitializer {
	
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
		return builder.sources(UserCenterApplication.class);
	}
	
	public static void main(final String[] args) {
		SpringApplication.run(UserCenterApplication.class, args);
	}
	
}
