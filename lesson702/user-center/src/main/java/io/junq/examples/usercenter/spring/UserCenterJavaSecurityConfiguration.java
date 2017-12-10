package io.junq.examples.usercenter.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import io.junq.examples.usercenter.security.UserCenterBasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@ComponentScan("io.junq.examples.usercenter.security")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class UserCenterJavaSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserCenterBasicAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			
			.anyRequest()
			.authenticated()
			
			.and()
			.httpBasic()
			.authenticationEntryPoint(authenticationEntryPoint)
			
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			
			.and()
			.csrf().disable()
			;
	}
			
}
