package com.example.techmasterpi.Controller;


import com.example.techmasterpi.domain.*;
import com.example.techmasterpi.model.SellContractDTO;
import com.example.techmasterpi.model.SellerOfferDTO;
import com.example.techmasterpi.model.TypeOffer;
import com.example.techmasterpi.repos.OfferFavoriteRepository;
import com.example.techmasterpi.repos.SellerOfferRepository;
import com.example.techmasterpi.service.IContratService;
import com.example.techmasterpi.service.ISellerService;
import com.example.techmasterpi.service.SellerService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.List;



@RestController
@RequestMapping(value = "/api/seller")
@CrossOrigin(origins = "*")

public class SellerController {
    @Autowired
    private SellerOfferRepository sellerOfferRepository;
    @Autowired
    private ISellerService sellerService;
    @Autowired
    private IContratService contratService;

    @Autowired
    private OfferFavoriteRepository offerFavoriteRepository;
    @Autowired
    private ISellerService favoritOffer;

    @PostMapping("uploadImage/{user_id}")
    public void createUser(@RequestParam("description") String description,
                           @RequestParam("title") String title, @RequestParam("address") String address, @RequestParam("datesell") Date datesell, @RequestParam("price") double price, @RequestParam(value = "image", required = false) MultipartFile image, @RequestParam("typeoffer")TypeOffer typeoffer, @RequestParam("surface")Double surface,@PathVariable("user_id") int user_id) throws IOException {
        SellerOffer offre = new SellerOffer();
        offre.setDescription(description);
        offre.setAddress(address);
        offre.setTitle(title);
        offre.setDatesell(datesell);
        offre.setPrice(price);
        offre.setTypeoffer(typeoffer);
        offre.setSurface(surface);

        if (image != null) {
            String imagePath = SellerService.saveImage(image);
            offre.setPicture(imagePath);
        }
        SellerOffer savedOffer = sellerService.saveoffer(offre,user_id);
        ResponseEntity.ok(savedOffer);
    }


    @PostMapping("addOffer/{user_id}")
    public void addOffer(@RequestBody SellerOffer sellerOffer,@PathVariable("user_id") int user_id)
    {
        sellerService.ajouterOffer(sellerOffer,user_id);

    }
    @GetMapping("/offers/interesse")
    public ResponseEntity<List<SellerOfferDTO>> getinteresseOffers() {
        return ResponseEntity.ok(sellerService.findAllOfferFavorit1());

    }

    @PutMapping("updateOffer")
    void updateOffer(@RequestBody SellerOffer c)
    {

        sellerService.updateOffer(c);
    }
    @DeleteMapping("deleteOffer/{idc}")
    void deleteOffer(@PathVariable("idc") int idc)
    {

        sellerService.deleteOffer(idc);
    }
    @DeleteMapping("deletenote/{idc}")
    void deletenote(@PathVariable("idc") int idc)
    {

        sellerService.deletenote(idc);
    }

//    @GetMapping("getById/{id}")
//    public ResponseEntity<SellerOffer> getOfferById(@PathVariable int id) {
//        SellerOffer Offer = sellerService.get(id);
//        if (Offer == null) {
//            return ResponseEntity.notFound().build();
//        } else {
//            return ResponseEntity.ok(Offer);
//        }
//    }
    @GetMapping("getbyId/{sellid}")
    public ResponseEntity<SellerOfferDTO> getSellerOffer(
        @PathVariable(name = "sellid") final Integer sellid) {
    return ResponseEntity.ok(sellerService.getById(sellid));
        }
    @GetMapping("findAll")
    public ResponseEntity<List<SellerOfferDTO>> getAllSellerOffers() {
        return ResponseEntity.ok(sellerService.findAll());
    }
//        @GetMapping("offer")
//        public List<SellerOffer> findAllOffer(){
//            return sellerService.findAllOffer();
//        }
@GetMapping("/findByPrice")
public ResponseEntity<List<SellerOfferDTO>> getPriceOffreAsc(){
    return ResponseEntity.ok( sellerService.findOffreByPriceASC());
}
    @GetMapping("offreBySurface/{minSurface}/{maxSurface}")
    public ResponseEntity<List<SellerOfferDTO>> findBySurfaceBetween(@PathVariable("minSurface") Double minSurface,
                                            @PathVariable("maxSurface") Double maxSurface) {
        return ResponseEntity.ok( sellerService.findBySurfaceBetween(minSurface, maxSurface));
    }
    @PostMapping("donnerNote/{offreId}/{user_id}")
    public void donnerRatingg(@RequestParam("raiting") int raiting,@PathVariable("offreId") int offreId,@PathVariable("user_id") int user_id) {
        // Convertir la note sur 10 en une note sur 5


        int noteSur5 = (int) Math.round(raiting * 0.5);
        SellerOffer offre = sellerOfferRepository.findById(offreId).get();

        // Ajouter le rating à l'objet Offre
        offre.setRating(noteSur5);


        // Créer une nouvelle note et la stocker dans la base de données

        Note n=new Note();
        n.setOffre(offre);
        n.setRating(noteSur5);
        Note savednote = sellerService.savenote(n,offreId,user_id);
        ResponseEntity.ok(savednote);



    }
    @GetMapping("getMoyNote/{offreId}")
    public Double calculerMoyenneRaitingParOffre(@PathVariable int offreId) {
       return  sellerService.calculerMoyenneRaitingParOffre(offreId);
    }
    @PutMapping("sold/{id}")
    public ResponseEntity<?> markAsSold(@PathVariable Long id) {
        sellerService.markAsSold(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("favorite/{id}")
    public ResponseEntity<?> markOfferAsFavorite(@PathVariable Integer id) {
        sellerService.markOfferAsFavorite(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("favorite")
    public List<OffreFavorit> findAllOfferfavorit(){
        return favoritOffer.findAllOfferFavorit();
    }

    @DeleteMapping("deleteOfferFavorit/{idc}")
    void deleteOfferFavorit(@PathVariable("idc") int idc)
    {

        sellerService.deleteOfferFavorit(idc);
    }
//    @GetMapping("/findByPrice")
//    public List<SellerOffer> getPlansDesc(){return sellerService.findOffreByPriceASC();}



    @PostMapping("demanderRendezVous/{idOffre}")
    public void demanderRendezVous(@RequestBody RendezVous rendezVous, @PathVariable int idOffre) {
        sellerService.demanderRendezVous(idOffre, rendezVous);
    }
    @PostMapping("addContrat/{user_id}/{sell_id}")
    public int addContrat(@RequestBody SellContract contract,@PathVariable("user_id") int user_id,@PathVariable("sell_id") int sellid)throws MessagingException
    {
        contratService.ajouterContrat(contract,user_id,sellid);
        return contract.getContractsellid();
    }
    @PutMapping("updateContrat")
    void updateContract(@RequestBody SellContract c)
    {

        contratService.updateContrat(c);
    }
    @DeleteMapping("deleteContrat/{idc}")
    void deleteContract(@PathVariable("idc") int id)
    {

        contratService.deleteContrat(id);
    }
    @GetMapping("getContrat/{contractsellid}")
    public ResponseEntity<SellContractDTO> getSellContract(
            @PathVariable(name = "contractsellid") final Integer contractsellid) {
        return ResponseEntity.ok(contratService.get(contractsellid));
    }
    @PostMapping("/charge")
    public ResponseEntity<Charge> createCharge(@RequestParam("token") String token,

                                               @RequestParam("currency") String currency,
                                               @RequestParam ("id") int id
    ) throws Exception

    {

        contratService.createCharge(token,currency,id);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    //    @GetMapping("/{idc}")
//    public ResponseEntity<SellContract> getContratById(@PathVariable int id) {
//        SellContract Contrat = contratService.get(id);
//        if (Contrat == null) {
//            return ResponseEntity.notFound().build();
//        } else {
//            return ResponseEntity.ok(Contrat);
//        }
//    }
//    @GetMapping("Contract")
//    public List<SellContract> findAllContrat(){
//        return contratService.findAllContrat();
//    }
@GetMapping("AllContrat")
public ResponseEntity<List<SellContractDTO>> getAllSellContracts() {
    return ResponseEntity.ok(contratService.findAll());
}

//    @PostConstruct
//    @Scheduled(cron = "0 0 0/12 * * *") // toutes les 12 heures
//    @Scheduled(fixedRate = 86400000) // 24 heures en millisecondes


//    @GetMapping("/offers/favorites")
//    public ResponseEntity<List<SellerOffer> getFavoriteOffers() {
//        List<SellerOffer> favoriteOffers = sellerService.getFavoriteOffers();
//        return ResponseEntity.ok(favoriteOffers);
//    }

//    @GetMapping
//    public List<OffreFavorit> getOfferHistory(@PathVariable int Id) {
//        SellerOffer offer = new SellerOffer();
//        offer.setSellid(Id);
//
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withMatcher("offer", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase());
//
//        Example<OffreFavorit> example = Example.of(new OffreFavorit(null, offer,null, null), matcher);
//        Sort sort = Sort.by(Sort.Direction.DESC, "timestamp");
//        return offerFavoriteRepository.findAll(example, sort);
//    }
//
//


}







