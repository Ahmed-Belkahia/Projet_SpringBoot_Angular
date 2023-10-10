package com.example.techmasterpi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@NoArgsConstructor

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class RendezVous {
    @Id
    @GeneratedValue(strategy =

            GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private Date rendezvousdate;
    @Column
    private String nom;
    @Column
    private String prenom;
    @Column
    private int tel;
    @Column
    private String email;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id")
    private SellerOffer offerRendezVous;
}
