package io.junq.examples.usercenter.web;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

import io.junq.examples.common.security.SpringSecurityUtil;
import io.junq.examples.usercenter.persistence.model.Privilege;
import io.junq.examples.usercenter.persistence.model.Role;
import io.junq.examples.usercenter.persistence.model.User;
import io.junq.examples.usercenter.util.UserCenterMapping;

@Controller
public class AuthenticationController {
	
	public AuthenticationController() {
        super();
    }

    // API

    @RequestMapping(method = RequestMethod.GET, value = UserCenterMapping.AUTHENTICATION)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User createAuthentication() {
        final Authentication authenticationInSpring = SpringSecurityUtil.getCurrentAuthentication();

        final Function<GrantedAuthority, Privilege> springAuthorityToPrivilegeFunction = new Function<GrantedAuthority, Privilege>() {
            @Override
            public final Privilege apply(final GrantedAuthority springAuthority) {
                return new Privilege(springAuthority.getAuthority());
            }
        };
        final Collection<Privilege> privileges = Collections2.transform(authenticationInSpring.getAuthorities(), springAuthorityToPrivilegeFunction);
        final Role defaultRole = new Role("defaultRole", Sets.<Privilege> newHashSet(privileges));

        final User authenticationResource = new User(authenticationInSpring.getName(), 
        											authenticationInSpring.getName(), 
        											(String) authenticationInSpring.getCredentials(), 
        											Sets.<Role> newHashSet(defaultRole));
        return authenticationResource;
    }

}
