package com.example.techmasterpi.service;

import com.example.techmasterpi.config.EmailAlreadyExistsException;
import com.example.techmasterpi.config.SecuritConfig;
import com.example.techmasterpi.config.UserNotFoundException;
import com.example.techmasterpi.domain.Token;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.model.roletype;
import com.example.techmasterpi.repos.UserRepository;
import com.example.techmasterpi.security.AuthenticationRequest;
import com.example.techmasterpi.security.AuthenticationResponse;
import com.example.techmasterpi.security.RegisterRequest;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.hibernate.type.LocalDateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private DeliveryService deliveryService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    SecuritConfig securityConfig;
    @Autowired
    ITokenAuthService tokenAuthService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IUserService userService;

    @Autowired
    ITokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public AuthenticationResponse register(  User user) throws MessagingException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        user.setTimeBlocked(LocalDateTime.now());
        userRepository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles().toString());
        String firstname = user.getFirstname();
        claims.put("firstname" , firstname);
        String lastname = user.getFirstname();
        claims.put("lastname" , lastname);
        String profilepicture = user.getProfilepicture();
        claims.put("profilepicture" , profilepicture);
        int phone = user.getPhone();
        claims.put("phone" , phone);
        String code = userService.genratOtp();
       claims.put("code", code);

        userService.sendEmail(user.getEmail(), "votre code de verficetion est ",code);
        var jwtToken = tokenAuthService.generateToken(claims, user);

        return AuthenticationResponse.builder().token(jwtToken)
                .build();
    }
    @Transactional
    public String confirmToken(String token) {
        Token confirmationToken = tokenService.getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));
        LocalDateTime expiredAt = confirmationToken.getExpiredat();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        tokenService.setConfirmedAt(token);
        userService.updateBlockedTime(confirmationToken.getUser().getUserid());
        userService.enableUser(
                confirmationToken.getUser().getEmail());

        return "confirmed";
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
        var user = userRepository.findByEmail(authRequest.getEmail()).orElse(null);
        if(LocalDateTime.now().isAfter(user.getTimeBlocked()) &&  passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            user.setEnabled(true);
            user.setFailedloginattempts(0);
            userRepository.save(user);
            if (user != null && user.isEnabled() && passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                user.setLastLoggedIn(LocalDateTime.now());
                userRepository.save(user);
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
                Map<String, Object> claims = new HashMap<>();
                claims.put("roles", user.getRoles().toString());
                String userid = String.valueOf(user.getUserid());
                claims.put("userid",userid);
                String firstname = user.getFirstname();
                claims.put("firstname" , firstname);
                String lastname = user.getLastname();
                claims.put("lastname" , lastname);
                String address = user.getAddress();
                claims.put("address" , address);
                int phone = user.getPhone();
                claims.put("phone" , phone);
                String profilepicture = user.getProfilepicture();
                claims.put("profilepicture" , profilepicture);

                var jwtToken = tokenAuthService.generateToken(claims, user);
                return AuthenticationResponse.builder().token(jwtToken)
                        .build();
            }
        }
        return  blockedUser(authRequest);
    }

    public void ForgotPassword(@NonNull HttpServletRequest request, String password){
      User user = userService.getUserByToken(request);
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }



    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        };
    }
    public static String saveImage(MultipartFile image, RegisterRequest request) throws IOException {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        Path path = Paths.get("uploads");
        Files.createDirectories(path);
        try (InputStream inputStream = image.getInputStream()) {
            Path filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            request.setProfilepicture(filePath.toString());
            return filePath.toString();
        } catch (IOException e) {
            throw new IOException("Could not save file " + fileName, e);
        }
    }

/*

    @Scheduled(fixedRate = 1000 * 60 * 60 ) // 5 minutes
    public void LogoutAfter2Hours(HttpServletRequest request, User user){
        LocalDateTime timeToDesactiveAccountAfter2hours = LocalDateTime.now();
        if(user.getLastLoggedIn().isBefore(timeToDesactiveAccountAfter2hours)){
            logout(request);
        }
    }



*/




    public AuthenticationResponse blockedUser(AuthenticationRequest request){
        var user = userRepository.findByEmail(request.getEmail()).orElse(null);
            userRepository.FailedLogin(request.getEmail());
            if (user.getFailedloginattempts() >= 2 ) {
                user.setEnabled(false);
                if( userRepository.findByEmail(request.getEmail()).isPresent()){
                    user.setTimeBlocked(LocalDateTime.now().plusMinutes(2));
                    userRepository.save(user);
                }
                userRepository.save(user);
        }
            return new AuthenticationResponse("error");
    }
    public AuthenticationResponse forgetPassword(AuthenticationRequest email) throws MessagingException {
        if(userRepository.findByEmail(email.getEmail()).isPresent()){
            User user =userRepository.findByEmail(email.getEmail()).get();
            Map<String, Object> claims = new HashMap<>();
            String code =  userService.genratOtp();
            claims.put("code", code);
            claims.put("roles",user.getRoles());
            userService.sendEmail(email.getEmail(), "votre code de verficetion est ",code);
            var JwtToken=tokenAuthService.generateToken(claims, user);
            return AuthenticationResponse.builder()
                    .token(JwtToken)
                    .build();
        }else{
            return  AuthenticationResponse.builder()
                    .token(null)
                    .build();
        }}



}
