package io.junq.examples.client.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "io.junq.examples.common.client", "io.junq.examples.client" })
public class RestClientConfig {
	
	  public RestClientConfig() {
	        super();
	    }

}
