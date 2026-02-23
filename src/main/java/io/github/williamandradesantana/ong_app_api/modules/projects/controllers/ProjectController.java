package io.github.williamandradesantana.ong_app_api.modules.projects.controllers;

import io.github.williamandradesantana.ong_app_api.modules.projects.data.dto.ProjectRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.projects.data.dto.ProjectResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.projects.services.ProjectServices;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/projects")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VOLUNTEER', 'ROLE_DONOR')")
public class ProjectController {

    @Autowired
    private ProjectServices services;

    @GetMapping(
        value = "/",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<Page<ProjectResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        var pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
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
    public ResponseEntity<ProjectResponseDTO> findById(@PathVariable("id") UUID id) {
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
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(services.createProject(requestDTO));
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
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable("id") UUID id,
            @RequestBody ProjectRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(services.updateProject(id, requestDTO));
    }

    @PatchMapping(
        value = "/{id}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<Void> disableProject(@PathVariable("id") UUID id) {
        services.disableProject(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") UUID id) {
        services.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
