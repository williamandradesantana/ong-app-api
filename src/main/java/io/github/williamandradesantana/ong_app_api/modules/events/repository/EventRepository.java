package io.github.williamandradesantana.ong_app_api.modules.events.repository;

import io.github.williamandradesantana.ong_app_api.modules.events.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {
    Page<EventEntity> findByEnabledTrue(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update EventEntity event set event.enabled = false where event.id = :id")
    void disableEvent(@Param("id") UUID id);
}
