package io.github.williamandradesantana.ong_app_api.modules.events.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class EventRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant eventDate;

    private String location;
    private UUID projectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getEventDate() {
        return eventDate;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        EventRequestDTO that = (EventRequestDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(eventDate, that.eventDate) && Objects.equals(location, that.location) && Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(eventDate);
        result = 31 * result + Objects.hashCode(location);
        result = 31 * result + Objects.hashCode(projectId);
        return result;
    }
}
