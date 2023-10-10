package com.example.techmasterpi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tokenid;
    private String token;
    private LocalDateTime createdat;
    private LocalDateTime expiredat;
    private LocalDateTime confirmedat;
    @ManyToOne
    private User user;

    public Token(String token,
                             LocalDateTime createdat,
                             LocalDateTime expiredat,
                             User user) {
        this.token = token;
        this.createdat = createdat;
        this.expiredat = expiredat;
        this.user = user;
    }


    public Token() {

    }
}
