package com.example.techmasterpi.domain;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter

public class RentalContract {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contractid;

    @Column

    private LocalDate startdate;
    @Column
    private LocalDate enddate;

    @Column
    private Integer nbmonth;
    @Column
    private double price;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "rentalContratPayment")
    private PaymentRentalContrat payment; // Ajout du champ paymentId


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_rentalcontract_id")
    private User userRentalcontract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rentaloffer_rental_contract_id")
    private RentalOffer rentalofferRentalContract;



}
