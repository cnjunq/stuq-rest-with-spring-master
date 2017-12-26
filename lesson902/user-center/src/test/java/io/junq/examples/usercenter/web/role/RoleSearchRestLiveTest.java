package io.junq.examples.usercenter.web.role;

import org.springframework.beans.factory.annotation.Autowired;

import io.junq.examples.client.IDtoOperations;
import io.junq.examples.usercenter.client.template.RoleRestClient;
import io.junq.examples.usercenter.model.RoleDtoOpsImpl;
import io.junq.examples.usercenter.persistence.model.Role;
import io.junq.examples.usercenter.test.live.UserCenterSearchRestLiveTest;

public class RoleSearchRestLiveTest extends UserCenterSearchRestLiveTest<Role> {

    @Autowired
    private RoleRestClient restTemplate;

    @Autowired
    private RoleDtoOpsImpl entityOps;

    public RoleSearchRestLiveTest() {
        super();
    }

    // tests

    // template

    @Override
    protected final RoleRestClient getApi() {
        return restTemplate;
    }

    @Override
    protected final IDtoOperations<Role> getEntityOps() {
        return entityOps;
    }

}
