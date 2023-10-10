package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.Note;
import com.example.techmasterpi.domain.OffreFavorit;
import com.example.techmasterpi.domain.RendezVous;
import com.example.techmasterpi.domain.SellerOffer;
import com.example.techmasterpi.model.SellerOfferDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISellerService {


    public SellerOffer ajouterOffer (SellerOffer sellerOffer,int id);

//    public SellerOffer saveoffer(SellerOffer sellerOffer,int id);
public SellerOffer saveoffer(SellerOffer sellerOffer,int user_id);
    public SellerOffer demanderRendezVous(int idOffre, RendezVous rendezVous);


    public void updateOffer (SellerOffer s);
    void deleteOffer(int id);
    public void deleteOfferFavorit(int id);

    public SellerOfferDTO getById(final Integer planid);
//    SellerOffer get(int id);

//    public List<SellerOffer> findAllOffer();
    public void markAsSold(Long id);
    public List<SellerOfferDTO> findAll();
//    public List<SellerOffer> findOffreByPriceASC();
public List<SellerOfferDTO> findOffreByPriceASC();
//    public Note donnerRating(Note note, int offreId, int user_id, int noteSur10);
public List<SellerOfferDTO> findBySurfaceBetween(Double minSurface, Double maxSurface);
public Note savenote(Note note,int offreId,int user_id);
    public Double calculerMoyenneRaitingParOffre(int offreId);
    public void deletenote(int idc);
  void markOfferAsFavorite(Integer id);
//    public List<SellerOffer> getFavoriteOffers();
//    public SellerOffer MarkOfferAsFavorite(SellerOffer offer);
//    List<SellerOffer> findByHistorique();
//
public List<OffreFavorit> findAllOfferFavorit();

    List<SellerOfferDTO> findAllOfferFavorit1();





}
