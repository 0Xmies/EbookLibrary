package com.xmies.Library.security;

import com.xmies.Library.service.userRelated.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(config ->
                config
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/library/**").permitAll()
                        .requestMatchers("/entry/**").permitAll()
                        .anyRequest().authenticated()
        )
                .formLogin(form ->
                        form
                                .loginPage("/entry/showLoginPage")
                                .loginProcessingUrl("/authenticateUser")
                                .permitAll()
                )
                .logout(logout -> logout.permitAll()
                )
                .exceptionHandling(config ->
                        config.accessDeniedPage("/entry/accessDenied")
                );

        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {

        DaoAuthenticationProvider authentication = new DaoAuthenticationProvider();

        authentication.setUserDetailsService(userService);
        authentication.setPasswordEncoder(passwordEncoder());

        return authentication;
    }
}
