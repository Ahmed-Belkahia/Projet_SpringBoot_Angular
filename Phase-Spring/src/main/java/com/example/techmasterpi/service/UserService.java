package com.example.techmasterpi.service;

import com.example.techmasterpi.config.EmailAlreadyExistsException;
import com.example.techmasterpi.config.UserNotFoundException;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.model.UserDTO;

import com.example.techmasterpi.repos.UserRepository;
import com.example.techmasterpi.security.AuthenticationResponse;
import com.example.techmasterpi.security.RegisterRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    private Session session;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    ITokenAuthService tokenAuthService;
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public User addUser(User u) {
        if (userRepository.findByEmail(u.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
       u.setTimeBlocked(LocalDateTime.now());
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRepository.save(u);
    }


    @Override
    public List<UserDTO> retrieveAllUsers() {


        return  userRepository.findAll().stream().map((user) -> mapToDTO(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO retrieveUserById(Integer userid) {
        UserDTO user=new UserDTO();
        return mapToDTO(userRepository.findById(userid).get(),user);
    }


    @Override
    public void deleteUser(Integer userid) {
        userRepository.delete(userRepository.findById(userid).get());

    }

    @Override
    public void updateUser(User u) {
        if (userRepository.findById(u.getUserid()).isPresent()){
            u.setTimeBlocked(LocalDateTime.now());
            userRepository.save(u);
        }
        else
            System.out.println("doesnt exist");
    }
    public void updatePassword(@NonNull HttpServletRequest request, String oldPassword, String newPassword) {
        User user = getUserByToken(request);
         if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password does not match");
        }
        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    public UserDTO getUserByEmail(String email) {
        UserDTO user=new UserDTO();
        return mapToDTO(userRepository.findByEmail(email).get(),user) ;
    }
    public void updateBlockedTime(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        LocalDateTime blockedTime = LocalDateTime.now();
        userRepository.updateBlockedTime(user.getUserid(), blockedTime);
    }

    @Override
    public User findUserByPhone(int phone) {
        return userRepository.findByPhone(phone);
    }


    public User updateOwnAccount(RegisterRequest u,@NonNull HttpServletRequest request) {
       User user = getUserByToken(request);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        // modifier uniquement les attributs non null
        if (u.getFirstname() != null) {
            user.setFirstname(u.getFirstname());
        }
        if (u.getLastname() != null) {
            user.setLastname(u.getLastname());
        }
        if (u.getEmail() != null) {
            user.setEmail(u.getEmail());
        }
        if (u.getProfilepicture() != null) {
            user.setProfilepicture(u.getProfilepicture());
        }
        if (u.getPhone() != 0) {
            user.setPhone(u.getPhone());
        }
        if (u.getCin() != 0) {
            user.setCin(u.getCin());
        }
        if (u.getAddress() != null) {
            user.setAddress(u.getAddress());
        }
        if (u.getRoles() != null) {
            user.setRoles(u.getRoles());
        }
        return userRepository.save(user);
    }



   public static String saveImage(MultipartFile image, User request) throws IOException {
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

    public void sendEmail(String to, String subject, String html) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);
        mailSender.send(message);
    }
    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setUserid(user.getUserid());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setPhone(user.getPhone());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAddress(user.getAddress());
        userDTO.setCin(user.getCin());
        userDTO.setProfilepicture(user.getProfilepicture());
        userDTO.setCompanyname(user.getCompanyname());
        userDTO.setRoles(user.getRoles());

        return userDTO;
     }

    public User getUserByToken(@NonNull HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        jwt = authHeader.substring(7);
        userEmail = tokenAuthService.extractUserEmail(jwt);

        return userRepository.findByEmail(userEmail).get();
    }
    @Override
    public   String genratOtp(){
        return new DecimalFormat("0000").format(new Random().nextInt(9999));

    }






}
