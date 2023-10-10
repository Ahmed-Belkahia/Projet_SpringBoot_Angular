package com.example.techmasterpi.repos;


import com.example.techmasterpi.domain.RentalOffer;
import com.example.techmasterpi.model.RentalOfferDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalOfferRepository extends JpaRepository<RentalOffer, Integer> {

    @Query("SELECT p FROM RentalOffer p WHERE p.title = :title")
    List<RentalOffer> findByRentalOfferByTitle(@Param("title") String title);


    @Query("SELECT p FROM RentalOffer p ORDER BY p.monthlyrent ASC")
    List<RentalOffer> findAllOrderByPriceAsc();

    @Query("SELECT p FROM RentalOffer p WHERE p.monthlyrent< :price2 and p.monthlyrent> :price1")
    List<RentalOffer> findRentalOfferByRangePrice(@Param("price1") double price1, @Param("price2")  double price2);

   // List<RentalOffer> findTopNByOrderByOffredateDesc(@Param("n") Integer n);
   @Query("SELECT p FROM RentalOffer p ORDER BY p.monthlyrent DESC")
   List<RentalOffer> findAllOrderByPriceDESC();

    @Query("SELECT p FROM RentalOffer p WHERE  p.user.userid=:idUser")
    List<RentalOffer> findRentalOfferByUser(int idUser);

}
