package io.junq.examples.usercenter.test.live;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import io.junq.examples.common.interfaces.INameableDto;
import io.junq.examples.test.common.web.AbstractLogicLiveTest;
import io.junq.examples.usercenter.spring.RestTestConfig;
import io.junq.examples.usercenter.spring.UserCenterClientConfig;
import io.junq.examples.usercenter.spring.UserCenterLiveTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserCenterLiveTestConfiguration.class, UserCenterClientConfig.class, RestTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class UserCenterLogicRestLiveTest<T extends INameableDto> extends AbstractLogicLiveTest<T> {

	public UserCenterLogicRestLiveTest(final Class<T> clazzToSet) {
		super(clazzToSet);
	}
}
