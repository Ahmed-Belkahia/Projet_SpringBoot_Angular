package com.example.techmasterpi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import java.util.Set;

@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Relocation {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer relocationid;

    @Column

    private Date relocationdate;


    @Column
    private String locationdep;
    @Column
    private String relocationState;


    @Column
    private String locationarr;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_relocation_id")
    private User userRelocation;

    @OneToMany(mappedBy = "relocationDelivery")
    private Set<Delivery> relocationDeliveryDeliverys;

    @OneToMany(mappedBy = "relocationFourtniture")
    private Set<Furniture> relocationFourtnitureFurnitures;



}
