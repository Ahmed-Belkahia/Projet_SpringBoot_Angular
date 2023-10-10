package com.example.techmasterpi.repos;

import com.example.techmasterpi.domain.OffreFavorit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferFavoriteRepository extends JpaRepository<OffreFavorit,Integer> {
}
