package io.junq.examples.usercenter.web.role;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.junq.examples.usercenter.client.template.PrivilegeRestClient;
import io.junq.examples.usercenter.client.template.RoleRestClient;
import io.junq.examples.usercenter.persistence.model.Role;
import io.junq.examples.usercenter.test.live.UserCenterReadOnlyLogicRestLiveTest;
import io.restassured.response.Response;

public class RoleReadOnlyLogicRestLiveTest extends UserCenterReadOnlyLogicRestLiveTest<Role> {

	private static final String APPLICATION_KRYO = "application/x-kryo";
	
    @Autowired
    private RoleRestClient api;
    @Autowired
    private PrivilegeRestClient associationApi;

    public RoleReadOnlyLogicRestLiveTest() {
        super(Role.class);
    }

    // tests

    @Test
    public final void giveConsumingAsKryo_whenAllResourcesAreRetrieved_then200IsReceived() {
        // When
        final Response response = getApi().givenReadAuthenticated().accept(APPLICATION_KRYO).get(getApi().getUri());

        // Then
        assertThat(response.getStatusCode(), is(200));
        assertThat(response.getContentType(), equalTo(APPLICATION_KRYO));
    }

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
