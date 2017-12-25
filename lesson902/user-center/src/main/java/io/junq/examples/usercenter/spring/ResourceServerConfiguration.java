package io.junq.examples.usercenter.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan({ "io.junq.examples.usercenter.security" })
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    public ResourceServerConfiguration() {
        super();
    }

    @Bean
    public AuthenticationProvider authProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }
    
    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.
        	authorizeRequests()
        	.antMatchers("/swagger-ui.html",
        				"/swagger-resources/**",
        				"/webjars/**",
        				"/v2/**",
        				"/management/**",
        				"/metric-graph-data")
        		.permitAll() // 允许swagger2相关URL可以被访问
        	.anyRequest().authenticated()
        .and().
        	sessionManagement()
        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().
        	csrf().disable();
    }

}
