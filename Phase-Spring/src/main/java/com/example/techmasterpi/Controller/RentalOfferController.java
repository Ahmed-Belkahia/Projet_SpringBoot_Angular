package com.example.techmasterpi.Controller;

import com.example.techmasterpi.domain.RentalOffer;

import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.model.RentalOfferDTO;
import com.example.techmasterpi.repos.RentalOfferRepository;
import com.example.techmasterpi.repos.UserRepository;
import com.example.techmasterpi.service.IRentalOffer;
import com.example.techmasterpi.service.RentalOfferService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.List;


@Log4j2
@RestController
@RequestMapping("/rentaloffers")
@CrossOrigin(origins = "*")

public class RentalOfferController {

    @Autowired
    IRentalOffer rentalOfferService;

@Autowired
    UserRepository userRepository;
@Autowired
RentalOfferService rentalOfferServiceee;

@Autowired

RentalOfferRepository rentalOfferRepository;
    @GetMapping("/GetAllRentalOffers")
    public List<RentalOfferDTO> getAllRentalOffers(){
        List<RentalOfferDTO> listContrats = rentalOfferService.getAll();

        return listContrats;
    }


    @GetMapping("/{ro}")

    public ResponseEntity<RentalOfferDTO> getRentalOffer(@PathVariable("ro") Integer ro){
        RentalOfferDTO rentalOfferDTO = rentalOfferService.getById(ro);
        if (rentalOfferDTO != null) {
            return ResponseEntity.ok(rentalOfferDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getNTopRentalOffers/{n}")
    public ResponseEntity<?> getRentalOffersByRangePrice(@PathVariable("n") Integer n){
        List<RentalOfferDTO> rentalOfferDTOList = rentalOfferService.findTopNByOrderByOffredateDesc(n);
        if (!rentalOfferDTOList.isEmpty()) {
            return ResponseEntity.ok(rentalOfferDTOList);
        } else {
            String errorMessage = "Aucune offre de location trouvée pour le nombre demandé de " + n;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @GetMapping("/getRentalOffersByRangePrice/{price1}/{price2}")
    public ResponseEntity<List<RentalOfferDTO>> getRentalOffersByRangePrice(@PathVariable("price1")double price1,@PathVariable("price2")double price2){
        List<RentalOfferDTO> rentalOffers = rentalOfferService.getOffersByRangePrice(price1,price2);
        if (rentalOffers.isEmpty()) {
            String message = "Il n'existe pas des offres de location entre les prix " + price1 + " et " + price2;

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            String message = "Liste des offres de location entre les prix " + price1 + " et " + price2;
            return ResponseEntity.ok().body(rentalOffers);
        }
    }
    @GetMapping("/Disponibilite/{offerid}")
    public ResponseEntity<List<String>> getRentalOffer(@PathVariable("offerid") int offerid){
        List<String> disponibility = rentalOfferServiceee.disponibility(offerid);
        if (disponibility.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            String message = "Disponibilité de l'offre avec l'id " + offerid;
            return ResponseEntity.ok().body(disponibility);
        }
    }
/*
    @GetMapping("/getScrappingOffers/{price1}/{price2}")
    public ResponseEntity<List<String>> getScrappingOffers(@PathVariable("price1")double price1,@PathVariable("price2")double price2){
        List<String> scrappingOffers = rentalOfferService.scrapping(price1, price2);
        if (scrappingOffers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            String message = "Offres de location récupérées par scrapping entre les prix " + price1 + " et " + price2;
            return ResponseEntity.ok().body(scrappingOffers);
        }
    }*/


    @PostMapping("/addRentalOffer")
    public ResponseEntity<String> addContrat(@RequestBody RentalOffer c,  @NotNull HttpServletRequest request){
        int newRentalOfferId = rentalOfferService.addRentalOffer(c, request);
        if (newRentalOfferId != 0) {
            String message = "Offre de location ajoutée avec succès avec l'id " + newRentalOfferId;
            return ResponseEntity.ok().body(message);
        } else {
            String message = "Erreur lors de l'ajout de l'offre de location";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }

    @PutMapping("/updateRentalOffer")
    public ResponseEntity<String> updateOffer(@RequestBody RentalOffer ro){
        boolean isUpdated = rentalOfferService.updateRentalOffer(ro);
        if (isUpdated) {
            String message = "Offre de location mise à jour avec succès";
            return ResponseEntity.ok().body(message);
        } else {
            String message = "Erreur lors de la mise à jour de l'offre de location";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }
/*
    @PostMapping("update-RentalOffer/{rentalOfferId}")
    public boolean updateOffer(@RequestBody RentalOffer ro,@PathVariable("rentalOfferId") int rentalOfferId) {
ro.setOffreid(rentalOfferId);
return rentalOfferService.updateRentalOffer(ro);
    }*/
    @DeleteMapping("/deleteRentalOffer/{ro}")
    public ResponseEntity<String> deleteRentalOffer(@PathVariable("ro") int ro) {
        boolean isDeleted = rentalOfferService.deleteRentalOffer(ro);
        if (isDeleted) {
            String message = "Offre de location supprimée avec succès";
            return ResponseEntity.ok().body(message);
        } else {
            String message = "Erreur lors de la suppression de l'offre de location";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }
    @GetMapping("/triDesc")
    public ResponseEntity<List<RentalOfferDTO>> getRentalOffersOrderByPriceDesc() {
        List<RentalOfferDTO> rentalOffers = rentalOfferService.findAllOrderByPriceDesc();
        if (rentalOffers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            String message = "Liste des offres de location triées par prix en ordre ascendant récupérée avec succès";
            return ResponseEntity.ok().header("message", message).body(rentalOffers);
        }
    }
    @GetMapping("/getNumberOffersByUser/{userId}")
    public ResponseEntity<?> getNumberOffersByUser(@PathVariable("userId") int idUser) {
        int numberOfferByUser = rentalOfferService.getNumberOffreByUser(idUser);

        String message = "Le nombre d'offre pour cette utilisateur est :" + numberOfferByUser;
        return ResponseEntity.status(HttpStatus.OK.value()).body(message);

    }

    @GetMapping("/GetAvailableRentalOffers/{startdate}/{enddate}")
    public List<RentalOfferDTO> GetAvailableRentalOffers(@PathVariable("startdate") String startdate,@PathVariable("enddate")String enddate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<RentalOfferDTO> listContrats = rentalOfferService.getAvailableOffers(LocalDate.parse(startdate,formatter),LocalDate.parse(enddate,formatter));

        return listContrats;
    }


    @GetMapping("/search")
    public ResponseEntity<?> getRentalOfferByTitle(@RequestParam String title) {
        List<RentalOfferDTO> rentalOffers = rentalOfferService.findRentalOfferByTitle(title);
        if (!rentalOffers.isEmpty()) {
            return ResponseEntity.ok(rentalOffers);
        } else {
            String message = "Aucune offre de location trouvée pour le titre: " + title;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
    @GetMapping("/tri")
    public ResponseEntity<List<RentalOfferDTO>> getRentalOffersOrderByPriceAsc() {
        List<RentalOfferDTO> rentalOffers = rentalOfferService.findAllOrderByPriceAsc();
        if (rentalOffers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            String message = "Liste des offres de location triées par prix en ordre ascendant récupérée avec succès";
            return ResponseEntity.ok().header("message", message).body(rentalOffers);
        }
    }
/*  @GetMapping("/revenueoffer/{offerId}")
    public ResponseEntity<Object> calculateRevenueForUser(@PathVariable int offerId) {
        Double revenue = rentalOfferService.getChiffreAffaireByOffer(offerId);
        if (revenue == null) {
            String message = "L'offre de location avec l'ID " + offerId + " n'existe pas";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            String message = "Le chiffre d'affaires pour l'offre de location avec l'ID " + offerId + " est de " + revenue;
            return ResponseEntity.ok().body(message);
        }
    }*/
    @GetMapping("/revenueoffer/{offerId}")
    public double calculateRevenueForUser(@PathVariable int offerId) {
        Double revenue = rentalOfferService.getChiffreAffaireByOffer(offerId);
        if (revenue == null) {
            return 0;
        } else {
            return revenue;
        }
    }




   /* @PostMapping("/addImage/{userId}")
    public ResponseEntity<?> addRentalOffer(@RequestParam("title") String title,
                                            @RequestParam(value = "image", required = false) MultipartFile image,
                                            @RequestParam("monthlyrent") Double monthlyrent,
                                            @RequestParam("adress") String adress,
                                            @RequestParam("description") String description,

                                            @PathVariable("userId") int userId) throws IOException {

        // Vérifier si l'utilisateur existe
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        }

        // Créer un nouveau plan
        RentalOffer rentalOffer = new RentalOffer();

        rentalOffer.setUser(user);

            rentalOffer.setTitle(title);


            rentalOffer.setDescription(description);

            rentalOffer.setMonthlyrent(monthlyrent);

            rentalOffer.setAdress(adress);



        rentalOffer.setOffredate(LocalDate.now());



        // Enregistrer l'image si elle existe
     //   if (image != null && !image.isEmpty()) {
        //String imagePath = rentalOfferServiceee.saveImage(image, rentalOffer);
  //          rentalOffer.setPicture(imagePath);
     //   }

        // Enregistrer le plan
        int rentalOfferid = rentalOfferService.addRentalOffer(c, userId);

        if (rentalOfferid > 0) {
            return ResponseEntity.ok().body(rentalOfferRepository.findById(rentalOfferid).orElse(null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de l'offre de location");
        }
    }*/

}


