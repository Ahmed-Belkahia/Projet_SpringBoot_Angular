package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.*;
import com.example.techmasterpi.model.SellerOfferDTO;
import com.example.techmasterpi.model.StatutOffre;
import com.example.techmasterpi.model.UserDTO;
import com.example.techmasterpi.repos.*;
import com.example.techmasterpi.util.NotFoundException;
import com.stripe.exception.oauth.InvalidRequestException;
import org.elasticsearch.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class SellerService implements ISellerService {
    @Autowired
    private SellerOfferRepository sellerOfferRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OfferFavoriteRepository offerFavoriteRepository;
    @Autowired
    private INoteRepository noteRepository;
    @Autowired
    private IRendezVousRepository rendezVousRepository;


    @Override
    public SellerOffer ajouterOffer(SellerOffer sellerOffer,int user_id) {
        User u = userRepository.findById(user_id).get();
        sellerOffer.setUser(u);
        sellerOfferRepository.save(sellerOffer);

        return sellerOffer;
    }


    @Override
    public void updateOffer(SellerOffer c) {

        if (sellerOfferRepository.findById(c.getSellid()).isPresent())
            sellerOfferRepository.save(c);
        else
            System.out.println("doesnt exist");

    }
//    public void update(final Integer sellid, final SellerOfferDTO sellerOfferDTO) {
//        final SellerOffer sellerOffer = sellerOfferRepository.findById(sellid)
//                .orElseThrow(NotFoundException::new);
//        mapToEntity(sellerOfferDTO, sellerOffer);
//        sellerOfferRepository.save(sellerOffer);
//    }

    @Override
    public void deleteOffer(int idc) {

        sellerOfferRepository.deleteById(idc);

    }
    @Override
    public SellerOfferDTO getById(final Integer planid) {
        return sellerOfferRepository.findById(planid)
                .map(plan -> mapToDTO(plan, new SellerOfferDTO()))
                .orElseThrow(NotFoundException::new);
    }

//    @Override
//    public SellerOffer get(int id) {
//        return sellerOfferRepository.findById(id).get();
//    }

    public List<SellerOfferDTO> findAll() {
        final List<SellerOffer> sellerOffers = sellerOfferRepository.findAll();
        return sellerOffers.stream()
                .map((sellerOffer) -> mapToDTO(sellerOffer, new SellerOfferDTO()))
                .collect(Collectors.toList());
    }


//    @Override
//    public List<SellerOffer> findAllOffer() {
//
//        return sellerOfferRepository.findAll();
//
//    }


    public List<SellerOffer> offer() {

        return sellerOfferRepository.findAll();

    }

    public Note savenote(Note note,int offreId,int user_id) {

        SellerOffer offre = sellerOfferRepository.findById(offreId).get();
        note.setOffre(offre);
        User u = userRepository.findById(user_id).get();
        note.setUser(u);
        noteRepository.save(note);



       return noteRepository.save(note);
    }
    @Override
    public void deletenote(int idc) {

        noteRepository.deleteById(idc);

    }
    @Override
    public Double calculerMoyenneRaitingParOffre(int offreId) {
        return noteRepository.findAvgRatingByOffre(offreId);
    }


    @Override
    public void markAsSold(Long id) {
        SellerOffer property = sellerOfferRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with id: " + id));
        property.setSold(true);
        sellerOfferRepository.save(property);
    }
    @Override
    public List<SellerOfferDTO> findAllOfferFavorit1() {

        final List<SellerOffer> sellerOffers = sellerOfferRepository.findByFavoriteTrue();
        return sellerOffers.stream()
                .map((sellerOffer) -> mapToDTO(sellerOffer, new SellerOfferDTO()))
                .collect(Collectors.toList());
    }

    public SellerOffer saveoffer(SellerOffer sellerOffer,int user_id) {


        User u = userRepository.findById(user_id).get();
        sellerOffer.setUser(u);
        sellerOffer.setStatut(StatutOffre.EN_ATTENTE);

        return sellerOfferRepository.save(sellerOffer);
    }


    public static String saveImage(MultipartFile image) throws IOException {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        Path path = Paths.get("uploads");
        Files.createDirectories(path);
        try (InputStream inputStream = image.getInputStream()) {
            Path filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException e) {
            throw new IOException("Could not save file " + fileName, e);
        }
    }

        @Override
    public void markOfferAsFavorite(Integer id) {
        SellerOffer offer = sellerOfferRepository.findById(id).get();

            OffreFavorit favorite = new OffreFavorit();
            favorite.setSellerOffer(offer);
            favorite.setDescription(offer.getDescription());
            favorite.setTitle(offer.getTitle());
            favorite.setPrice(offer.getPrice());
            favorite.setAddress(offer.getAddress());
            favorite.setPicture(offer.getPicture());
            favorite.setTypeoffer(offer.getTypeoffer());
            favorite.setDatesell(offer.getDatesell());
            favorite.setSold(offer.getSold());
            favorite.setTimestamp(LocalDateTime.now());


        offerFavoriteRepository.save(favorite);

            offer.setFavorite(true);
            sellerOfferRepository.save(offer);
        }
    @Override
    public List<SellerOfferDTO> findOffreByPriceASC(){
        final List<SellerOffer> sellerOffers= sellerOfferRepository.findOffreByPriceASC();
        return sellerOffers.stream()
                .map((sellerOffer) -> mapToDTO(sellerOffer, new SellerOfferDTO()))
                .collect(Collectors.toList());
    }
    @Override
    public List<SellerOfferDTO> findBySurfaceBetween(Double minSurface, Double maxSurface) {
        final List<SellerOffer> sellerOffers= sellerOfferRepository.findBySurfaceBetween(minSurface, maxSurface);
        return sellerOffers.stream()
                .map((sellerOffer) -> mapToDTO(sellerOffer, new SellerOfferDTO()))
                .collect(Collectors.toList());
    }
//    @Override
//    public List<SellerOffer> findOffreByPriceASC(){
//        return sellerOfferRepository.findOffreByPriceASC();
//    }


@Override
    public SellerOffer demanderRendezVous(int idOffre, RendezVous rendezVous) {
        SellerOffer offre = sellerOfferRepository.findById(idOffre).orElseThrow(() -> new NotFoundException("Offre non trouvée"));

        if (offre.getStatut() != StatutOffre.EN_ATTENTE) {
            throw new NotFoundException("Le rendez-vous ne peut être demandé que pour une offre en attente");
        }

        rendezVousRepository.save(rendezVous);

        rendezVous.setOfferRendezVous(offre);
        offre.setRendezvousdate(rendezVous.getRendezvousdate());
        offre.setStatut(StatutOffre.AVEC_RENDEZ_VOUS);
        return sellerOfferRepository.save(offre);
    }





//    public List<SellerOffer> getFavoriteOffers() {
//        return sellerOfferRepository.findByFavoriteTrue();
//    }
//public List<OffreFavorit> getAllOffresFavorites() {
//    return OfferFavoriteRepository.findAll();
//}
@Override
public List<OffreFavorit> findAllOfferFavorit() {

    return offerFavoriteRepository.findAll();

}
    @Override
    public void deleteOfferFavorit(int id) {
        SellerOffer offer = sellerOfferRepository.findById(id).get();
        offer.setFavorite(false);
        sellerOfferRepository.save(offer);

        offerFavoriteRepository.deleteById(id);


    }
    private SellerOfferDTO mapToDTO(final SellerOffer sellerOffer,
                                    final SellerOfferDTO sellerOfferDTO) {
        sellerOfferDTO.setSellid(sellerOffer.getSellid());
        sellerOfferDTO.setDescription(sellerOffer.getDescription());
        sellerOfferDTO.setTitle(sellerOffer.getTitle());
        sellerOfferDTO.setPrice(sellerOffer.getPrice());
        sellerOfferDTO.setAddress(sellerOffer.getAddress());
        sellerOfferDTO.setFavorite(sellerOffer.getFavorite());
        sellerOfferDTO.setSold(sellerOffer.getSold());
        sellerOfferDTO.setRendezvousdate(sellerOffer.getRendezvousdate());
        sellerOfferDTO.setDatesell(sellerOffer.getDatesell());
        sellerOfferDTO.setPicture(sellerOffer.getPicture());
        sellerOfferDTO.setTypeoffer(sellerOffer.getTypeoffer());
        sellerOfferDTO.setSurface(sellerOffer.getSurface());
        sellerOfferDTO.setUserSell(sellerOffer.getUser() == null ? null : sellerOffer.getUser().getUserid());
        return sellerOfferDTO;
    }

    private SellerOffer mapToEntity(final SellerOfferDTO sellerOfferDTO,
                                    final SellerOffer sellerOffer) {
        sellerOffer.setDescription(sellerOfferDTO.getDescription());
        sellerOffer.setTitle(sellerOfferDTO.getTitle());
        sellerOffer.setPrice(sellerOfferDTO.getPrice());
        sellerOffer.setAddress(sellerOfferDTO.getAddress());
        sellerOffer.setDatesell(sellerOfferDTO.getDatesell());
        sellerOffer.setPicture(sellerOfferDTO.getPicture());
        sellerOffer.setFavorite(sellerOfferDTO.getFavorite());
        sellerOffer.setSold(sellerOfferDTO.getSold());
     sellerOffer.setRendezvousdate(sellerOfferDTO.getRendezvousdate());
//        sellerOffer.setStatut(sellerOfferDTO.getStatut());
        sellerOffer.setTypeoffer(sellerOfferDTO.getTypeoffer());
        final User userSell = sellerOfferDTO.getUserSell() == null ? null : userRepository.findById(sellerOfferDTO.getUserSell())
                .orElseThrow(() -> new NotFoundException("user not found"));
        sellerOffer.setUser(userSell);

       return sellerOffer;
    }
    }



