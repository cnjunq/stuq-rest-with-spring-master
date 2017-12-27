package io.junq.examples.usercenter.service.main;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import io.junq.examples.common.persistence.model.INameableEntity;
import io.junq.examples.test.common.service.AbstractServiceIntegrationTest;
import io.junq.examples.usercenter.spring.UserCenterClientConfig;
import io.junq.examples.usercenter.spring.UserCenterContextConfiguration;
import io.junq.examples.usercenter.spring.UserCenterPersistenceJpaConfiguration;
import io.junq.examples.usercenter.spring.UserCenterServiceConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserCenterPersistenceJpaConfiguration.class, UserCenterServiceConfiguration.class, UserCenterContextConfiguration.class, UserCenterClientConfig.class }, loader = AnnotationConfigContextLoader.class)
public abstract class SecServiceIntegrationTest<T extends INameableEntity> extends AbstractServiceIntegrationTest<T> {

}
