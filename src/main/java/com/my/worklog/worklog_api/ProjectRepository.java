package com.my.worklog.worklog_api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {
}
