package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.RentalOffer;
import com.example.techmasterpi.model.RentalOfferDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface IRentalOffer {

//public List<String> scrapping(double price1, double price2);
    public List<RentalOfferDTO> findRentalOfferByTitle(String title);
    public List<RentalOfferDTO> findAllOrderByPriceAsc();
    int addRentalOffer(RentalOffer rentalOffer, @NotNull HttpServletRequest request);
    boolean updateRentalOffer(RentalOffer rentalOffer);
    boolean deleteRentalOffer(int id);
    RentalOfferDTO getById(final Integer offreid);
   public List<RentalOfferDTO> getAll();
    double getChiffreAffaireByOffer(int offerId);
    //public List<RentalOfferDTO> findAll();
    public List<RentalOfferDTO> getOffersByRangePrice(double price1, double price2);
    public  List<RentalOfferDTO> findTopNByOrderByOffredateDesc(Integer n);
    public int getNumberOffreByUser(int idUser);
    public List<RentalOfferDTO> getAvailableOffers(LocalDate startdate, LocalDate enddate);
    public List<RentalOfferDTO> findAllOrderByPriceDesc();
}
