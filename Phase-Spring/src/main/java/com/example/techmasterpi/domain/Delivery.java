package com.example.techmasterpi.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Delivery {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deliveryid;

    @Column
    private Long totaleprice;

    @Column
    private String servicedetail;

    @Column
    private String track;

    @Column
    private String state;

    @Column
    private String stateDeleviry;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_delevery_id")
    private User userDelevery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relocation_delivery_id")
    private Relocation relocationDelivery;


    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "deliveryPayment")
    @JsonIgnore
    private Payment paymentDel;




}
