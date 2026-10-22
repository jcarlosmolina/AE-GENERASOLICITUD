package com.keralty.aegenerasolicitud.security;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;

import com.keralty.aegenerasolicitud.viewmodel.LoginRequest;

@ApplicationScoped
public interface UserAuthenticationService {

    Optional<String> login(String username, String password);
    
    Optional<String> login(LoginRequest loginRequest);

    void logout(User user);
}
