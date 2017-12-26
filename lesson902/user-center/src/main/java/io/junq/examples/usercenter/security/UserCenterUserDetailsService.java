package io.junq.examples.usercenter.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

import io.junq.examples.usercenter.persistence.model.User;
import io.junq.examples.usercenter.service.IUserService;

@Component
public class UserCenterUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserService userService;
	
    public UserCenterUserDetailsService() {
        super();
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Preconditions.checkNotNull(username);

        final User user = userService.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username was not found: " + username);
        }

        final List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            if (role != null) {
                authorities.addAll(role.getPrivileges().stream().map(priv -> new SimpleGrantedAuthority(priv.getName())).distinct().collect(Collectors.toList()));
            }
        });

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
	}

}
