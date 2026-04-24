package com.example.springboottodo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Configuration
public class DatabaseConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource(Environment environment) {
        DatabaseConnectionSettings settings = resolveSettings(environment);

        HikariDataSource dataSource = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setJdbcUrl(settings.jdbcUrl());

        if (StringUtils.hasText(settings.username())) {
            dataSource.setUsername(settings.username());
        }

        if (StringUtils.hasText(settings.password())) {
            dataSource.setPassword(settings.password());
        }

        return dataSource;
    }

    private DatabaseConnectionSettings resolveSettings(Environment environment) {
        String datasourceUrl = firstNonBlank(
                environment.getProperty("spring.datasource.url"),
                environment.getProperty("DATABASE_PUBLIC_URL"),
                environment.getProperty("DATABASE_URL"),
                "jdbc:postgresql://localhost:5432/todo_db"
        );

        String username = firstNonBlank(
                environment.getProperty("spring.datasource.username"),
                environment.getProperty("PGUSER")
        );

        String password = firstNonBlank(
                environment.getProperty("spring.datasource.password"),
                environment.getProperty("PGPASSWORD")
        );

        if (datasourceUrl.startsWith("postgresql://") || datasourceUrl.startsWith("postgres://")) {
            return fromPostgresUri(datasourceUrl, username, password);
        }

        return new DatabaseConnectionSettings(
                datasourceUrl,
                firstNonBlank(username, "postgres"),
                firstNonBlank(password, "postgres")
        );
    }

    private DatabaseConnectionSettings fromPostgresUri(String databaseUrl, String configuredUsername, String configuredPassword) {
        URI uri = URI.create(databaseUrl);
        StringBuilder jdbcUrl = new StringBuilder("jdbc:postgresql://")
                .append(uri.getHost());

        if (uri.getPort() != -1) {
            jdbcUrl.append(':').append(uri.getPort());
        }

        if (StringUtils.hasText(uri.getRawPath())) {
            jdbcUrl.append(uri.getRawPath());
        }

        if (StringUtils.hasText(uri.getRawQuery())) {
            jdbcUrl.append('?').append(uri.getRawQuery());
        }

        String username = configuredUsername;
        String password = configuredPassword;

        if (StringUtils.hasText(uri.getRawUserInfo())) {
            String[] userInfo = uri.getRawUserInfo().split(":", 2);
            username = firstNonBlank(decode(userInfo[0]), username, "postgres");

            if (userInfo.length > 1) {
                password = firstNonBlank(decode(userInfo[1]), password, "postgres");
            }
        }

        return new DatabaseConnectionSettings(
                jdbcUrl.toString(),
                firstNonBlank(username, "postgres"),
                firstNonBlank(password, "postgres")
        );
    }

    private String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (StringUtils.hasText(value)) {
                return value;
            }
        }

        return null;
    }

    private record DatabaseConnectionSettings(String jdbcUrl, String username, String password) {
    }
}
