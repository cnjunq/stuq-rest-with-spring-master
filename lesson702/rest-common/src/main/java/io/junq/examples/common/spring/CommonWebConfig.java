package io.junq.examples.common.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "io.junq.examples.common.web" })
public class CommonWebConfig {

    public CommonWebConfig() {
        super();
    }
	
}
