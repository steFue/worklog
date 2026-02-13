package com.my.worklog.worklog_api.exceptions;

public class NotFoundException extends RuntimeException
{
    public NotFoundException(String message) {
        super(message);
    }
}
