package io.github.williamandradesantana.ong_app_api.modules.user_roles.mapper;

import io.github.williamandradesantana.ong_app_api.modules.roles.entity.RoleEntity;
import io.github.williamandradesantana.ong_app_api.modules.user_roles.data.dto.UserRolesRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.user_roles.data.dto.UserRolesResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.user_roles.entity.UserRolesEntity;
import io.github.williamandradesantana.ong_app_api.modules.users.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserRolesMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Mapping(target = "updatedAt", source = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    UserRolesResponseDTO toResponseDTO(UserRolesEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "roleId", target = "role")
    UserRolesEntity toEntity(UserRolesRequestDTO dto);

    default UserEntity mapUser(UUID userId) {
        if (userId == null) return null;

        var user = new UserEntity();
        user.setId(userId);
        return user;
    }

    default RoleEntity mapRole(UUID roleId) {
        if (roleId == null) return null;

        var role = new RoleEntity();
        role.setId(roleId);
        return role;
    }
}
