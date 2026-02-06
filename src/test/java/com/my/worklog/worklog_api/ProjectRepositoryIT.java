package com.my.worklog.worklog_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProjectRepositoryIT extends AbstractIntegrationTest{

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProjectService projectService;

    @Test
    void addTaskToProject_persistsTaskLinkedToProject() {
        UUID projectId = projectService.createProject("Service IT project");

        UUID taskId = projectService.addTaskToProject(projectId, "First task");

        TaskEntity task = taskRepository.findById(taskId).orElseThrow();
        assertThat(task.getTitle()).isEqualTo("First task");
        assertThat(task.getProject().getId()).isEqualTo(projectId);
        assertThat(task.getStatus()).isEqualTo(TaskStatus.TODO);
    }

    @Test
    void canSaveAndLoadProject() {
        UUID id = UUID.randomUUID();

        ProjectEntity p = new ProjectEntity(id, "Test Project");

        projectRepository.save(p);

        ProjectEntity loaded = projectRepository.findById(id).orElseThrow();
        assertThat(loaded.getName()).isEqualTo("Test Project");
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void changeTaskStatus_updateStatusInDatabase() {
        UUID projectId = projectService.createProject("Test Project");
        UUID taskId = projectService.addTaskToProject(projectId, "Task to change");

        projectService.changeTaskStatus(projectId, taskId, TaskStatus.IN_PROGRESS);

        TaskEntity reloaded = taskRepository.findById(taskId).orElseThrow();
        assertThat(reloaded.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
    }
}
