package com.example.techmasterpi.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalDate;


@Getter
@Setter
public class RentalOfferDTO {

    private Integer offreid;

    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String adress;

    private LocalDate offredate;


    private String picture;

    private Double monthlyrent;

    private TypeROffer typerentalloffer;

}
