package com.my.worklog.worklog_api;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToMany (
            mappedBy = "project",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<TaskEntity> tasks = new ArrayList<>();

    protected ProjectEntity() {

    }

    public ProjectEntity(UUID id, String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Project name must not be blank");
        }
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public TaskEntity addTask(UUID taskId, String title) {
        TaskEntity task = new TaskEntity(taskId, this, title);
        tasks.add(task);
        return task;
    }

    public List<TaskEntity> getTasks() {
        return List.copyOf(tasks);
    }

    public void rename(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Project name must not be blank");
        }
        this.name = newName;
    }
}
