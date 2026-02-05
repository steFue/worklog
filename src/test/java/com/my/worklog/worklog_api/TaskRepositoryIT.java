package com.my.worklog.worklog_api;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class TaskRepositoryIT {

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
    TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void findByProjectId_returnsOnlyTasksForThatProject() {

        UUID p1 = UUID.randomUUID();
        UUID p2 = UUID.randomUUID();

        ProjectEntity project1 = new ProjectEntity(p1, "Project 1");
        ProjectEntity project2 = new ProjectEntity(p2, "Project 2");

        project1.addTask(UUID.randomUUID(), "Task A");
        project1.addTask(UUID.randomUUID(), "Task B");
        project2.addTask(UUID.randomUUID(), "Task C");

        projectRepository.save(project1);
        projectRepository.save(project2);

        List<TaskEntity> tasksForP1 = taskRepository.findByProjectId(p1);

        assertThat(tasksForP1).hasSize(2);
        assertThat(tasksForP1)
                .extracting(TaskEntity::getTitle)
                .containsExactlyInAnyOrder("Task A", "Task B");

    }
}
