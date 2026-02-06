package com.my.worklog.worklog_api;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProjectService {


    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

//
    @Transactional
    public UUID createProject(String name) {
        UUID id =  UUID.randomUUID();
        try {
            ProjectEntity project = new ProjectEntity(id,name);
            projectRepository.save(project);
            return id;
        } catch (IllegalArgumentException e) {
            throw new DomainValidationException(e.getMessage());
        }
    }
}
