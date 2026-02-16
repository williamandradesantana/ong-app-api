package io.github.williamandradesantana.ong_app_api.modules.user_roles.repository;

import io.github.williamandradesantana.ong_app_api.modules.user_roles.entity.UserRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRolesRepository extends JpaRepository<UserRolesEntity, UUID> {
    boolean existsByUserIdAndRoleId(UUID userId, UUID roleId);
}
