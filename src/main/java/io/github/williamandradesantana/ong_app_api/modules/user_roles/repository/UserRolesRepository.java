package io.github.williamandradesantana.ong_app_api.modules.user_roles.repository;

import io.github.williamandradesantana.ong_app_api.modules.user_roles.entity.UserRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRolesRepository extends JpaRepository<UserRolesEntity, UUID> {
    boolean existsByUserIdAndRoleId(UUID userId, UUID roleId);

    @Query("""
            select ur
            from UserRolesEntity ur
            join fetch ur.role r
            where ur.user.id = :userId
              and r.enabled = true
        """)
    List<UserRolesEntity> findAllByUserIdAndRoleEnabledTrue(UUID userId);
}
