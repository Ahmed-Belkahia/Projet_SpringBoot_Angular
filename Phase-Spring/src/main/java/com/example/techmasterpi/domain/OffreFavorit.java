package com.example.techmasterpi.domain;

import com.example.techmasterpi.model.TypeOffer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter


public class OffreFavorit {


        @Id
        @GeneratedValue(strategy =

                GenerationType.IDENTITY)
        private int id;
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
    private Boolean sold;


    @Column
    @Enumerated(EnumType.STRING)
    private TypeOffer typeoffer;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "offer_id")
        private SellerOffer sellerOffer;
    private LocalDateTime timestamp;



    public OffreFavorit(int id, SellerOffer sellerOffer,LocalDateTime timestamp, String action) {
        this.id = id;
        this.sellerOffer = sellerOffer;

        this.timestamp = timestamp;

    }


    public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }




}
