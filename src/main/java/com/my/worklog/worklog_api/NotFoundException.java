package com.my.worklog.worklog_api;

public class NotFoundException extends RuntimeException
{
    public NotFoundException(String message) {
        super(message);
    }
}
