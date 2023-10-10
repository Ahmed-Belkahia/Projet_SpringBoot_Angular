package com.example.techmasterpi.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class FurnitureDTO {

    private Integer furnitureid;

    private Double furnitureweight;

    private Double price;

    private Double surface;

    @Size(max = 255)
    private String picture;

    private Integer furnitureRelocation;

    private Integer relocationFourtniture;

}
