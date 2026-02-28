package io.github.williamandradesantana.ong_app_api.modules.donations.controllers;

import io.github.williamandradesantana.ong_app_api.modules.donations.data.dto.DonationRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.donations.data.dto.DonationResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.donations.services.DonationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    @Autowired
    private DonationServices services;

    @GetMapping(
        value = "/",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<Page<DonationResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortedDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        var pageable = PageRequest.of(page, size, Sort.by(sortedDirection, "createdAt"));
        return ResponseEntity.ok(services.findAll(pageable));
    }

    @GetMapping(
        value = "/{id}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<DonationResponseDTO> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(services.findById(id));
    }

    @PostMapping(
        value = "/",
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        },
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<DonationResponseDTO> createDonation(@RequestBody DonationRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(services.createDonation(requestDTO));
    }

    @PutMapping(
        value = "/{id}",
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        },
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<DonationResponseDTO> updateDonation(
            @PathVariable("id") UUID id,
            @RequestBody DonationRequestDTO requestDTO) {
        return ResponseEntity.ok(services.updateDonation(id, requestDTO));
    }

    @PatchMapping(
        value = "/{id}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public ResponseEntity<Void> disableDonation(@PathVariable("id") UUID id) {
        services.disableDonation(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable("id") UUID id) {
        services.deleteDonation(id);
        return ResponseEntity.noContent().build();
    }
}

