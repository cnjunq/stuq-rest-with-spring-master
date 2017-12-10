package io.junq.examples.usercenter.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:web-${webTarget:local}.properties")
@ComponentScan({ "io.junq.examples.usercenter.model" })
public class UserCenterLiveTestConfiguration {

    public UserCenterLiveTestConfiguration() {
        super();
    }

    // beans

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        final PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        return pspc;
    }
	
}
