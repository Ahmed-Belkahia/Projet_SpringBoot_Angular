package com.example.techmasterpi.Controller;

import org.springframework.stereotype.Service;
import com.example.techmasterpi.config.SecuritConfig;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.security.AuthenticationRequest;
import com.example.techmasterpi.security.AuthenticationResponse;
import com.example.techmasterpi.service.AuthenticationService;
import com.example.techmasterpi.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/login")
public class AuthGoogleController {

        @GetMapping("/oauth2/google")
        public String googleLogin() {
            return "redirect:/oauth2/authorization/google";
        }

}
