package com.example.techmasterpi.Controller;

import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.security.AuthenticationResponse;
import com.example.techmasterpi.security.RegisterRequest;
import com.example.techmasterpi.service.AuthenticationService;
import com.example.techmasterpi.service.IUserService;
import com.example.techmasterpi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.transaction.Transactional;
import java.io.IOException;
@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/auth/register")
public class RegistrationController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserService userService;


    @PostMapping("/withoutimage")
    @Transactional
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User user) throws MessagingException, IOException {
        return ResponseEntity.ok(authenticationService.register(user)) ;

    }

    @GetMapping("/getToken/{jwt}")
    public String getToken(@PathVariable("jwt") String jwt) {
        return authenticationService.confirmToken(jwt);

    }


}
