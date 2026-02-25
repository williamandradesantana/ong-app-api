package io.github.williamandradesantana.ong_app_api.modules.events.mapper;

import io.github.williamandradesantana.ong_app_api.modules.events.data.dto.EventRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.events.data.dto.EventResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.events.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Mapping(target = "updatedAt", source = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    EventResponseDTO toResponseDTO(EventEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "project", ignore = true)
    EventEntity toEntity(EventRequestDTO requestDTO);
}
