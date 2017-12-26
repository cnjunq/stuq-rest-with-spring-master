package io.junq.examples.usercenter.web.role;

import org.springframework.beans.factory.annotation.Autowired;

import io.junq.examples.client.IDtoOperations;
import io.junq.examples.usercenter.client.template.RoleRestClient;
import io.junq.examples.usercenter.model.RoleDtoOpsImpl;
import io.junq.examples.usercenter.persistence.model.Role;
import io.junq.examples.usercenter.test.live.UserCenterDiscoverabilityRestLiveTest;

public class RoleDiscoverabilityRestLiveTest extends UserCenterDiscoverabilityRestLiveTest<Role> {

    @Autowired
    private RoleRestClient restTemplate;

    @Autowired
    private RoleDtoOpsImpl entityOps;

    public RoleDiscoverabilityRestLiveTest() {
        super(Role.class);
    }

    // tests

    // template method

    @Override
    protected final Role createNewResource() {
        return getEntityOps().createNewResource();
    }

    @Override
    protected final String getUri() {
        return getApi().getUri();
    }

    @Override
    protected final RoleRestClient getApi() {
        return restTemplate;
    }

    @Override
    protected final IDtoOperations<Role> getEntityOps() {
        return entityOps;
    }

}
