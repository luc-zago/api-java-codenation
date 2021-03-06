package com.codenation.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
@PropertySource("classpath:application.yml")
public class Authorization extends AuthorizationServerConfigurerAdapter {
    @Value("${authorization.client.id}")
    private String CLIENT_ID;
    @Value("${authorization.client.secret}")
    private String CLIENT_SECRET;
    @Value("${authorization.client.grant-types}")
    private String[] CLIENT_GRANT_TYPES;
    @Value("${authorization.client.scopes}")
    private String[] CLIENT_SCOPES;

    // libera acesso as requisições do oauth2 /token_key, /check_token

    private final AuthenticationManager authenticationManager;

    @Autowired
    public Authorization(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    // definindo com quem fica armazenado os tokens para acesso
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60;
        int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 90;

        clients.inMemory()
                .withClient(CLIENT_ID)
                .secret(new BCryptPasswordEncoder().encode(CLIENT_SECRET))
                .authorizedGrantTypes(CLIENT_GRANT_TYPES)
                .scopes(CLIENT_SCOPES)
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
    }

    // definindo o gerenciador de authenticação
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }
}
