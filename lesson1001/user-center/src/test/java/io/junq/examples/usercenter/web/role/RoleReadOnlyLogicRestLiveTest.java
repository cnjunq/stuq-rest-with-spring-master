package io.junq.examples.usercenter.web.role;

import org.springframework.beans.factory.annotation.Autowired;

import io.junq.examples.usercenter.client.template.PrivilegeRestClient;
import io.junq.examples.usercenter.client.template.RoleRestClient;
import io.junq.examples.usercenter.persistence.model.Role;
import io.junq.examples.usercenter.test.live.UserCenterReadOnlyLogicRestLiveTest;

public class RoleReadOnlyLogicRestLiveTest extends UserCenterReadOnlyLogicRestLiveTest<Role> {

    @Autowired
    private RoleRestClient api;
    @Autowired
    private PrivilegeRestClient associationApi;

    public RoleReadOnlyLogicRestLiveTest() {
        super(Role.class);
    }

    // tests

    // template

    @Override
    protected final RoleRestClient getApi() {
        return api;
    }

    // util

    final PrivilegeRestClient getAssociationAPI() {
        return associationApi;
    }

}
