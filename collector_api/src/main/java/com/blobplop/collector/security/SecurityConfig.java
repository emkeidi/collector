package com.blobplop.collector.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationConfiguration authConfig) throws
                                                                                   Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz ->
                        {
                            try {
                                authz
                                        .requestMatchers(HttpMethod.GET, "/healthy")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.POST, "/authenticate")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.POST, "/create_account")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.POST, "/refresh_token")
                                        .authenticated()
                                        .requestMatchers(HttpMethod.GET, "/api/buyer", "/api/buyer/*",
                                                "/api/buyer/user/*")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.PUT, "/api/buyer/*", "/api/buyer/bank" +
                                                "/*")
                                        .authenticated()
                                        .requestMatchers(HttpMethod.POST, "/api/buyer")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/realtor", "/api/realtor/*")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.POST, "/api/house")
                                        .hasAuthority("ADMIN")
                                        .requestMatchers(HttpMethod.PUT, "/api/house/*")
                                        .hasAnyAuthority("ADMIN", "USER")
                                        .requestMatchers(HttpMethod.DELETE, "/api/house/*")
                                        .hasAuthority("ADMIN")
                                        .requestMatchers("/**").denyAll();

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .addFilter(new JwtRequestFilter(authenticationManager(authConfig), converter))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();


    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        try {
            return config.getAuthenticationManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}