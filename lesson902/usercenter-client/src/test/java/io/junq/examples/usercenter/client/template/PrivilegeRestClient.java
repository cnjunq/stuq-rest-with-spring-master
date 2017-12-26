package io.junq.examples.usercenter.client.template;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.junq.examples.test.common.client.template.AbstractRestClient;
import io.junq.examples.usercenter.client.UserCenterPaths;
import io.junq.examples.usercenter.persistence.model.Privilege;
import io.junq.examples.usercenter.util.UserCenter;

@Component
@Profile("client")
public final class PrivilegeRestClient extends AbstractRestClient<Privilege> {

    @Autowired
    protected UserCenterPaths paths;

    public PrivilegeRestClient() {
        super(Privilege.class);
    }

    // template method

    @Override
    public final String getUri() {
        return paths.getPrivilegeUri();
    }

    @Override
    public final Pair<String, String> getDefaultCredentials() {
        return new ImmutablePair<String, String>(UserCenter.ADMIN_EMAIL, UserCenter.ADMIN_PASS);
    }

}
