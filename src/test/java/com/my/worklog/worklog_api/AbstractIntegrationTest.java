package com.my.worklog.worklog_api;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
//@Testcontainers
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    //@Container
    static final PostgreSQLContainer<?> postgres =  new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("worklog")
            .withUsername("test")
            .withPassword("test");

    static {
        postgres.start();
    }

    @DynamicPropertySource
    static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        // Kopplar spring boot datasource till containern och inte lokala postgres
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        // Flyway äger schemat, Hibernate ska validera mapping.
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }
}
