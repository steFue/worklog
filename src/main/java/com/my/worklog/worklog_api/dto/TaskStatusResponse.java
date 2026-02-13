package com.my.worklog.worklog_api.dto;

import com.my.worklog.worklog_api.domain.TaskStatus;

import java.util.UUID;

public record TaskStatusResponse(UUID id, TaskStatus status) {
}
