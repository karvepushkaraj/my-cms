package com.servicecenter.cms.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    void addNewUser(String username, String password, String technicianId);

    void deleteUser(String technicianId);
}
