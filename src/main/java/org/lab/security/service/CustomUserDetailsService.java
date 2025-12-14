package org.lab.security.service;

import lombok.RequiredArgsConstructor;
import org.lab.projectmember.persistence.repository.ProjectMemberRepository;
import org.lab.security.model.CustomUserDetails;
import org.lab.user.persistence.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Пользователь с именем %s не найден", username));
        }

        var user = userOpt.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        var projectPermissions = projectMemberRepository.findAllByUser_Id(user.getId());
        for (var member : projectPermissions) {
            var projectName = member.getProject().getName();
            var role = member.getRole().name();

            authorities.add(
                    new SimpleGrantedAuthority(
                            "PROJECT:" + projectName + ":" + role
                    )
            );
        }
        return CustomUserDetails.fromEntity(user, authorities);
    }

    public CustomUserDetails getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalStateException("Пользователь не аутентифицирован");
        }

        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new IllegalStateException("Некорректные данные о пользователе");
        }

        return (CustomUserDetails) authentication.getPrincipal();
    }

}
