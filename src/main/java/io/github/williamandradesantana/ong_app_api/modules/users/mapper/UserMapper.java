package io.github.williamandradesantana.ong_app_api.modules.users.mapper;

import io.github.williamandradesantana.ong_app_api.modules.users.data.dto.UserRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.users.data.dto.UserResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.users.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(UserRequestDTO requestDTO);
    UserRequestDTO toRequestDTO(UserEntity entity);
    UserResponseDTO toResponseDTO(UserEntity entity);
}
