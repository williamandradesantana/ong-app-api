package io.github.williamandradesantana.ong_app_api.modules.roles.services;

import io.github.williamandradesantana.ong_app_api.modules.roles.data.dto.RoleRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.roles.data.dto.RoleResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.roles.mapper.RoleMapper;
import io.github.williamandradesantana.ong_app_api.modules.roles.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleServices {
    private final RoleRepository repository;
    private final RoleMapper mapper;

    public RoleServices(RoleRepository repository, RoleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<RoleResponseDTO> findAll(int page, int size) {
        var pageRequest = PageRequest.of(page, size);
        var pageResult = repository.findByEnabledTrue(pageRequest);

        return pageResult.map(mapper::toResponseDTO);
    }

    public RoleResponseDTO createRole(RoleRequestDTO requestDTO) {
        var entity = mapper.toEntity(requestDTO);
        var savedRole = repository.save(entity);
        return mapper.toResponseDTO(savedRole);
    }
}
