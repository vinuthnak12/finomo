package com.finomo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Finomo - Personal Finance Expense Tracker API")
                        .description("A comprehensive REST API for managing personal finance expenses. " +
                                   "This API allows you to create, read, update, and delete expense records, " +
                                   "as well as perform analytics and search operations.")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Finomo Development Team")
                                .email("support@finomo.com")
                                .url("https://finomo.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server"),
                        new Server()
                                .url("https://api.finomo.com")
                                .description("Production Server")
                ));
    }
}