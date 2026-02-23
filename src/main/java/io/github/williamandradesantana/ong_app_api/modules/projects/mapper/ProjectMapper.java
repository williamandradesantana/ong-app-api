package io.github.williamandradesantana.ong_app_api.modules.projects.mapper;

import io.github.williamandradesantana.ong_app_api.modules.projects.data.dto.ProjectRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.projects.data.dto.ProjectResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.projects.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProjectEntity toEntity(ProjectRequestDTO requestDTO);

    ProjectRequestDTO toRequestDTO(ProjectEntity entity);

    ProjectResponseDTO toResponseDTO(ProjectEntity entity);
}
