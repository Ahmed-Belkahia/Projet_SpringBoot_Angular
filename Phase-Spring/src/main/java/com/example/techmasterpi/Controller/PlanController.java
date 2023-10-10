package com.example.techmasterpi.Controller;

import com.example.techmasterpi.domain.Plan;

import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.model.PlanDTO;
import com.example.techmasterpi.repos.PlanRepository;
import com.example.techmasterpi.repos.UserRepository;
import com.example.techmasterpi.service.IPlan;
import com.example.techmasterpi.service.IUserService;
import com.example.techmasterpi.service.PlanService;

import com.example.techmasterpi.service.UserService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.ServletContext;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.File;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/plans")
@CrossOrigin(origins = "*")
public class PlanController {
    @Autowired
    private IPlan plan;
    @Autowired
    IUserService userService;
    @Autowired
    PlanService planService;
    @Autowired
    UserRepository userRepository;
@Autowired
    PlanRepository planRepository;
    @Autowired
    UserService userservice;
    @Autowired  ServletContext context;
    @PostMapping("/addPlan")
    public ResponseEntity<Void> addPlan(@RequestBody Plan p, int userId) throws Exception
    {
        plan.create(p, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addplan1")
    public ResponseEntity<String> addContrat(@RequestBody Plan c, @NonNull HttpServletRequest request){
        int newRentalOfferId = planService.addplan1(c, request);
        if (newRentalOfferId != 0) {
            String message = "Offre de location ajoutée avec succès avec l'id " + newRentalOfferId;
            return ResponseEntity.ok().body(message);
        } else {
            String message = "Erreur lors de l'ajout de l'offre de location";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }


    @GetMapping("/getPlan")
    public ResponseEntity<List<PlanDTO>> getAllPlans()
    {
        List<PlanDTO> plans = plan.findAll();
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/picture")
    public  ResponseEntity<List<PlanDTO>> getPicture()
    {
        List<PlanDTO> plans = plan.PLAN_DTOS();
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanDTO> getPlan(@PathVariable int id) {  PlanDTO contrat = plan.get(id);
        if (contrat == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(contrat);

        }
    }

    @PutMapping("updatePlan")

    public ResponseEntity<String> updateContract(@RequestBody Plan p)
    {
        plan.update(p);
        return ResponseEntity.ok("Le Plan a été mis à jour avec succès.");
    }
    @DeleteMapping("plan/{id}")
    public ResponseEntity<Void> deleteplan(@PathVariable("id") int id) {
        boolean deleted = plan.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/notnull")
    public ResponseEntity<List<PlanDTO>> getPlansByTitleAndPictureNotNull(@RequestParam String title)
    {
        List<PlanDTO> plans = plan.findPlansByTitleAndPictureNotNull(title);
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/tri")
    public ResponseEntity<List<PlanDTO>> getPlans()
    {
        List<PlanDTO> plans = plan.findAllOrderByPriceAsc();
        return ResponseEntity.ok(plans);
    }


    @GetMapping("/triDesc")
    public ResponseEntity<List<PlanDTO>> getPlansDesc()
    {
        List<PlanDTO> plans = plan.findAllOrderByPriceDesc();
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/nb/{idPlan}")
    public ResponseEntity<Integer> nombrecontratparoffre(@PathVariable int idPlan)
    {
        int nbContrats = plan.contractNumberByPlan(idPlan);
        return ResponseEntity.ok(nbContrats);
    }
    @GetMapping("/search")
    public ResponseEntity<List<PlanDTO>> search(@RequestParam Map<String, Object> searchCriteria) {
        List<PlanDTO> plans = planService.dynamicSearch(searchCriteria);
        return ResponseEntity.ok(plans);
    }
    @PostMapping("/addImage/{id}")
    public ResponseEntity<Plan> addPlan(@RequestParam("title") String title,
                                        @RequestParam(value = "image", required = false) MultipartFile image,
                                        @RequestParam("price") Double price,
                                        @RequestParam("realizationprice") Double realizationprice,
                                        @RequestParam("livingroom") Integer livingroom,
                                        @RequestParam("kitchen") Integer kitchen,
                                        @RequestParam("wc") Integer wc,
                                        @RequestParam("room1") Integer room1,
                                        @RequestParam("room2") Integer room2,
                                        @RequestParam("description") String description,
                                        @PathVariable("id") int id)
                                        /*@NonNull HttpServletRequest request)*/ throws Exception {

        // Vérifier si l'utilisateur existe
            //User user = userRepository.findById(userId).get();
    //    User user = userservice.getUserByToken(request);

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        // Créer un nouveau plan
        Plan plan1 = new Plan();
        plan1.setUser(user);
        plan1.setTitle(title);
        plan1.setLivingroom(livingroom);
        plan1.setKitchen(kitchen);
        plan1.setDescription(description);
        plan1.setPrice(price);
        plan1.setRealizationprice(realizationprice);
        plan1.setWc(wc);
        plan1.setRoom1(room1);
        plan1.setRoom2(room2);
        // Enregistrer l'image si elle existe
        if (image != null && !image.isEmpty()) {
            String imagePath = PlanService.saveImage(image, plan1);
            plan1.setPicture(imagePath);
        }
        // Enregistrer le plan
        Plan savedPlan = plan.savePlan(plan1, /*request*/ id);
        return ResponseEntity.ok(savedPlan);
    }
    @GetMapping("/plans/les")
    public ResponseEntity<List<Plan>> getPlansByPriceLessThan(@RequestParam(name = "price") double price) {
        List<Plan> plans = planService.findPlansByPriceLessThan(price);
        return ResponseEntity.ok(plans);
    }
    @PostMapping("/add")
    public int createPlan( @RequestBody Plan plan, @NonNull HttpServletRequest request) {
        return planService.addplan1(plan, request);
    }
    @GetMapping("/revenueoffer/{offerId}")
    public double calculateRevenueForUser(@PathVariable int offerId) {
        Double revenue = planService.getChiffreAffaireByOffer(offerId);
        if (revenue == null) {
            return 0;
        } else {
            return revenue;
        }
    }
}
