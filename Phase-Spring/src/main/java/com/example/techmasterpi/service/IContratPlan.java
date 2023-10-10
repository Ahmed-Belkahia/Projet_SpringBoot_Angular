package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.ContractPlan;
import com.example.techmasterpi.model.ContractPlanDTO;
import com.itextpdf.text.DocumentException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.NonNull;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface IContratPlan {

    List<ContractPlanDTO> findAllContract();

    public ContractPlanDTO get(final Integer contractId);


    int create(ContractPlan c,   @NonNull HttpServletRequest request, int idplan) throws MessagingException;

    void  update (ContractPlan c);


    public boolean delete(int id);


    void exportcontrat( int idContrat, String filePath) throws IOException;

    Charge createCharge(String token, String currency, int idcontract) throws StripeException;

    void messageNonPayment() throws MessagingException;

    double Revnnu(@NonNull HttpServletRequest request);
}

