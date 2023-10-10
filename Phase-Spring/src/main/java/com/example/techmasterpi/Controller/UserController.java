package com.example.techmasterpi.Controller;


import com.example.techmasterpi.domain.Role;
import com.example.techmasterpi.model.UserDTO;
import com.example.techmasterpi.model.roletype;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.security.RegisterRequest;
import com.example.techmasterpi.service.AuthenticationService;
import com.example.techmasterpi.service.ITokenAuthService;
import com.example.techmasterpi.service.IUserService;
import com.example.techmasterpi.service.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.boot.jaxb.Origin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;
    @Autowired
    ITokenAuthService tokenAuthService;

    @Autowired
    UserService user1Service;
    @Autowired
    AuthenticationService authenticationService;


/*    @PostMapping(value = "/add-user")
    @Transactional
    public ResponseEntity<String> addUser(@RequestParam("firstname") String firstname,
                                          @RequestParam("lastname") String lastname,
                                          @RequestParam("email") String email,
                                          @RequestParam("password") String password,
                                          @RequestParam("phone") int phone,
                                          @RequestParam("cin") int cin,
                                          @RequestParam("address") String address,
                                          @RequestParam("roles") roletype roles,
                                          @RequestParam("companyname") String companyname,
                                          @RequestParam(value = "profilepicture", required = false) MultipartFile image) throws IOException {

        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setRoles(roles);
        user.setAddress(address);
        user.setCin(cin);
        user.setCompanyname(companyname);
        if (image != null && !image.isEmpty()) {
            String imagePath = UserService.saveImage(image,user);
            user.setProfilepicture(imagePath);
        }
        userService.addUser(user, user.getPassword());
        return ResponseEntity.ok("User was successfully added");
    }*/
    @PostMapping(value = "/adduserwithoutimage")
    @Transactional
    public void addUserWithoutimage(@RequestBody User user)  {
        userService.addUser(user);
    }

    @GetMapping("/retrieve-all-users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> listUsers = userService.retrieveAllUsers();
        return new ResponseEntity<List<UserDTO>>(listUsers, HttpStatus.OK);
    }

    @GetMapping("/retrieve-user/{user-id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("user-id") Integer userid) {
        UserDTO u = userService.retrieveUserById(userid);
        return new ResponseEntity<UserDTO>(u, HttpStatus.OK);
    }


    @DeleteMapping("/remove-user/{user-id}")
    @Transactional
    public ResponseEntity<String> removeUser(@PathVariable("user-id") Integer userid) {
        userService.deleteUser(userid);
        return new ResponseEntity<String>("User with '"+userid+"' has been sucessfully deleted",HttpStatus.OK);
    }
    @PutMapping("updateUser")
    @Transactional
    void updateUser(@RequestBody User u)
    {
        userService.updateUser(u);
    }

    @PutMapping("/UpdatePassword")
    @Transactional
    public void updatePassword(@NonNull HttpServletRequest request,@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword)throws MessagingException
    {
        userService.updatePassword(request,oldPassword,newPassword);
    }
    @GetMapping("/comptes/{email}")
    public ResponseEntity<UserDTO> getCompteByEmail(@PathVariable String email) {
        UserDTO user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }
    @GetMapping("/getUserById/{userid}")
    public UserDTO retrieveUserById(@PathVariable Integer userid) {
        return userService.retrieveUserById(userid);
    }
 /*   @PutMapping("/updatecomptes/{email}")
    public ResponseEntity<User> updateCompteByEmail(@PathVariable("email") String email, @RequestPart("user") RegisterRequest u, @RequestPart("image") MultipartFile image, @NonNull HttpServletRequest request) throws IOException {

        if (image != null && !image.isEmpty()) {
            String imagePath = UserService.saveImage(image,u);
            u.setProfilepicture(imagePath);

        }
        User user = userService.updateOwnAccount(u,request);
        return ResponseEntity.ok(user);
    }*/
    @PutMapping("/updateaccountwithoutimage")
    public ResponseEntity<User> updateCompteByEmail(@RequestBody RegisterRequest u,@NonNull HttpServletRequest request) throws IOException {
        User user = userService.updateOwnAccount(u,request);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/findbyphone/{phone}")
    public ResponseEntity<User> findUserByPhone(@PathVariable("phone") int phone) {
        User user = userService.findUserByPhone(phone);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }


}
