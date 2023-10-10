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
public class Tax {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taxid;

    @Column
    private Long totaletax;

    @Column
    private Long pricetax;

    @Column
    private String statustax;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "usertax")
    private User taxuser;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "taxpayment")
    private Payment payment;

}
