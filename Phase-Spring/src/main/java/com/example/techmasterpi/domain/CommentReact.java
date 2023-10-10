package com.example.techmasterpi.domain;

import com.example.techmasterpi.model.TypeReact;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class CommentReact implements Serializable {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer reactId;

        @Enumerated(EnumType.STRING)
        private TypeReact typeReact;



        @ManyToOne

        private User reactOwner;


        @ManyToOne
        private Comment reactComment;



    }