package com.example.techmasterpi.Controller;


import com.example.techmasterpi.domain.Delivery;
import com.example.techmasterpi.domain.RentalContract;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.model.RentalContractDTO;
import com.example.techmasterpi.model.UserDTO;
import com.example.techmasterpi.repos.RentalContractRepository;
import com.example.techmasterpi.service.IRentalContract;
import com.example.techmasterpi.service.RentalContractService;
import com.example.techmasterpi.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/rentalcontract")
@CrossOrigin(origins = "*")

public class RentalContractController {
    @Autowired
    IRentalContract rentalContractService;
@Autowired
RentalContractService contractService;
    @Autowired
    RentalContractRepository rentalContractRepository;
    @Autowired
    UserService userService;
    @GetMapping("/GetAllRentalContract")
    public ResponseEntity<List<RentalContractDTO>> getAllRentalContracts(){
        List<RentalContractDTO> rentalContracts = rentalContractService.findAll();
        if(rentalContracts.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(rentalContracts);
        }
    }


    @GetMapping("/getContratYoufaBaadTroisJoirs")
    public List<RentalContractDTO> getContratYoufaBaadTroisJoirs(@NonNull HttpServletRequest request){
        List<RentalContractDTO> rentalContracts = contractService.getContratYoufaBaadTroisJoirs(request);
        if(rentalContracts.isEmpty()){
            return null;
        } else {
            return rentalContracts;
        }
    }

    @GetMapping("/GetUsersFinContrat")
    public ResponseEntity<List<Integer>> getUsersFinContrat(){
        List<Integer> idUserFinContrat = rentalContractService.rappelFinContratAngular();
        if(idUserFinContrat.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(idUserFinContrat);
        }
    }

    @GetMapping("/getRentalContract/{rc}")
    public ResponseEntity<?> getRentalOffer(@PathVariable("rc") int rc) {
        RentalContractDTO rentalContractDTO = rentalContractService.getById(rc);
        if (rentalContractDTO == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(rentalContractDTO);
        }
    }

    @PostMapping("/addRentalContract/{rentalOfferId}")
    public ResponseEntity<String> addContrat(@RequestBody RentalContract rc, @NonNull HttpServletRequest request, @PathVariable("rentalOfferId") int rentalOfferId) throws MessagingException {
        try {
            int contratId = rentalContractService.addRentalContract(rc, request, rentalOfferId);
            if(contratId == -1) throw new Exception() ;
            return ResponseEntity.ok(" contrat : " + contratId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Une erreur s'est produite lors de l'ajout du contrat : " + e.getMessage());
        }
    }



    @GetMapping("/ContractIsValidd/{startdate}/{enddate}")
    public Boolean contractIsValid(@PathVariable("startdate") String startdate,@PathVariable("enddate") String enddate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return contractService.contractIsValidd(LocalDate.parse(startdate,formatter),LocalDate.parse(enddate,formatter));
    }

    /*
    @PostMapping("/ContractIsValid")
    public Boolean contractIsValid(LocalDate startdate, LocalDate enddate, @PathVariable("rentalOfferId")int rentalOfferId) {
        return contractService.contractIsValidd(startdate,enddate);
    }*/

  /*  @GetMapping("/contractIsValid/{date1}/{date2}")
    public ResponseEntity<Boolean> addContrat(@RequestBody RentalContract rc, @PathVariable("date1") int userId, @PathVariable("date2") int rentalOfferId) throws MessagingException {


    }
*/
    @PutMapping("/updateRentalContract")
    public ResponseEntity<String> updateContrat(@RequestBody RentalContract rc) {
        boolean updated = rentalContractService.updateRentalContract(rc);
        if (updated) {
            return ResponseEntity.ok("Rental contract updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update rental contract.");
        }
    }

    @DeleteMapping("/deleteRentalContract/{rc}")
    public ResponseEntity<String> deleteRentalOffer(@PathVariable("rc") int rc) {
        boolean deleted = rentalContractService.deleteRentalContract(rc);
        if (deleted) {
            return ResponseEntity.ok("Rental contract deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rental contract not found.");
        }
    }


    @GetMapping("/revenue")
    public ResponseEntity<String> calculateRevenueForUser(@NonNull HttpServletRequest request) {
        Double revenue = rentalContractService.getChiffreAffaireByUser(request);
        User user = userService.getUserByToken(request);

        if (revenue != null) {
            return ResponseEntity.ok("Chiffre d'affaires pour l'utilisateur " + user.getUserid() + " : " + revenue);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucun chiffre d'affaires n'a été trouvé pour l'utilisateur " + user.getUserid() + ".");
        }
    }


    @PostMapping("/charge")
    public ResponseEntity<Charge> createCharge(@RequestParam("token") String token,
                                               @RequestParam("amount") int amount,
                                               @RequestParam("currency") String currency,
                                               @RequestParam ("id") int id
    ) throws StripeException
    {

        Charge charge = rentalContractService.createCharge(token,amount,currency,id);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }






}
