package com.example.techmasterpi.model;

import com.twilio.rest.chat.v1.service.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
public class UserDTO {

    private Integer userid;

    @Size(max = 255)
    private String firstname;

    @Size(max = 255)
    private String lastname;

    private Integer phone;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String password;

    @Size(max = 255)
    private String address;

    private Integer cin;

    @Size(max = 255)
    private String profilepicture;

    @Size(max = 255)
    private String companyname;

    private roletype roles;

    private List<Integer> userMeetings;

    private Integer contractUser;

}
