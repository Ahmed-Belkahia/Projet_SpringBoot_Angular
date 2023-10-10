package com.example.techmasterpi.repos;

import com.example.techmasterpi.domain.Relocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RelocationRepository extends JpaRepository<Relocation, Integer> {
    @Query("SELECT r FROM Relocation r JOIN r.userRelocation u WHERE u.userid =:userId")
    public List<Relocation> findRelocationByUserRelocation(int userId);

    @Query("SELECT r FROM Delivery d JOIN d.relocationDelivery r WHERE d.state =:etat")
    public List<Relocation> findRelocationParEtat(String etat);

    @Query("SELECT r FROM Delivery d JOIN d.relocationDelivery r WHERE d.state =:etat AND r.userRelocation.userid =:iduser")
    public List<Relocation> findRelocationParEtatForUser(String etat, int iduser);

    @Query("SELECT r FROM Delivery d JOIN d.relocationDelivery r WHERE r.relocationdate =:relocationdate AND d.userDelevery.userid =:userid")
    public List<Relocation> findRelocationParDateForUser(Date relocationdate, int userid);

    @Query("SELECT r FROM Delivery d JOIN d.relocationDelivery r WHERE r.relocationdate=CURRENT_DATE() AND d.userDelevery.userid =:userid")
    public List<Relocation> findRelocationByLocalDate(int userid);


}