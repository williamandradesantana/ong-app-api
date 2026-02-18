package io.github.williamandradesantana.ong_app_api.modules.user_roles.controllers;

import io.github.williamandradesantana.ong_app_api.modules.user_roles.data.dto.UserRolesRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.user_roles.data.dto.UserRolesResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.user_roles.services.UserRolesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-roles")
public class UserRolesController {

    @Autowired
    private UserRolesServices services;

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
            })
    public UserRolesResponseDTO create(@RequestBody UserRolesRequestDTO requestDTO) {
        return services.create(requestDTO);
    }
}
