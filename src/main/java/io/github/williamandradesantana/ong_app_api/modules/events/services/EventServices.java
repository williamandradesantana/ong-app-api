package io.github.williamandradesantana.ong_app_api.modules.events.services;

import io.github.williamandradesantana.ong_app_api.exceptions.RequiredObjectIsNullException;
import io.github.williamandradesantana.ong_app_api.exceptions.ResourceNotFoundException;
import io.github.williamandradesantana.ong_app_api.modules.events.controllers.EventController;
import io.github.williamandradesantana.ong_app_api.modules.events.data.dto.EventRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.events.data.dto.EventResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.events.entity.EventEntity;
import io.github.williamandradesantana.ong_app_api.modules.events.mapper.EventMapper;
import io.github.williamandradesantana.ong_app_api.modules.events.repository.EventRepository;
import io.github.williamandradesantana.ong_app_api.modules.projects.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class EventServices {

    private final EventRepository eventRepository;
    private final ProjectRepository projectRepository;
    private final EventMapper mapper;

    public EventServices(EventRepository eventRepository, ProjectRepository projectRepository, EventMapper mapper) {
        this.eventRepository = eventRepository;
        this.projectRepository = projectRepository;
        this.mapper = mapper;
    }

    public Page<EventResponseDTO> findAll(Pageable pageable) {
        var events = eventRepository.findByEnabledTrue(pageable);
        var eventsWithLinks = events.map((event) -> {
           var dto = mapper.toResponseDTO(event);
           addHateoasLinks(dto);
           return dto;
        });

        return eventsWithLinks;
    }

    public EventResponseDTO findById(UUID id) {
        var event = findEnabledEventOrThrow(id);
        return toResponseDTOWithLinks(event);
    }

    public EventResponseDTO createEvent(EventRequestDTO requestDTO) {
        if (requestDTO == null) throw new RequiredObjectIsNullException();

        var project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + requestDTO.getProjectId()));

        var event = mapper.toEntity(requestDTO);
        event.setProject(project);

        var savedEvent = eventRepository.save(event);

        return mapper.toResponseDTO(savedEvent);
    }

    public EventResponseDTO updateEvent(UUID id, EventRequestDTO requestDTO) {
        if (requestDTO == null) throw new RequiredObjectIsNullException();

        var entity = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

        var project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + requestDTO.getProjectId()));

        entity.setName(requestDTO.getName());
        entity.setDescription(requestDTO.getDescription());
        entity.setEventDate(requestDTO.getEventDate());
        entity.setLocation(requestDTO.getLocation());
        entity.setProject(project);

        var updated = eventRepository.save(entity);
        return toResponseDTOWithLinks(updated);
    }

    @Transactional
    public void disableEvent(UUID id) {
        eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        eventRepository.disableEvent(id);

        var entity = eventRepository.findById(id).get();
        var dto = mapper.toResponseDTO(entity);
        addHateoasLinks(dto);
    }

    public void deleteEvent(UUID id) {
        var event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        eventRepository.delete(event);
    }

    private EventEntity findEnabledEventOrThrow(UUID id) {
        var entity = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event not found"));
        if (!entity.getEnabled()) throw new ResourceNotFoundException("Event not found");
        return entity;
    }

    private EventResponseDTO toResponseDTOWithLinks(EventEntity entity) {
        var dto = mapper.toResponseDTO(entity);
        addHateoasLinks(dto);
        return dto;
    }

    private void addHateoasLinks(EventResponseDTO dto) {
        dto.add(linkTo(methodOn(EventController.class).findAll(1, 12, "asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(EventController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(EventController.class).disableEvent(dto.getId())).withRel("disableEvent").withType("PATCH"));
        dto.add(linkTo(methodOn(EventController.class).deleteEvent(dto.getId())).withRel("deleteEvent").withType("DELETE"));
    }
}
