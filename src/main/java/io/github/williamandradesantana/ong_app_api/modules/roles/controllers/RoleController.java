package io.github.williamandradesantana.ong_app_api.modules.roles.controllers;

import io.github.williamandradesantana.ong_app_api.modules.roles.data.dto.RoleRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.roles.data.dto.RoleResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.roles.services.RoleServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleServices services;

    public RoleController(RoleServices services) {
        this.services = services;
    }

    @PostMapping("/")
    public RoleResponseDTO createRole(@RequestBody RoleRequestDTO requestDTO) {
        return services.createRole(requestDTO);
    }
}
