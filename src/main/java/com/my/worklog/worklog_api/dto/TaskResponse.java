package com.my.worklog.worklog_api.dto;

import com.my.worklog.worklog_api.domain.TaskStatus;

import java.util.UUID;

public record TaskResponse(UUID id, UUID projectId, String title, TaskStatus status) {
}
