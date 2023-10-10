package com.example.techmasterpi.repos;


import com.example.techmasterpi.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository


public interface TagRepository extends JpaRepository<Tag, Integer> {

}
