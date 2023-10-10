package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.model.UserDTO;
import com.example.techmasterpi.security.RegisterRequest;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface IUserService {

   User addUser (User user);
    void updatePassword(@NonNull HttpServletRequest request, String oldPassword, String newPassword);

    List<UserDTO> retrieveAllUsers();

    void updateBlockedTime(int userId);

    User findUserByPhone(int phone);
    User updateOwnAccount(RegisterRequest u, HttpServletRequest request);

    UserDTO retrieveUserById(Integer userid);


    void deleteUser(Integer userid);
      void updateUser(User u);

    int enableUser(String email);
    User getUserByToken(@NonNull HttpServletRequest request);

 void sendEmail(String to, String subject, String text) throws MessagingException;

 UserDTO getUserByEmail(String email);

    String genratOtp();
}
