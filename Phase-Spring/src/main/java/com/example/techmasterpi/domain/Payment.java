package com.example.techmasterpi.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDate;



@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter



    
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String cvc;

    @Column(nullable = false)
    private String TypePayment;

    @Column(nullable = false)
    private String cardholderName;
    @Column
    private LocalDate datepaiement;
    @JsonIgnore

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "paymentDel")
    private Delivery deliveryPayment;
    @JsonIgnore

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "paymenttax")
    private Tax tax;
    @JsonIgnore
    @OneToOne(cascade = {CascadeType.ALL})
        @JoinColumn(name = "payment")
        private SellContract sellContract;
        @JsonIgnore
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "paymentPlan")
    private ContractPlan contractPlan;
}

