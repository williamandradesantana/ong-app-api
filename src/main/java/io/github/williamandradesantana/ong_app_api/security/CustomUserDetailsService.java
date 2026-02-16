package io.github.williamandradesantana.ong_app_api.security;

import io.github.williamandradesantana.ong_app_api.modules.user_roles.services.UserRolesServices;
import io.github.williamandradesantana.ong_app_api.modules.users.services.UserServices;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServices userServices;
    @Autowired
    private UserRolesServices userRolesServices;

    @NullMarked
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userServices.findByUsername(username);

        var userRoles = userRolesServices.findEnabledRolesByUserId(user.getId());

        if (userRoles.isEmpty()) {
            throw new UsernameNotFoundException("User has no roles assigned");
        }

        var authorities = userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getName()))
                .toList();

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .disabled(!user.getEnabled())
                .build();
    }
}
