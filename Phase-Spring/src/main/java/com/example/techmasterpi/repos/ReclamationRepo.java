package com.example.techmasterpi.repos;

import java.util.List;

import com.example.techmasterpi.domain.Reclamation;
import com.example.techmasterpi.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface ReclamationRepo extends CrudRepository<Reclamation, Integer>{

}
