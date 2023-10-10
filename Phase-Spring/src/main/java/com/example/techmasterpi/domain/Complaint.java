package com.example.techmasterpi.domain;

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
public class Complaint {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer complaintId;

    @Column
    private String object;

    @Column(name = "\"description\"")
    private String description;

    @Column
    private LocalDate complainDate;

    @Column
    private String etat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_complain_id")
    private User userComplain;





}
