package io.junq.examples.usercenter.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "io.junq.examples.usercenter.service" })
public class UserCenterServiceConfiguration {

    public UserCenterServiceConfiguration() {
        super();
    }

    // beans
	
}
