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
public class ContractPlan {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contractId;

    @Column

    private LocalDate contractDate;

    @Column

    private Double price;
    @Column
    private String statut;
    @ManyToOne
    @JoinColumn(name = "plan_user_id")
    private User planUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_contract_plan_id")
    private Plan planContractPlan;


    @JsonIgnore
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "contractPlan")
    private Payment paymentPlan;
    public int getIdContrat() {
        return this.contractId;
    }

    public String getStatus() {
        return this.statut;
    }

}
