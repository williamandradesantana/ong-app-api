package io.github.williamandradesantana.ong_app_api.modules.users.mapper;

import io.github.williamandradesantana.ong_app_api.modules.users.data.dto.UserRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.users.data.dto.UserResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.users.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(UserRequestDTO requestDTO);
    UserRequestDTO toRequestDTO(UserEntity entity);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Mapping(target = "updatedAt", source = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    UserResponseDTO toResponseDTO(UserEntity entity);
}
