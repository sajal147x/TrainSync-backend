package com.trainSync.TrainSync.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * Author: Sajal Gupta
 * Date: 2025-11-06
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // allow CORS for all endpoints
                        .allowedOrigins(
                            "http://localhost:8081",   // Expo Web
                            "http://192.168.1.100:19006", // Expo Web via LAN IP
                            "http://localhost:8080",      // optional for local testing
                            "http://192.168.1.100:8080"   // optional for mobile device testing
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
