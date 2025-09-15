package com.example.springboottodo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${DATABASE_PUBLIC_URL:NOT_SET}")
    private String publicUrl;

    @Value("${DATABASE_URL:NOT_SET}")
    private String privateUrl;

    @PostConstruct
    public void logDatabaseConfig() {
        System.out.println("=== DATABASE CONFIGURATION DEBUG ===");
        System.out.println("🔧 Final datasource URL: " + databaseUrl);
        System.out.println("🌐 DATABASE_PUBLIC_URL: " + publicUrl);
        System.out.println("🏠 DATABASE_URL: " + privateUrl);
        System.out.println("=====================================");
    }
}