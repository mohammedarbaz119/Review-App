package com.example.movieapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/")
                        .authenticated().anyRequest().permitAll()
                )
                .formLogin(withDefaults())
                .sessionManagement((sessions) -> sessions
                        .sessionConcurrency((concurrency) -> concurrency
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(true)
                        )
                );
        // @formatter:on
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =User.withUsername("user").password(passwordEncoder().encode("password")).authorities("USER").build();
//        User.builder().username("user").password("password").roles("USER")
//                .build();

        return new InMemoryUserDetailsManager(user);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
