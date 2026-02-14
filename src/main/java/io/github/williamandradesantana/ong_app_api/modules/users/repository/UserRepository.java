package io.github.williamandradesantana.ong_app_api.modules.users.repository;

import io.github.williamandradesantana.ong_app_api.modules.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
