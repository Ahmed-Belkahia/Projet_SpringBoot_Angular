package com.example.techmasterpi.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class SellContractDTO {

    private Integer contractsellid;
    private Double price;
    private LocalDate selldate;
    private Integer contractSell;
    private Integer userContractsale;

}
