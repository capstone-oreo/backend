package com.speechmaru.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${spring.origin}")
    private String ORIGIN;

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl(ORIGIN);
        return new OpenAPI().servers(List.of(server));
    }
}
