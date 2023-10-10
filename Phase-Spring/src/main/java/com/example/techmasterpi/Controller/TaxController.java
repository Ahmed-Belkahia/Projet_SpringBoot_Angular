package com.example.techmasterpi.Controller;

import com.example.techmasterpi.domain.Payment;
import com.example.techmasterpi.repos.PaymentRepository;
import com.example.techmasterpi.service.DeliveryService;
import com.example.techmasterpi.util.NotFoundException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/taxs", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaxController {
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private PaymentRepository paymentRepository;
    @PostMapping("/charge")
    public ResponseEntity<Charge> createCharge(@RequestParam("token") String token,
                                               @RequestParam("currency") String currency,
                                               @RequestParam("id") int id)
    {
        try {


            Charge charge = deliveryService.createChargeTax(token,currency,id);
            // deliveryService.generatPDF(HttpServletResponse response,charge);
            return ResponseEntity.status(HttpStatus.CREATED).build();


        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
