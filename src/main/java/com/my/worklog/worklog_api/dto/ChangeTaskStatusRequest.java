package com.my.worklog.worklog_api.dto;

import com.my.worklog.worklog_api.domain.TaskStatus;
import jakarta.validation.constraints.NotNull;

@NotNull
public record ChangeTaskStatusRequest(TaskStatus taskStatus) {
}
