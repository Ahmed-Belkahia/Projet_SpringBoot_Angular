package com.example.techmasterpi.repos;

import com.example.techmasterpi.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface INoteRepository extends JpaRepository<Note,Integer> {
    @Query(value = "SELECT AVG(n.rating) FROM Note n WHERE n.offre.sellid = :offreId")
    Double findAvgRatingByOffre(@Param("offreId") int offreId);
}
