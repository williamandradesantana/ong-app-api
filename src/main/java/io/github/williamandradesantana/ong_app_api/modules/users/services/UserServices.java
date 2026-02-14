package io.github.williamandradesantana.ong_app_api.modules.users.services;

import io.github.williamandradesantana.ong_app_api.exceptions.RequiredObjectIsNullException;
import io.github.williamandradesantana.ong_app_api.modules.users.data.dto.UserRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.users.data.dto.UserResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.users.mapper.UserMapper;
import io.github.williamandradesantana.ong_app_api.modules.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        if (requestDTO == null) throw new RequiredObjectIsNullException();

        var entity = mapper.toEntity(requestDTO);
        entity.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        var savedUser = repository.save(entity);

        return mapper.toResponseDTO(savedUser);
    }
}
