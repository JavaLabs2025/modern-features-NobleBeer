package org.lab.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.lab.projectmember.model.ProjectRole;
import org.lab.user.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public static CustomUserDetails fromEntity(UserEntity user, List<GrantedAuthority> authorities) {
        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPasswordHash(),
                authorities
        );
    }

    public Set<ProjectRole> getProjectRoles(String projectName) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> a.startsWith("PROJECT:" + projectName + ":"))
                .map(a -> a.substring(("PROJECT:" + projectName + ":").length()))
                .map(ProjectRole::valueOf)
                .collect(Collectors.toSet());
    }

    public boolean hasProjectAccess(String projectName) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.startsWith("PROJECT:" + projectName + ":"));
    }
}
