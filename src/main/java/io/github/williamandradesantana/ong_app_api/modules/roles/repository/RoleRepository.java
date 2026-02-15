package io.github.williamandradesantana.ong_app_api.modules.roles.repository;


import io.github.williamandradesantana.ong_app_api.modules.roles.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
}
