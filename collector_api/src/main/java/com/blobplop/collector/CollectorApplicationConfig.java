package com.blobplop.collector;

import com.blobplop.collector.security.JwtConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CollectorApplicationConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final String[] allowedOrigins;

    public CollectorApplicationConfig(@Value("${ALLOWED_ORIGINS}") String allowedOrigins) {
        if (allowedOrigins == null || allowedOrigins.isBlank()) {
            this.allowedOrigins = new String[0];
        } else {
            this.allowedOrigins = allowedOrigins.split("\\s*,\\s*");
        }
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns(allowedOrigins)
                        .allowedMethods("DELETE", "GET", "POST", "PUT");
            }
        };
    }

    @Bean
    public JwtConverter getJwtConverter() {
        return new JwtConverter();
    }


}
