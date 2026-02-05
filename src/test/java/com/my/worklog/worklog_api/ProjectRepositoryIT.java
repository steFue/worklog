package com.my.worklog.worklog_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class ProjectRepositoryIT {

    @Container
    static final PostgreSQLContainer<?> postgres =  new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("worklog")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        // Kopplar spring boot datasource til containern och inte lokala postgres
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        // gör tester tydliga/snabba

        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void canSaveAndLoadProject() {
        UUID id = UUID.randomUUID();

        ProjectEntity p = new ProjectEntity(id, "Test Project");

        projectRepository.save(p);

        ProjectEntity loaded = projectRepository.findById(id).orElseThrow();
        assertThat(loaded.getName()).isEqualTo("Test Project");
        assertThat(loaded.getId()).isEqualTo(id);
    }
}
