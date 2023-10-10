package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.RentalContract;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.model.RentalContractDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.NonNull;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface IRentalContract {

    int addRentalContract(RentalContract rentalContract, @NonNull HttpServletRequest request, int rentalOfferId)throws MessagingException;
    boolean updateRentalContract(RentalContract rentalContract);
    boolean deleteRentalContract(int id);
    public RentalContractDTO getById(final Integer contractid);
    public List<RentalContractDTO> findAll();
  //  List<RentalContract> getAll();
  public double getChiffreAffaireByUser(@NonNull HttpServletRequest request);

//    public void rappelFinContrat() throws MessagingException;
    public List<Integer> rappelFinContratAngular();
    public Charge createCharge(String token, int amount, String currency, int idcontract) throws StripeException;
}
