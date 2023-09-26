package com.xmies.Library.security;

import com.xmies.Library.service.userRelated.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

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

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return  authenticationProvider;
    }



    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
