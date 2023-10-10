package com.example.techmasterpi.repos;


import com.example.techmasterpi.domain.RentalContract;
import com.example.techmasterpi.domain.RentalOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalContractRepository extends JpaRepository<RentalContract, Integer> {
@Query("select r from RentalContract r WHERE r.startdate between :startDate and :endDate or r.enddate between :startDate and :endDate or r.rentalofferRentalContract.offredate>:startDate")
public List<RentalContract> getActiveRentalContractByDates(LocalDate startDate,LocalDate endDate);




}
