package com.my.worklog.worklog_api.dto;

import jakarta.validation.constraints.NotBlank;


public record CreateTaskRequest(@NotBlank String title) {
}
