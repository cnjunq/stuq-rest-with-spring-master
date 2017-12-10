package io.junq.examples.usercenter.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import io.junq.examples.client.spring.RestClientConfig;
import io.junq.examples.usercenter.spring.UserCenterClientConfig;
import io.junq.examples.usercenter.spring.UserCenterContextConfiguration;
import io.junq.examples.usercenter.spring.UserCenterPersistenceJpaConfiguration;
import io.junq.examples.usercenter.spring.UserCenterServiceConfiguration;
import io.junq.examples.usercenter.spring.UserCenterWebConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestClientConfig.class,
						UserCenterClientConfig.class, 
						UserCenterPersistenceJpaConfiguration.class,
						UserCenterContextConfiguration.class, 
						UserCenterServiceConfiguration.class, 
						UserCenterWebConfiguration.class })
@WebAppConfiguration
public class WebSpringIntegrationTest {

    @Test
    public final void whenContextIsBootstrapped_thenOk() {
        //
    }

}
