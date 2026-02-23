package io.github.williamandradesantana.ong_app_api.modules.projects.enums;

public enum ProjectStatus {
    DRAFT, ACTIVE, COMPLETED, CANCELLED;

    public static ProjectStatus fromString(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Project status invalid! " + value);

        for (ProjectStatus status : ProjectStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) return status;
        }

        throw new IllegalArgumentException("Project status invalid! " + value);
    }
}
