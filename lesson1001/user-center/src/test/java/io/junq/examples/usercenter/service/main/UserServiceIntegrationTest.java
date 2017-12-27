package io.junq.examples.usercenter.service.main;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

import com.google.common.collect.Sets;

import io.junq.examples.common.persistence.service.IService;
import io.junq.examples.usercenter.persistence.model.Role;
import io.junq.examples.usercenter.persistence.model.User;
import io.junq.examples.usercenter.service.IUserService;

public class UserServiceIntegrationTest extends SecServiceIntegrationTest<User> {
	
    @Autowired
    private IUserService userService;

    // create

    @Test
    public void whenSaveIsPerformed_thenNoException() {
        getApi().create(createNewEntity());
    }

    @Test(expected = TransactionSystemException.class)
    public void whenAUniqueConstraintIsBroken_thenSpringSpecificExceptionIsThrown() {
        final String name = randomAlphabetic(8);

        getApi().create(createNewEntity(name));
        getApi().create(createNewEntity(name));
    }

    // template method

    @Override
    protected final IService<User> getApi() {
        return userService;
    }

    @Override
    protected final User createNewEntity() {
        return new User(randomAlphabetic(8), randomAlphabetic(8) + "@junq.io", randomAlphabetic(8), Sets.<Role> newHashSet());
    }

    protected final User createNewEntity(final String name) {
        return new User(name, randomAlphabetic(8), randomAlphabetic(8) + "@junq.io", Sets.<Role> newHashSet());
    }

    @Override
    protected void invalidate(final User entity) {
        entity.setName(null);
    }

    @Override
    protected void change(final User entity) {
        entity.setName(randomAlphabetic(6));
    }
}
