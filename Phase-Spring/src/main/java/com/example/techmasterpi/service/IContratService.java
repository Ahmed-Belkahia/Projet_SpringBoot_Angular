package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.SellContract;
import com.example.techmasterpi.domain.SellerOffer;
import com.example.techmasterpi.model.SellContractDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import javax.mail.MessagingException;
import java.util.List;

public interface IContratService {
    public void ajouterContrat (SellContract sellerContrat,int user_id,int sellid)throws MessagingException;



    public void updateContrat (SellContract contract);
    void deleteContrat(int id);
//    public SellContract get(int id);
public SellContractDTO get(final Integer contractsellid);

//    public List<SellContract> findAllContrat();
public List<SellContractDTO> findAll();

    public Charge createCharge(String token,  String currency, int idDelevry) throws Exception;
}
