package com.speechmaru.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:3000", "http://132.145.87.252",
                "https://localhost:3000", "https://132.145.87.252",
                "https://speechmaru.kro.kr", "https://www.speechmaru.kro.kr")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept")
            .allowCredentials(true);
    }
}
