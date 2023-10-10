package com.example.techmasterpi.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class RentalContractDTO {

    private Integer contractid;
    private LocalDate startdate;
    private LocalDate enddate;
    private Integer nbmonth;
    private double price;
    private Integer rentalIOfferContractRental;
    private Integer userRentalcontract;
    private Integer rentalofferRentalContract;

}
