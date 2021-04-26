package com.codenation.config.security;

import com.codenation.enums.Authority;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class Resource extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/user").permitAll()
                .antMatchers(HttpMethod.GET,"/user").hasAuthority(Authority.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/level/{id}").hasAuthority(Authority.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/level").hasAuthority(Authority.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/user/{id}").hasAuthority(Authority.ADMIN.name())
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/**",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                .anyRequest().authenticated();
    }
}
