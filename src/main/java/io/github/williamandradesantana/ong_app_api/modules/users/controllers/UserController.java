package io.github.williamandradesantana.ong_app_api.modules.users.controllers;

import io.github.williamandradesantana.ong_app_api.modules.users.data.dto.UserRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.users.data.dto.UserResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.users.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServices services;

    @PostMapping("/")
    public UserResponseDTO createUser(@RequestBody UserRequestDTO requestDTO) {
        return services.createUser(requestDTO);
    }
}
