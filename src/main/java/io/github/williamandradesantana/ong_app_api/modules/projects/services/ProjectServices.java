package io.github.williamandradesantana.ong_app_api.modules.projects.services;

import io.github.williamandradesantana.ong_app_api.exceptions.RequiredObjectIsNullException;
import io.github.williamandradesantana.ong_app_api.exceptions.ResourceNotFoundException;
import io.github.williamandradesantana.ong_app_api.modules.projects.controllers.ProjectController;
import io.github.williamandradesantana.ong_app_api.modules.projects.data.dto.ProjectRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.projects.data.dto.ProjectResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.projects.entity.ProjectEntity;
import io.github.williamandradesantana.ong_app_api.modules.projects.mapper.ProjectMapper;
import io.github.williamandradesantana.ong_app_api.modules.projects.repository.ProjectRepository;
import io.github.williamandradesantana.ong_app_api.modules.projects.validators.ProjectValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProjectServices {

    @Autowired
    private ProjectRepository repository;
    @Autowired
    private ProjectValidator validator;
    @Autowired
    private ProjectMapper mapper;

    public Page<ProjectResponseDTO> findAll(Pageable pageable) {
        var projects = repository.findByEnabledTrue(pageable);
        var projectsWithLinks = projects.map((project) -> {
           var dto = mapper.toResponseDTO(project);
           addHateoasLinks(dto);
           return dto;
        });

        return projectsWithLinks;
    }

    public ProjectResponseDTO findById(UUID id) {
        var project = findEnabledProjectOrThrow(id);
        return toResponseDTOWithLinks(project);
    }

    public ProjectResponseDTO createProject(ProjectRequestDTO requestDTO) {

        if (requestDTO == null) throw new RequiredObjectIsNullException();

        validator.validateProject(requestDTO);

        var entity = mapper.toEntity(requestDTO);
        var savedProject = repository.save(entity);

        return mapper.toResponseDTO(savedProject);
    }

    public ProjectResponseDTO updateProject(UUID id, ProjectRequestDTO requestDTO) {
        if (requestDTO == null) throw new RequiredObjectIsNullException();

        validator.validateProject(requestDTO);

        var entity = mapper.toEntity(requestDTO);

        entity.setName(entity.getName());
        entity.setDescription(entity.getDescription());
        entity.setProjectStatus(requestDTO.getProjectStatus());
        entity.setGoalAmount(requestDTO.getGoalAmount());
        entity.setCurrentAmount(requestDTO.getCurrentAmount());
        entity.setStartDate(requestDTO.getStartDate());
        entity.setEndDate(requestDTO.getEndDate());

        var savedProject = repository.save(entity);
        return toResponseDTOWithLinks(entity);
    }

    @Transactional
    public void disableProject(UUID id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        repository.disabledProject(id);

        var entity = repository.findById(id).get();
        var dto = mapper.toResponseDTO(entity);
        addHateoasLinks(dto);
    }

    public void deleteProject(UUID id) {
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        repository.delete(entity);
    }

    private ProjectEntity findEnabledProjectOrThrow(UUID id) {
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!entity.getEnabled()) throw new ResourceNotFoundException("User not found");
        return entity;
    }

    private ProjectResponseDTO toResponseDTOWithLinks(ProjectEntity entity) {
        var dto = mapper.toResponseDTO(entity);
        addHateoasLinks(dto);
        return dto;
    }

    private void addHateoasLinks(ProjectResponseDTO dto) {
        dto.add(linkTo(methodOn(ProjectController.class).findAll(1, 12, "asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(ProjectController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(ProjectController.class).disableProject(dto.getId())).withRel("disableProject").withType("PATCH"));
        dto.add(linkTo(methodOn(ProjectController.class).disableProject(dto.getId())).withRel("deleteProject").withType("DELETE"));
    }
}
