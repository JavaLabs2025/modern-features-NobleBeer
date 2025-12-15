package org.lab.security;

import java.util.Collections;

import org.lab.infrastructure.DataFactory;
import org.lab.security.model.CustomUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserDetailsBodyFactory {

    public static CustomUserDetails.CustomUserDetailsBuilder getUserDetailsWithPersistenceIdentifiersAndRole(String role) {
        return getUserDetailsWithoutPersistenceIdentifiers()
                .id(1L)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(role)));
    }

    public static CustomUserDetails.CustomUserDetailsBuilder getUserDetailsWithoutPersistenceIdentifiers() {
        return CustomUserDetails.builder()
                .username(DataFactory.USERNAME)
                .password(DataFactory.PASSWORD_HASH);
    }
}
