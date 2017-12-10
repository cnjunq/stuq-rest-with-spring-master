package io.junq.examples.usercenter.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//@Configuration
@ComponentScan("io.junq.examples.usercenter.security")
@ImportResource({ "classpath*:userCenterSecurityConfig.xml" })
public class UserCenterSecurityConfiguration {

}
