package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.Token;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.repos.TokenRepository;
import com.example.techmasterpi.security.AuthenticationRequest;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenService implements ITokenService {

    @Autowired
    TokenRepository tokenRepository;

    public void saveToken(Token token){
            tokenRepository.save(token);
    }

    public Optional<Token> getToken(String token) {
        return tokenRepository.findByToken(token);
    }
    public int setConfirmedAt(String token) {
        return tokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
    public Boolean isTokenValid(String jwt, User user, Token token) {
        final String userEmail = user.getEmail();
        return (userEmail.equals(user.getUsername())) &&!token.getExpiredat().isBefore(LocalDateTime.now())
                && token.getToken() == jwt;
    }
}
