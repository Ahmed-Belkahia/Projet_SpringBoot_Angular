
package com.example.techmasterpi.Controller;

import com.example.techmasterpi.domain.ContractPlan;
import com.example.techmasterpi.domain.Payment;
import com.example.techmasterpi.model.ContractPlanDTO;
import com.example.techmasterpi.repos.PaymentRepository;
import com.example.techmasterpi.service.IContratPlan;
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
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.List;



@RestController
@RequestMapping(value = "/api/contractPlans")
@CrossOrigin(origins = "http://localhost:4200")
public class ContratPlanController {
     @Autowired
     private IContratPlan contratPlan;
     @Autowired
     private PaymentRepository paymentRepository;
     @PostMapping("/addContrat/{planId}")
     public ResponseEntity<String> createContractPlan(@RequestBody ContractPlan c,
                                                      @NonNull HttpServletRequest request,
                                                      @PathVariable("planId") int planId) throws MessagingException
     {
          try {


               int contractId = contratPlan.create(c, request, planId);
               String message = "Le contrat avec l'ID " + contractId + " a été créé avec succès.";
               return ResponseEntity.status(HttpStatus.CREATED).body(message);
          }
          catch (MessagingException e) {
               return ResponseEntity.badRequest().body(e.getMessage());

          }}


     @GetMapping("/findall")
     public ResponseEntity<List<ContractPlanDTO>> getAllContractPlans() {
          return ResponseEntity.ok(contratPlan.findAllContract());
     }
     @GetMapping("/{id}")
     public ResponseEntity<ContractPlanDTO> getContratById(@PathVariable int id) {
          ContractPlanDTO contrat = contratPlan.get(id);
          if (contrat == null) {
               return ResponseEntity.notFound().build();
          } else {
               return ResponseEntity.ok(contrat);
          }
     }
     @PutMapping("updateContrat")
     ResponseEntity<String> updateContract(@RequestBody ContractPlan c)
     {
          contratPlan.update(c);
          return ResponseEntity.ok("Le contrat a été mis à jour avec succès.");
     }


     @DeleteMapping("contrat/{id}")
     public ResponseEntity<Void> deleteComment(@PathVariable("id") int id) {
          boolean deleted = contratPlan.delete(id);
          if (deleted) {
               return ResponseEntity.noContent().build();
          } else {
               return ResponseEntity.notFound().build();
          }
     }
     @GetMapping("/export/{id}")
     public ResponseEntity<Resource> exportContrat(@PathVariable int id) throws IOException {

          String filename = "contract_" + id + ".pdf";
          String filePath = "C:\\Users\\yassi\\OneDrive\\Desktop" + filename; // Update with your actual desktop path
          // Export the contract to PDF
          contratPlan.exportcontrat(id, filePath);

          // Prepare the file as a Resource
          File file = new File(filePath);
          Path path = file.toPath();
          ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

          // Set the response headers
          HttpHeaders headers = new HttpHeaders();

          headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

          // Return the file as a ResponseEntity
          return ResponseEntity.ok()
                  .headers(headers)
                  .contentLength(file.length())
                  .contentType(MediaType.parseMediaType("application/pdf"))
                  .body(resource);

     }

     @PostMapping("/charge")
     public ResponseEntity<Charge> createCharge(@RequestParam("token") String token,

                                                @RequestParam("currency") String currency,
                                                @RequestParam ("id") int id
     ) throws StripeException
     {

          Charge charge = contratPlan.createCharge(token,currency,id);
          return ResponseEntity.status(HttpStatus.CREATED).build();
     }
     @GetMapping("/revenue")
     public ResponseEntity<Double> calculateRevenueForUser(@NonNull HttpServletRequest request) {
          Double revenue = contratPlan.Revnnu(request);
          if (revenue != null) {
               return ResponseEntity.ok(revenue);
          } else {
               return ResponseEntity.notFound().build();
          }
     }

     @GetMapping("/getpayement")
     public ResponseEntity<List<Payment>> getAllPayement() throws ParseException {
          return ResponseEntity.ok(paymentRepository.findAll());
     }

}
