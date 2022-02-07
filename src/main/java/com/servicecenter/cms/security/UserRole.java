package com.servicecenter.cms.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.servicecenter.cms.security.UserPermission.*;
import static java.util.stream.Collectors.toSet;

public enum UserRole {

    ADMIN(new HashSet<>(Arrays.asList(TECH_MODIFY, TECH_GET, SERVICE_CALL_GET))),
    TECHNICIAN(new HashSet<>(Arrays.asList(TECH_GET, SERVICE_CALL_GET, SERVICE_CALL_CLOSE))),
    CALLCENTER(new HashSet<>(Arrays.asList(SERVICE_CALL_ALLOCATE,SERVICE_CALL_DELETE)));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> authorities = getPermissions()
                .stream().map(Enum::name).map(SimpleGrantedAuthority::new).collect(toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
