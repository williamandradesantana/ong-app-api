package io.github.williamandradesantana.ong_app_api.modules.donations.services;

import io.github.williamandradesantana.ong_app_api.exceptions.RequiredObjectIsNullException;
import io.github.williamandradesantana.ong_app_api.exceptions.ResourceNotFoundException;
import io.github.williamandradesantana.ong_app_api.modules.donations.controllers.DonationController;
import io.github.williamandradesantana.ong_app_api.modules.donations.data.dto.DonationRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.donations.data.dto.DonationResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.donations.entity.DonationEntity;
import io.github.williamandradesantana.ong_app_api.modules.donations.mapper.DonationMapper;
import io.github.williamandradesantana.ong_app_api.modules.donations.repository.DonationRepository;
import io.github.williamandradesantana.ong_app_api.modules.projects.repository.ProjectRepository;
import io.github.williamandradesantana.ong_app_api.modules.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class DonationServices {
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final DonationMapper donationMapper;

    public DonationServices(DonationRepository donationRepository, UserRepository userRepository, ProjectRepository projectRepository, DonationMapper donationMapper) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.donationMapper = donationMapper;
    }

    public Page<DonationResponseDTO> findAll(Pageable pageable) {
        var donations = donationRepository.findByEnabledTrue(pageable);
        var donationsWithLinks = donations.map((donation) -> {
            var dto = donationMapper.toResponse(donation);
            addHateoasLinks(dto);
            return dto;
        });
        return donationsWithLinks;
    }

    public DonationResponseDTO findById(UUID id) {
        var entity = findEnabledDonationOrThrow(id);
        return toResponseDTOWithLinks(entity);
    }

    public DonationResponseDTO createDonation(DonationRequestDTO requestDTO) {
        if (requestDTO == null) throw new RequiredObjectIsNullException();

        var user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + requestDTO.getUserId()));
        var project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + requestDTO.getProjectId()));

        var donation = donationMapper.toEntity(requestDTO);
        donation.setProject(project);
        donation.setUser(user);

        var savedDonation = donationRepository.save(donation);
        return donationMapper.toResponse(savedDonation);
    }

    public DonationResponseDTO updateDonation(UUID id, DonationRequestDTO requestDTO) {
        if (requestDTO == null) throw new RequiredObjectIsNullException();

        var entity = donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id " + id));

        var user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + requestDTO.getUserId()));
        var project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + requestDTO.getProjectId()));

        entity.setAmount(requestDTO.getAmount());
        entity.setDonationDate(requestDTO.getDonationDate());
        entity.setDonationStatus(requestDTO.getDonationStatus());
        entity.setUser(user);
        entity.setProject(project);

        var updated = donationRepository.save(entity);
        return toResponseDTOWithLinks(updated);
    }

    @Transactional
    public void disableDonation(UUID id) {
        donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id " + id));
        donationRepository.disableDonation(id);

        var entity = donationRepository.findById(id).get();
        var dto = donationMapper.toResponse(entity);
        addHateoasLinks(dto);
    }

    public void deleteDonation(UUID id) {
        var donation = donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id " + id));
        donationRepository.delete(donation);
    }

    private DonationEntity findEnabledDonationOrThrow(UUID id) {
        var entity = donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id " + id));
        if (!entity.getEnabled()) throw new ResourceNotFoundException("Donation not found with id: " + id);
        return entity;
    }

    private DonationResponseDTO toResponseDTOWithLinks(DonationEntity entity) {
        var dto = donationMapper.toResponse(entity);
        addHateoasLinks(dto);
        return dto;
    }

    private void addHateoasLinks(DonationResponseDTO dto) {
        dto.add(linkTo(methodOn(DonationController.class).findAll(1, 12, "asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(DonationController.class).findById(dto.getId())).withRel("findById").withType("GET"));
        dto.add(linkTo(methodOn(DonationController.class).updateDonation(dto.getId(), null)).withRel("updateDonation").withType("PUT"));
        dto.add(linkTo(methodOn(DonationController.class).disableDonation(dto.getId())).withRel("disableDonation").withType("PATCH"));
        dto.add(linkTo(methodOn(DonationController.class).deleteDonation(dto.getId())).withRel("deleteDonation").withType("DELETE"));
    }
}
