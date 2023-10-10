package com.example.techmasterpi.domain;


import com.example.techmasterpi.model.StatutContrat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class SellContract {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int contractsellid;

   

    @Column
    private Double price;

    @Column
    private LocalDate selldate;

    @Column
    private String city;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_sell_id")
    private SellerOffer contractSell;

    @Enumerated(EnumType.STRING)
    private StatutContrat statut;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_contractsale_id")
    private User userContractsale;


    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "sellContract")
    private Payment payment;




}
