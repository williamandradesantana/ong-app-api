package io.github.williamandradesantana.ong_app_api.modules.roles.controllers;

import io.github.williamandradesantana.ong_app_api.modules.roles.data.dto.RoleRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.roles.data.dto.RoleResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.roles.services.RoleServices;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleServices services;

    public RoleController(RoleServices services) {
        this.services = services;
    }

    @GetMapping("/")
    public Page<RoleResponseDTO> findAll(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "5", required = false) int size
    ) {
        return services.findAll(page, size);
    }

    @PostMapping("/")
    public RoleResponseDTO createRole(@RequestBody RoleRequestDTO requestDTO) {
        return services.createRole(requestDTO);
    }
}
