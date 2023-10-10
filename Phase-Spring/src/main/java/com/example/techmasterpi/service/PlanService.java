package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.ContractPlan;
import com.example.techmasterpi.domain.Plan;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.model.PlanDTO;
import com.example.techmasterpi.repos.PlanRepository;
import com.example.techmasterpi.repos.UserRepository;
import com.example.techmasterpi.util.NotFoundException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PlanService  implements IPlan {

    @Autowired

    private PlanRepository planRepository;

    private List<Plan> plans = new ArrayList<>();
    @Autowired
    private UserRepository userRepository;
    public static final String ACCOUNT_SID = "AC74f6ca913c873955a421df73ce4efe20";
    public static final String AUTH_TOKEN = "833d1953229f6413f64af4845205c28d";
    public static final String FROM_NUMBER = "+15673131571";

    @Autowired
    private EntityManager entityManager;
    @Autowired
    UserService userService;

     ContractPlan ContractList = new ContractPlan();
    @Override
    public List<PlanDTO> findAll() {
        final List<Plan> plans = planRepository.findAll(Sort.by("planid"));
        return plans.stream()
                .map((plan) -> mapToDTO(plan, new PlanDTO()))
                .collect(Collectors.toList());
    }


    @Override
    public List<PlanDTO> PLAN_DTOS() {
        final List<Plan> plans = planRepository.picturenotnull();
        return plans.stream()
                .map((plan) -> mapToDTO(plan, new PlanDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public PlanDTO get(final Integer planid) {
        return planRepository.findById(planid)
                .map(plan -> mapToDTO(plan, new PlanDTO()))
                .orElseThrow(NotFoundException::new);
    }
    @Override
    public Plan create(Plan p, int userId) throws Exception {
        User user = userRepository.findById(userId).get();
       // User user = userService.getUserByToken(request);
        p.setUser(user);
        return planRepository.save(p);
    }

    @Override
    public Boolean update(Plan c) {
    /*    if (planRepository.findById(c.getPlanid()).isPresent())
            planRepository.save(c);
        else
            System.out.println("doesnt exist");
        */
        if(planRepository.existsById(c.getPlanid())){
            planRepository.save(c).equals(c);
            return true;
        }else {
            return false;
        }
    }



    @Override
    public boolean delete(int id) {
        if (planRepository.existsById(id)) {
            planRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<PlanDTO> findPlansByTitleAndPictureNotNull(String title) {
        final List<Plan> plans = planRepository.findByTitleAndPictureIsNotNull(title);
        return plans.stream()
                .map((plan) -> mapToDTO(plan, new PlanDTO()))
                .collect(Collectors.toList());
    }
    public List<Plan> findPlansByPriceLessThan(double price) {
        return planRepository.findByPriceLessThan(price);
    }
    public void sendSMS(String toPhoneNumber, String messageBody){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(FROM_NUMBER), messageBody).create();
        System.out.println("SMS envoyé avec succès avec l'ID : " + message.getSid());
    }

    @Override
    public List<PlanDTO> findAllOrderByPriceAsc() {
        final List<Plan> plans = planRepository.findAllOrderByPriceAsc();
        return plans.stream()
                .map((plan) -> mapToDTO(plan, new PlanDTO()))
                .collect(Collectors.toList());

    }
    @Override
    public List<PlanDTO> findAllOrderByPriceDesc(){

        final List<Plan> plans = planRepository.findAllOrderByPriceDesc();
        return plans.stream()
                .map((plan) -> mapToDTO(plan, new PlanDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public int contractNumberByPlan(int idPlan){
        Plan plan = planRepository.findById(idPlan).get();
        Set<ContractPlan> contractPlans = plan.getPlanContractPlanContractPlans();
        int nbContract = 0;
        for (ContractPlan contractPlan : contractPlans){
            nbContract+=1;
        }
        return nbContract;
    }

    private PlanDTO mapToDTO(final Plan plan, final PlanDTO planDTO) {
        planDTO.setPlanid(plan.getPlanid());
        planDTO.setTitle(plan.getTitle());
        planDTO.setPicture(plan.getPicture());
        planDTO.setPrice(plan.getPrice());
        planDTO.setRealizationprice(plan.getRealizationprice());
        planDTO.setLivingroom(plan.getLivingroom());
        planDTO.setKitchen(plan.getKitchen());
        planDTO.setWc(plan.getWc());
        planDTO.setRoom1(plan.getRoom1());
        planDTO.setRoom2(plan.getRoom2());
        planDTO.setDescription(plan.getDescription());
        return planDTO;
    }

    @Override
    public Plan savePlan(Plan plan, /*@NonNull HttpServletRequest request*/ int id) throws Exception {
       // sendSMS("+","hello");
       // User user = userService.getUserByToken(request);
        User user = userRepository.findById(id).get();
        plan.setUser(user);
        return planRepository.save(plan);
    }



    public List<PlanDTO> dynamicSearch(Map<String, Object> searchCriteria) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Plan> query = builder.createQuery(Plan.class);
            Root<Plan> root = query.from(Plan.class);

            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> entry : searchCriteria.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (value != null && !value.toString().isEmpty()) {
                    if (root.get(key).getJavaType() == String.class) {
                        predicates.add(builder.like(root.get(key), "%" + value + "%"));
                    } else if (root.get(key).getJavaType() == Integer.class) {
                        predicates.add(builder.equal(root.get(key), Integer.parseInt(value.toString())));
                    } else if (root.get(key).getJavaType() == Date.class) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date = sdf.parse(value.toString());
                            predicates.add(builder.equal(root.get(key), date));
                        } catch (ParseException e) {
                            e.printStackTrace();

                        }
                    }
                }
            }

            query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
            TypedQuery<Plan> typedQuery = entityManager.createQuery(query);
            return typedQuery.getResultList().stream().map((plan -> mapToDTO(plan, new PlanDTO()))).collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();

            return Collections.emptyList();
        }
    }

    public static String saveImage(MultipartFile image, Plan plan) throws IOException {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        Path path = Paths.get("uploads");
        Files.createDirectories(path);
        try (InputStream inputStream = image.getInputStream()) {
            Path filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            plan.setPicture(filePath.toString());
            return filePath.toString();
        } catch (IOException e) {
            throw new IOException("Could not save file " + fileName, e);
        }
    }

    @Override
    public int addplan1(Plan plan,@NonNull HttpServletRequest request) {
        //User user=userRepository.findById(idUser).get();
        User user = userService.getUserByToken(request);
        
        plan.setUser(user);
        return planRepository.save(plan).getPlanid();
    }


    @Override
    public double getChiffreAffaireByOffer(int offerId) {
        Plan plan= planRepository.findById(offerId).get();
        Set<ContractPlan> ContractList=plan.getPlanContractPlanContractPlans();
        double prix=0.0;
        for (ContractPlan contract:ContractList){
              prix+= contract.getPrice();
        }
        return prix;
    }

}
