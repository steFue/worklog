package com.my.worklog.worklog_api.controller;

import com.my.worklog.worklog_api.domain.TaskStatus;
import com.my.worklog.worklog_api.dto.*;
import com.my.worklog.worklog_api.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest req) {

        UUID id = projectService.createProject(req.name());
        ProjectResponse body = new ProjectResponse(id, req.name());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{projectId}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).body(body);
        //return ResponseEntity.created(URI.create("/api/projects/" + id)).body(body);

    }

    @PostMapping("/{projectId}/tasks")
    public ResponseEntity<TaskResponse> addTaskToProject(@PathVariable UUID projectId, @Valid @RequestBody CreateTaskRequest req) {

        UUID taskId = projectService.addTaskToProject(projectId, req.title());
        TaskResponse body = new TaskResponse(taskId, projectId, req.title(), TaskStatus.TODO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{taskId}")
                .buildAndExpand(taskId)
                .toUri();

        return ResponseEntity.created(location).body(body);

        //return ResponseEntity.created(URI.create("/api/projects/{projectId}/tasks")).body(body);
    }

    @PatchMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskStatusResponse> changeStatus(@PathVariable UUID projectId, @PathVariable UUID taskId, @Valid @RequestBody ChangeTaskStatusRequest req) {

        projectService.changeTaskStatus(projectId, taskId, req.status());
        TaskStatusResponse body = new TaskStatusResponse(taskId, req.status());

        return ResponseEntity.ok(body);

    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable UUID projectId) {
        ProjectResponse response = projectService.getProjectById(projectId);
        return ResponseEntity.ok(response);
    }

}
