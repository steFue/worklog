package com.my.worklog.worklog_api;


import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    protected TaskEntity() {

    }

    public TaskEntity(UUID id, ProjectEntity project, String title) {
        if (id == null) {
            throw new DomainValidationException("Task id must not be null");
        }
        if (project == null) {
            throw new DomainValidationException("Task must belong to a project");
        }
        if (title == null || title.isBlank()) {
            throw new DomainValidationException("Task title must not be blank");
        }
        this.id = id;
        this.project = project;
        this.title = title;
        this.status = TaskStatus.TODO;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void rename(String newTitle) {
        if (newTitle == null || newTitle.isBlank()) {
            throw new DomainValidationException("Task title must not be blank");
        }
        this.title = newTitle;
    }

    public void changeStatus(TaskStatus newStatus) {
        if (newStatus == null) {
            throw new DomainValidationException("Task status must not be null");
        }
        this.status = newStatus;
    }

}
