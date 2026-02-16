package io.github.williamandradesantana.ong_app_api.modules.user_roles.services;

import io.github.williamandradesantana.ong_app_api.exceptions.BusinessException;
import io.github.williamandradesantana.ong_app_api.exceptions.ResourceNotFoundException;
import io.github.williamandradesantana.ong_app_api.modules.roles.repository.RoleRepository;
import io.github.williamandradesantana.ong_app_api.modules.user_roles.data.dto.UserRolesRequestDTO;
import io.github.williamandradesantana.ong_app_api.modules.user_roles.data.dto.UserRolesResponseDTO;
import io.github.williamandradesantana.ong_app_api.modules.user_roles.entity.UserRolesEntity;
import io.github.williamandradesantana.ong_app_api.modules.user_roles.mapper.UserRolesMapper;
import io.github.williamandradesantana.ong_app_api.modules.user_roles.repository.UserRolesRepository;
import io.github.williamandradesantana.ong_app_api.modules.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserRolesServices {
    @Autowired
    private UserRolesRepository userRolesRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRolesMapper userRolesMapper;

    public UserRolesResponseDTO create(UserRolesRequestDTO requestDTO) {
        var user = userRepository
                .findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var role = roleRepository
                .findById(requestDTO.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        boolean alreadyExists = userRolesRepository.existsByUserIdAndRoleId(user.getId(), role.getId());
        if (alreadyExists) throw new BusinessException("User already has this role");

        UserRolesEntity entity = userRolesMapper.toEntity(requestDTO);
        UserRolesEntity savedUserRole = userRolesRepository.save(entity);

        return userRolesMapper.toResponseDTO(savedUserRole);
    }

    public List<UserRolesEntity> findEnabledRolesByUserId(UUID userId) {
        return userRolesRepository.findAllByUserIdAndRoleEnabledTrue(userId);
    }
}
