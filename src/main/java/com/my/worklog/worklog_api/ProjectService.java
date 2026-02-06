package com.my.worklog.worklog_api;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProjectService {


    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public UUID createProject(String name) {
        UUID id =  UUID.randomUUID();
        try {
            ProjectEntity project = new ProjectEntity(id,name);
            projectRepository.save(project);
            return id;
        } catch (IllegalArgumentException e) {
            throw new DomainValidationException(e.getMessage(), e);
        }
    }

    @Transactional
    public UUID addTaskToProject(UUID projectId, String title) {

        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found: " + projectId));

        UUID taskId = UUID.randomUUID();

        try {
            project.addTask(taskId, title);
            projectRepository.save(project);
            return taskId;
        } catch (IllegalArgumentException e) {
            throw new DomainValidationException(e.getMessage(), e);
        }
    }

    @Transactional
    public void changeTaskStatus(UUID projectId, UUID taskId, TaskStatus newStatus) {
        TaskEntity task = taskRepository.findByIdAndProjectId(taskId, projectId)
                .orElseThrow(() -> new NotFoundException("Task not found: " + taskId));

        try {
            task.changeStatus(newStatus);
            taskRepository.save(task);
        } catch (IllegalArgumentException e) {
            throw new DomainValidationException(e.getMessage(), e);
        }
    }
}
