package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.Token;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.security.AuthenticationRequest;

import java.util.Optional;

public interface ITokenService {
    void saveToken(Token token);
    Optional<Token> getToken(String token);

    int setConfirmedAt(String token);
    Boolean isTokenValid(String jwt, User user,Token token);
}
