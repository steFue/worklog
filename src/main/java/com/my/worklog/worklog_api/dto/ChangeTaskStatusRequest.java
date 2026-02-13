package com.my.worklog.worklog_api.dto;

import com.my.worklog.worklog_api.domain.TaskStatus;
import jakarta.validation.constraints.NotNull;


public record ChangeTaskStatusRequest(@NotNull TaskStatus status) {
}
