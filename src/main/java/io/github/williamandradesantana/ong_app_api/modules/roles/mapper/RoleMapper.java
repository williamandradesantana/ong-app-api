package io.github.williamandradesantana.ong_app_api.modules.roles.mapper;

import io.github.williamandradesantana.ong_app_api.modules.roles.data.dto.RoleRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.roles.data.dto.RoleResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.roles.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleEntity toEntity(RoleRequestDTO requestDTO);
    RoleRequestDTO toRequestDTO(RoleEntity entity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    RoleResponseDTO toResponseDTO(RoleEntity entity);
}
