package com.example.springboottodo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "TODO List API",
                description = "API para gerenciamento de tarefas com autenticação JWT",
                version = "1.0.0",
                contact = @Contact(
                        name = "TODO API Support",
                        email = "support@todoapi.com"
                )
        ),
        servers = {
                @Server(url = "/", description = "Default Server URL")
        }
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@SpringBootApplication
public class SpringBootTodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTodoApplication.class, args);
    }

}
