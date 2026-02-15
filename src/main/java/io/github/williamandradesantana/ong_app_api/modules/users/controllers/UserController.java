package io.github.williamandradesantana.ong_app_api.modules.users.controllers;

import io.github.williamandradesantana.ong_app_api.modules.users.data.dto.UserRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.users.data.dto.UserResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.users.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServices services;

    @GetMapping("/")
    public Page<UserResponseDTO> getAllUsers(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "5", required = false) int size
    ) {
        return services.findAll(page, size);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable UUID id) {
         return services.findById(id);
    }

    @PostMapping("/")
    public UserResponseDTO createUser(@RequestBody UserRequestDTO requestDTO) {
        return services.createUser(requestDTO);
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@PathVariable("id") UUID id, @RequestBody UserRequestDTO requestDTO) {
        return services.updateUser(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        services.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
