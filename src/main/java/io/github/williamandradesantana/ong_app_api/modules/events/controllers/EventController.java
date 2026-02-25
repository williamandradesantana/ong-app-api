package io.github.williamandradesantana.ong_app_api.modules.events.controllers;

import io.github.williamandradesantana.ong_app_api.modules.events.data.dto.EventRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.events.data.dto.EventResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.events.services.EventServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/events")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VOLUNTEER', 'ROLE_DONOR')")
public class EventController {

    private final EventServices services;

    public EventController(EventServices services) {
        this.services = services;
    }

    @GetMapping(
        value = "/",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<Page<EventResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortedDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        var pageable = PageRequest.of(page, size, Sort.by(sortedDirection, "name"));
        return ResponseEntity.ok(services.findAll(pageable));
    }

    @GetMapping(
        value = "/{id}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<EventResponseDTO> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(services.findById(id));
    }

    @PostMapping(
        value = "/",
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        },
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(services.createEvent(requestDTO));
    }


    @PutMapping(
        value = "/{id}",
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        },
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<EventResponseDTO> updateEvent(
            @PathVariable("id") UUID id, @RequestBody EventRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(services.updateEvent(id, requestDTO));
    }

    @PatchMapping(
        value = "/{id}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<Void> disableEvent(@PathVariable("id") UUID id) {
        services.disableEvent(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") UUID id) {
        services.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
