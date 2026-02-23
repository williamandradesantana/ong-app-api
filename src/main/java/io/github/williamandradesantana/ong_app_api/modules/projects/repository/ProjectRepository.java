package io.github.williamandradesantana.ong_app_api.modules.projects.repository;

import io.github.williamandradesantana.ong_app_api.modules.projects.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {
    Page<ProjectEntity> findByEnabledTrue(Pageable pageable);
    boolean existsByName(String name);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ProjectEntity p SET p.enabled = false WHERE p.id = :id")
    void disabledProject(@Param("id") UUID id);
}
