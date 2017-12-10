package io.junq.examples.usercenter.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Value("${signing-key:oui214hmui23o4hm1pui3o2hp4m1o3h2m1o43}")
    private String signingKey;

    @Autowired
    private UserDetailsService userDetailsService;
    
    public AuthorizationServerConfiguration() {
        super();
    }
    
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
    	final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(signingKey);
        return jwtAccessTokenConverter;
    }
    
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
    
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
    	final DefaultTokenServices tokenServices = new DefaultTokenServices();
    	tokenServices.setTokenStore(tokenStore());
    	tokenServices.setSupportRefreshToken(true);
    	return tokenServices;
    }

    // config

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
        .tokenStore(tokenStore())
        .userDetailsService(userDetailsService)
        .authenticationManager(authenticationManager)
        .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
        .accessTokenConverter(accessTokenConverter());
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
        	.withClient("live-test")
        	.secret("bGl2ZS10ZXN0")
        	.authorizedGrantTypes("password" , "refresh_token")
        	.refreshTokenValiditySeconds(3600 * 24)
        	.scopes("user-center")
        	.autoApprove("user-center")
        	.accessTokenValiditySeconds(3600);
    }
    
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("permitAll()");
		super.configure(security);
	}
}
