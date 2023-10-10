package com.example.techmasterpi.model;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import com.example.techmasterpi.domain.SellContract;
import com.example.techmasterpi.model.TypeOffer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;


@Getter
@Setter
public class SellerOfferDTO {

    private Integer sellid;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String title;

    private double price;

    @Size(max = 255)
    private String address;

    private Date datesell;
    private Double surface;

    private Boolean sold=false;

    private Boolean favorite=false ;

    @Size(max = 255)
    private String picture;

    private TypeOffer typeoffer;
    private StatutOffre statut;

    private Date rendezvousdate;

    private Integer userSell;

    private Integer contractSell;

}
