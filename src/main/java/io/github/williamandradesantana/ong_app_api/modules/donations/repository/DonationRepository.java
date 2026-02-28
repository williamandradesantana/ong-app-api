package io.github.williamandradesantana.ong_app_api.modules.donations.repository;

import io.github.williamandradesantana.ong_app_api.modules.donations.entity.DonationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface DonationRepository extends JpaRepository<DonationEntity, UUID> {
    Page<DonationEntity> findByEnabledTrue(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update DonationEntity donation set donation.enabled = false where donation.id = :id")
    void disableDonation(@Param("id") UUID id);
}
