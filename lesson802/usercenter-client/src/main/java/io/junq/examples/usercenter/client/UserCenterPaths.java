package io.junq.examples.usercenter.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.junq.examples.common.client.CommonPaths;
import io.junq.examples.common.web.IUriMapper;
import io.junq.examples.usercenter.persistence.model.Privilege;
import io.junq.examples.usercenter.persistence.model.Role;
import io.junq.examples.usercenter.persistence.model.User;

@Component
@Profile("client")
public class UserCenterPaths {
	
	@Value("${http.sec.path}")
    private String secPath;
	
    @Value("${http.oauthPath}")
    private String oauthPath;

    @Autowired
    private CommonPaths commonPaths;

    @Autowired
    private IUriMapper uriMapper;

    // API

    public final String getContext() {
        return commonPaths.getServerRoot() + secPath;
    }

    public final String getRootUri() {
        return getContext() + "api/";
    }

    public final String getUserUri() {
        return getRootUri() + uriMapper.getUriBase(User.class);
    }

    public final String getRoleUri() {
        return getRootUri() + uriMapper.getUriBase(Role.class);
    }

    public final String getPrivilegeUri() {
        return getRootUri() + uriMapper.getUriBase(Privilege.class);
    }

    public final String getAuthenticationUri() {
        return getRootUri() + "authentication";
    }

    public final String getLoginUri() {
        return getContext() + "/j_spring_security_check";
    }

    public final String getOauthPath() {
        return oauthPath;
    }
}
