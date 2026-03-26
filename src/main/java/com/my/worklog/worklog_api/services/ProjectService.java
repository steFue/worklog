package com.my.worklog.worklog_api.services;

import com.my.worklog.worklog_api.domain.ProjectEntity;
import com.my.worklog.worklog_api.domain.TaskEntity;
import com.my.worklog.worklog_api.domain.TaskStatus;
import com.my.worklog.worklog_api.dto.ProjectResponse;
import com.my.worklog.worklog_api.exceptions.NotFoundException;
import com.my.worklog.worklog_api.repository.ProjectRepository;
import com.my.worklog.worklog_api.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProjectService {

    private final static Logger log = LoggerFactory.getLogger(ProjectService.class);


    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public UUID createProject(String name) {

        UUID projectId =  UUID.randomUUID();

        ProjectEntity project = new ProjectEntity(projectId, name);
        projectRepository.save(project);

        log.info("Project created projectId={}", projectId);
        return projectId;

    }


    @Transactional
    public UUID addTaskToProject(UUID projectId, String title) {

        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found: " + projectId));

        UUID taskId = UUID.randomUUID();

        project.addTask(taskId, title);
        //projectRepository.save(project);
        // No saved needed, managed entity + dirty checking withing transaction taskRepository.save(task);

        log.info("Task created projectId={} taskId={}", projectId, taskId);
        return taskId;

    }

    @Transactional
    public void changeTaskStatus(UUID projectId, UUID taskId, TaskStatus newStatus) {

        TaskEntity task = taskRepository.findByIdAndProject_Id(taskId, projectId)
                .orElseThrow(() -> new NotFoundException("Task not found: " + taskId + " for project: " + projectId));

        TaskStatus oldStatus = task.getStatus();

        task.changeStatus(newStatus);
        // No saved needed, managed entity + dirty checking withing transaction taskRepository.save(task);
        log.info("Task status changed projectId={} taskId={} from={} to={}", projectId, taskId, oldStatus, newStatus);
    }

    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(UUID projectId) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found: " + projectId));
        return new ProjectResponse(projectId, project.getName());
    }
}
