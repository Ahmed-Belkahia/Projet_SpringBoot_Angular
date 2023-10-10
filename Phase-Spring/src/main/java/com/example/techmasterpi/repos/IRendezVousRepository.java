package com.example.techmasterpi.repos;

import com.example.techmasterpi.domain.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRendezVousRepository extends JpaRepository<RendezVous,Integer> {

}
