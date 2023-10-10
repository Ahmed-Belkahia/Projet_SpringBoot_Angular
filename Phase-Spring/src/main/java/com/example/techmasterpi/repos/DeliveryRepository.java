package com.example.techmasterpi.repos;

import com.example.techmasterpi.domain.Delivery;
import com.example.techmasterpi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    @Query("SELECT d.userDelevery FROM Delivery d JOIN d.relocationDelivery r WHERE d.state = :etat GROUP BY d.userDelevery")
    public List<User> finbbestrelocatocateur(String etat);


}
