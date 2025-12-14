package org.lab.security.service;

import lombok.RequiredArgsConstructor;
import org.lab.security.model.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            var user = userRepository.findByUsername(username);
            return CustomUserDetails.fromEntity(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException(String.format("Пользователь с именем %s не найден", username), e);
        }
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
