package io.github.williamandradesantana.ong_app_api.modules.donations.mapper;

import io.github.williamandradesantana.ong_app_api.modules.donations.data.dto.DonationRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.donations.data.dto.DonationResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.donations.entity.DonationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DonationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "user", ignore = true)
    DonationEntity toEntity(DonationRequestDTO requestDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Mapping(target = "updatedAt", source = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    DonationResponseDTO toResponse(DonationEntity entity);
}
