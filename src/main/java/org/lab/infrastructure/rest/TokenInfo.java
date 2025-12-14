package org.lab.infrastructure.rest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenInfo {
    public static final String JWT_TOKEN_TYPE = "Bearer";
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
}
