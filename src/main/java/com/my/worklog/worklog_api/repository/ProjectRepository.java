package com.my.worklog.worklog_api.repository;

import com.my.worklog.worklog_api.domain.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {
}
