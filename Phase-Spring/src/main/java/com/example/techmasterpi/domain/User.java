package com.example.techmasterpi.domain;




import antlr.LexerSharedInputState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import com.example.techmasterpi.model.roletype;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;
    private int cin;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private int phone;
    private String address;
    @Nullable
    private String profilepicture;
    @Nullable
    private String companyname;


    public boolean enabled;
    public int failedloginattempts;

    public LocalDateTime timeBlocked;
    public LocalDateTime lastLoggedIn;

    @Enumerated(EnumType.STRING)
    private roletype roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority(roles.name())));
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }




    @JsonIgnore
    @OneToMany(mappedBy = "userRelocation")
    private Set<Relocation> userRelocationRelocations;

    @OneToMany(mappedBy = "userPost")


    @JsonIgnore

    private Set<Post> userPosts;

    @JsonIgnore
    @OneToMany(mappedBy = "userComplain")
    private Set<Complaint> userComplainComplaints;
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_meeting",
            joinColumns = @JoinColumn(name = "user_userid"),
            inverseJoinColumns = @JoinColumn(name = "meeting_meetingid")
    )
    private Set<Meeting> userMeetingMeetings;


    @JsonIgnore
    @OneToMany(mappedBy = "userContractsale")
    private Set<SellContract> userContractsaleSellContracts;
    @JsonIgnore
    @OneToMany(mappedBy = "userRentalcontract")
    private Set<RentalContract> userRentalcontractRentalContracts;


    @OneToMany(mappedBy = "planUser")

    @JsonIgnore

    private Set<ContractPlan> planUser;


    @JsonIgnore
    @OneToMany(mappedBy = "userDelevery")
    private Set<Delivery> userDeleveryDeliverys;


    @JsonIgnore
    @OneToMany(mappedBy="reactOwner", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<CommentReact> listReacts;



    @JsonIgnore
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "taxuser")
    private Tax usertax;
    @JsonIgnore
    @OneToMany(mappedBy="ratingOwner", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<Rating> listRatings;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Notification> notifications;

    @JsonProperty
    @JsonIgnore
    @OneToMany(mappedBy = "user" , cascade=CascadeType.ALL)
    private Set<Reclamation> reclamations;




}

