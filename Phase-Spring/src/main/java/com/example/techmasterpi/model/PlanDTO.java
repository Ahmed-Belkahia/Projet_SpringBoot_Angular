package com.example.techmasterpi.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class PlanDTO {

    private Integer planid;

    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String picture;

    private Double price;

    private Double realizationprice;

    private Integer livingroom;

    private Integer kitchen;

    private Integer wc;

    private Integer room1;

    private Integer room2;

    @Size(max = 255)
    private String description;

}
