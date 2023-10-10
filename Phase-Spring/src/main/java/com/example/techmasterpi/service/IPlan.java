package com.example.techmasterpi.service;


import com.example.techmasterpi.domain.Plan;
import com.example.techmasterpi.model.PlanDTO;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface IPlan  {


    PlanDTO get(Integer planid);

     Plan create(Plan p, int userId) throws Exception;

    Boolean  update ( Plan c);
     boolean  delete (int id );
    List<PlanDTO> findAll();

    List<PlanDTO> findPlansByTitleAndPictureNotNull(String title);

    List<PlanDTO> findAllOrderByPriceAsc();

    List<PlanDTO> findAllOrderByPriceDesc();

    int contractNumberByPlan(int idPlan);

    Plan savePlan(Plan plan,/* @NonNull HttpServletRequest request*/ int id) throws Exception;
    List<PlanDTO> PLAN_DTOS ();


    int addplan1(Plan plan, @NonNull HttpServletRequest request);

    double getChiffreAffaireByOffer(int offerId);
}
