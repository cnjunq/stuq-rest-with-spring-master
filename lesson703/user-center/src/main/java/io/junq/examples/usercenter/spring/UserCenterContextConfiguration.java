package io.junq.examples.usercenter.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan("io.junq.examples.usercenter.persistence.model")
@PropertySource("classpath:persistence-${persistenceTarget:local}.properties")
public class UserCenterContextConfiguration {

    public UserCenterContextConfiguration() {
        super();
    }

    // beans

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        final PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        return pspc;
    }
	
}
