package com.example.techmasterpi.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class ContractPlanDTO {

    private Integer contractId;
    private LocalDate contractDate;
    private Double price;
    private String statut;
    private Integer planUser;
    private Integer userContartplan;
    private Integer planContractPlan;

}
