package com.my.worklog.worklog_api.repository;

import com.my.worklog.worklog_api.domain.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {

    List<TaskEntity> findByProjectId(UUID projectId);

    Optional<TaskEntity> findByIdAndProjectId (UUID id, UUID projectId);
}
