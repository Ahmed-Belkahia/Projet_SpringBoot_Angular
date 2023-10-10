package com.example.techmasterpi.repos;

import com.example.techmasterpi.domain.ContractPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractPlanRepository extends JpaRepository<ContractPlan, Integer> {
}
