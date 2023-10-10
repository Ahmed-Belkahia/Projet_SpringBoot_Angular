package com.example.techmasterpi.repos;


import com.example.techmasterpi.domain.SellContract;
import com.example.techmasterpi.model.StatutContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SellContractRepository extends JpaRepository<SellContract, Integer> {

}
