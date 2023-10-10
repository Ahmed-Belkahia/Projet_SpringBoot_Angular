package com.example.techmasterpi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;
@Column
    private int rating;

    @JsonIgnore
    @ManyToOne
    private Post post;


    @ManyToOne
    @JsonIgnore
    private User ratingOwner;



}