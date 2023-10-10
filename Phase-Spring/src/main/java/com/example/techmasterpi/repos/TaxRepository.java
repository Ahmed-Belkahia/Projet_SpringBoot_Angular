package com.example.techmasterpi.repos;

import com.example.techmasterpi.domain.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Integer> {
    @Modifying
    @Query(value = "UPDATE tax SET taxpayment = null WHERE taxid = :paymentId", nativeQuery = true)
    void updateTaxToNull( int paymentId);

    public Tax findByTaxuser_Userid(int id);
}
