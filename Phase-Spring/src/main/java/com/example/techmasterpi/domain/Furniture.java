package com.example.techmasterpi.domain;

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
public class Furniture {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer furnitureid;

    @Column
    private Double furnitureweight;

    @Column
    private Double price;

    @Column
    private Double surface;

    @Column
    private String picture;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relocation_fourtniture_id")
    private Relocation relocationFourtniture;



}
