package com.my.worklog.worklog_api;

import com.my.worklog.worklog_api.domain.ProjectEntity;
import com.my.worklog.worklog_api.exceptions.DomainValidationException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProjectEntityTest {

    @Test
    void constructor_throws_whenNameIsBlank() {
        UUID id = UUID.randomUUID();

        assertThrows(DomainValidationException.class, () ->
                new ProjectEntity(id,""));
    }

    @Test
    void rename_throws_whenNameIsBlank() {
        UUID id = UUID.randomUUID();
        ProjectEntity project = new ProjectEntity(id, "Valid name");

        assertThrows(DomainValidationException.class, () ->
                project.rename( "  "));
    }
}
