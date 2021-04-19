package com.codenation.config.security;

import com.codenation.models.User;
import com.codenation.repositories.UserRepository;
import com.codenation.services.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.yml")
public class Web extends WebSecurityConfigurerAdapter {

    @Value("${authorization.email}")
    private String USER_EMAIL;

    @Value("${authorization.password}")
    private String USER_PASSWORD;

    @Value("Administrador")
    private String USER_NAME;

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManagerBean();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth, UserRepository userRepository) throws Exception {
        if (userRepository.count() == 0) {
            userRepository.save(User.builder()
                    .email(USER_EMAIL)
                    .password(passwordEncoder().encode(USER_PASSWORD))
                    .build());
        }
        auth.userDetailsService(
                email -> userRepository.findByEmail(email).map(LoginServiceImpl::new).orElse(null)
        ).passwordEncoder(passwordEncoder());
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
