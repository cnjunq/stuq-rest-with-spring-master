package io.junq.examples.usercenter.test.live;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import io.junq.examples.common.interfaces.IDto;
import io.junq.examples.test.common.web.AbstractDiscoverabilityLiveTest;
import io.junq.examples.usercenter.spring.RestTestConfig;
import io.junq.examples.usercenter.spring.UserCenterClientConfig;
import io.junq.examples.usercenter.spring.UserCenterLiveTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserCenterLiveTestConfiguration.class, UserCenterClientConfig.class, RestTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class UserCenterDiscoverabilityRestLiveTest<T extends IDto> extends AbstractDiscoverabilityLiveTest<T> {

    public UserCenterDiscoverabilityRestLiveTest(final Class<T> clazzToSet) {
        super(clazzToSet);
    }
	
}
