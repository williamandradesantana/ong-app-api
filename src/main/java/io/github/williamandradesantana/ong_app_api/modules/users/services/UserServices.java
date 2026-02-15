package io.github.williamandradesantana.ong_app_api.modules.users.services;

import io.github.williamandradesantana.ong_app_api.exceptions.RequiredObjectIsNullException;
import io.github.williamandradesantana.ong_app_api.exceptions.ResourceNotFoundException;
import io.github.williamandradesantana.ong_app_api.modules.users.controllers.UserController;
import io.github.williamandradesantana.ong_app_api.modules.users.data.dto.UserRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.users.data.dto.UserResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.users.entity.UserEntity;
import io.github.williamandradesantana.ong_app_api.modules.users.mapper.UserMapper;
import io.github.williamandradesantana.ong_app_api.modules.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserServices {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserServices(UserRepository repository,
                        UserMapper mapper,
                        PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<UserResponseDTO> findAll(int page, int size) {
        var pageRequest = PageRequest.of(page, size);
        var pageResult = repository.findByEnabledTrue(pageRequest);

        return pageResult.map(this::toResponseDTOWithLinks);
    }

    public UserResponseDTO findById(UUID id) {
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return toResponseDTOWithLinks(entity);
    }

    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        validateRequest(requestDTO);

        var entity = mapper.toEntity(requestDTO);
        entity.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        var savedUser = repository.save(entity);

        return toResponseDTOWithLinks(savedUser);
    }

    public UserResponseDTO updateUser(UUID id, UserRequestDTO requestDTO) {
        validateRequest(requestDTO);

        var entity = findEnabledUserOrThrow(id);

        entity.setUsername(requestDTO.getUsername());
        entity.setEmail(requestDTO.getEmail());

        if (requestDTO.getPassword() != null && !requestDTO.getPassword().isBlank()) {
            entity.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        }
        entity.setUpdatedAt(Instant.now());

        var savedUser = repository.save(entity);
        return toResponseDTOWithLinks(savedUser);
    }

    public void deleteUser(UUID id) {
        var entity = findEnabledUserOrThrow(id);
        entity.setEnabled(false);
        repository.save(entity);
    }

    private UserEntity findEnabledUserOrThrow(UUID id) {
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!entity.getEnabled()) throw new ResourceNotFoundException("User not found");
        return entity;
    }

    private void validateRequest(UserRequestDTO requestDTO) {
        if (requestDTO == null) throw new RequiredObjectIsNullException();
    }

    private UserResponseDTO toResponseDTOWithLinks(UserEntity entity) {
        var dto = mapper.toResponseDTO(entity);
        addLinksHateoas(dto);
        return dto;
    }

    private void addLinksHateoas(UserResponseDTO responseDTO) {
        responseDTO.add(linkTo(methodOn(UserController.class).getAllUsers(1,5)).withRel("getAllUsers").withType("GET"));
        responseDTO.add(linkTo(methodOn(UserController.class).getUserById(responseDTO.getId())).withSelfRel().withType("GET"));
        responseDTO.add(linkTo(methodOn(UserController.class).updateUser(responseDTO.getId(), null)).withRel("updateUser").withType("PUT"));
        responseDTO.add(linkTo(methodOn(UserController.class).deleteUser(responseDTO.getId())).withRel("deleteUser").withType("DELETE"));
    }
}
