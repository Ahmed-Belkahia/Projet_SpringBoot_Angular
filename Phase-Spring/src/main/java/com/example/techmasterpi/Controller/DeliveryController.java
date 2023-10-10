package com.example.techmasterpi.Controller;



import com.example.techmasterpi.domain.Payment;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.model.DeliveryDTO;
import com.example.techmasterpi.repos.PaymentRepository;
import com.example.techmasterpi.service.DeliveryService;
import com.example.techmasterpi.util.NotFoundException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/deliverys", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    private final PaymentRepository paymentRepository;

    public DeliveryController( DeliveryService deliveryService,
                               PaymentRepository paymentRepository) {
        this.deliveryService = deliveryService;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping
    public ResponseEntity<List<DeliveryDTO>> getAllDeliverys() throws ParseException {
        deliveryService.calculeTax();

        return ResponseEntity.ok(deliveryService.findAll());
    }

    @GetMapping("/{deliveryid}")
    public ResponseEntity<DeliveryDTO> getDelivery(
            @PathVariable(name = "deliveryid")  Integer deliveryid) {
        return ResponseEntity.ok(deliveryService.get(deliveryid));
    }
    @GetMapping("/bestchifferdaffaire")
    public String getbestchiifre(){
        return deliveryService.bestMonthDelivery();
    }


    @Scheduled(cron="0 0 0 * * *")
    @GetMapping("/BestDelivery")
    public ResponseEntity<String> getBestDelivery() {
     User d=  deliveryService.finbBestRelocateur();
     System.out.println(d.getUserid());

        return ResponseEntity.ok(d.getFirstname());
    }

    @PostMapping("/test/{idrelocation}")
    public ResponseEntity<Integer> createDelivery(
            @RequestBody @Valid  DeliveryDTO deliveryDTO,
            @NonNull HttpServletRequest iduser ,
            @PathVariable("idrelocation")Integer idrelocation) throws MessagingException, IOException {
        deliveryService.create(deliveryDTO,iduser,idrelocation);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/charge")
    public ResponseEntity<Charge> createCharge(@RequestParam("token") String token,
                                               @RequestParam("currency") String currency,
                                               @RequestParam("iddelevry")int iddelivery) throws Exception {
        try {


            Charge charge = deliveryService.createCharge(token,currency,iddelivery);
           // deliveryService.generatPDF(HttpServletResponse response,charge);

            return ResponseEntity.status(HttpStatus.CREATED).build();


        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/{deliveryid}")
    public ResponseEntity<Void> updateDelivery(
            @PathVariable(name = "deliveryid")  Integer deliveryid,
            @RequestBody @Valid  DeliveryDTO deliveryDTO) {
        deliveryService.update(deliveryid, deliveryDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{deliveryid}")
    public ResponseEntity<Void> deleteDelivery(
            @PathVariable(name = "deliveryid") Integer deliveryid) {
        deliveryService.delete(deliveryid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/factures/{id}")
    public ResponseEntity<byte[]> telechargerFacturePDF(@PathVariable("id") Long id) {
        try {
            // Récupérer la facture depuis la base de données
            Payment facture = paymentRepository.findById(id).orElseThrow(() -> new NotFoundException("Facture non trouvée"));

            // Générer le contenu PDF de la facture
            byte[] contenuPDF = deliveryService.genererFacturePDF(facture);


            // Ajouter les en-têtes de la réponse
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.attachment().filename("facture.pdf").build());

            // Retourner la réponse avec le contenu PDF
            return new ResponseEntity<>(contenuPDF, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
