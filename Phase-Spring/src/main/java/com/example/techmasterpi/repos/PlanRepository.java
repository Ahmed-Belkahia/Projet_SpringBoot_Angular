package com.example.techmasterpi.repos;


import com.example.techmasterpi.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {

    @Query("SELECT p FROM Plan p WHERE p.title = :title AND p.picture IS NOT NULL")
    List<Plan> findByTitleAndPictureIsNotNull(@Param("title") String title);

    @Query("SELECT p FROM Plan p WHERE p.picture IS NOT NULL  ORDER BY p.price ASC  ")
    List<Plan> findAllOrderByPriceAsc();

    @Query("SELECT p FROM Plan p WHERE p.picture IS NOT NULL ORDER BY p.price desc ")
    List<Plan> findAllOrderByPriceDesc();

    List<Plan> findByPriceLessThan(double price);

    @Query("Select p from Plan  p where p.picture is not NULL ")
    List<Plan> picturenotnull();
}
