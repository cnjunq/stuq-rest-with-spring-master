package io.junq.examples.usercenter.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.junq.examples.client.spring.RestClientConfig;
import io.junq.examples.common.spring.CommonWebConfig;

@Configuration
@ComponentScan({ "io.junq.examples.usercenter.client"})
@Import({ RestClientConfig.class, CommonWebConfig.class })
public class UserCenterClientConfig {

    public UserCenterClientConfig() {
        super();
    }

}
