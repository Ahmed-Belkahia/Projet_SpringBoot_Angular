package com.example.techmasterpi.domain;



import com.example.techmasterpi.model.StatutOffre;
import com.example.techmasterpi.model.TypeOffer;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Set;

@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter

@AllArgsConstructor


public class SellerOffer {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int sellid;

    @Column
    private String description;


    @Column
    private String title;

    @Column

    private double price;

    @Column
    private String address;

    @Column
    private Date datesell;

    @Column
    private String picture;
    @Column
    private Boolean sold=false;
    @Column
    private Boolean favorite =false;


    @Column
    @Enumerated(EnumType.STRING)
    private TypeOffer typeoffer;

    @Column
    @Enumerated(EnumType.STRING)
    private StatutOffre statut = StatutOffre.EN_ATTENTE;
    @Column
    private Date rendezvousdate;






    @OneToMany(mappedBy = "contractSell")

    private Set<SellContract> sellContracts;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "sellerOffer")
    private List<OffreFavorit> offreFavorits;

    @OneToMany(mappedBy = "offerRendezVous")
    private List<RendezVous> rendezVous;

    private Double surface;

    private int rating;
    @OneToMany(mappedBy = "offre", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();


//    public SellerOffer(String description) {
//        this.description=description;
//    }
//
//    public SellerOffer(String descriptipon, String title) {
//        this.description=descriptipon;
//        this.title=title;
//    }
//
//    public SellerOffer(String descriptipon, String title, Double price, String address, Date datesell) {
//        this.description=descriptipon;
//        this.title=descriptipon;
//        this.price=price;
//        this.address=address;
//        this.datesell=datesell;
//    }
//
//    public SellerOffer(String descriptipon, String title, Double price) {
//        this.description=descriptipon;
//        this.title=title;
//        this.price=price;
//            }
//
//
//    public SellerOffer(String description, String title, String address) {
//        this.description=description;
//        this.title=title;
//        this.address=address;
//    }
//
//    public SellerOffer(String description, String title, String address, Date datesell) {
//        this.description=description;
//        this.title=title;
//        this.address=address;
//        this.datesell=datesell;
    }
//    public boolean getFavorite(){
//        return favorite;
//    }




