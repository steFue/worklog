package com.my.worklog.worklog_api;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TaskRepositoryIT extends AbstractIntegrationTest{

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
