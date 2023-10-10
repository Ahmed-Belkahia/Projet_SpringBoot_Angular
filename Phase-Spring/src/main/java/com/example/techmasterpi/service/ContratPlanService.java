package com.example.techmasterpi.service;


import com.example.techmasterpi.domain.*;
import com.example.techmasterpi.model.ContractPlanDTO;
import com.example.techmasterpi.util.NotFoundException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import com.example.techmasterpi.repos.ContractPlanRepository;
import com.example.techmasterpi.repos.PlanRepository;
import com.example.techmasterpi.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class ContratPlanService implements  IContratPlan {
    @Autowired
    private ContractPlanRepository contractPlanRepository;
    @Value("${spring.mail.from}")
    private String from;

    @Autowired
    private Session session;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlanRepository planRepository;
@Autowired
private  UserService userService;



    @Override
    public List<ContractPlanDTO> findAllContract() {

        final List<ContractPlan> contractPlanList = contractPlanRepository.findAll(Sort.by("contractId"));
        return contractPlanList.stream()
                .map((contractPlan) -> mapToDTO(contractPlan, new ContractPlanDTO()))
                .collect(Collectors.toList());

    }

    public ContractPlanDTO get(final Integer contractId) {
        return contractPlanRepository.findById(contractId)
                .map(contractPlan -> mapToDTO(contractPlan, new ContractPlanDTO()))
                .orElseThrow(NotFoundException::new);
    }

   /* @Override
    public int create(ContractPlan c, int userid, int planid) throws MessagingException {
        User user = userRepository.findById(userid).get();
        Plan plan = planRepository.findById(planid).get();
        Double price = plan.getPrice();
        Double realizationprice = plan.getRealizationprice();
        c.setPlanUser(user);
        c.setPlanContractPlan(plan);
        List<ContractPlan> contractPlanList = contractPlanRepository.findAll();
        c.setPrice(price + realizationprice);
        for (ContractPlan contractPlan : contractPlanList) {
            if (contractPlan.getPlanUser().getUserid() == userid && contractPlan.getStatus().equals("Payed") )  {
                c.setPrice((c.getPlanContractPlan().getPrice()+c.getPlanContractPlan().getRealizationprice()) * 0.95);
            }
             else {
                c.setPrice(price + realizationprice);
            }
        }
     //   sendEmail(c.getPlanUser().getEmail(), "text", "text");
        String htmlBody = "<h1><strong>Titre en gras</strong></h1><p><em>Texte en italique</em></p>";
        sendEmail("medhabib.dhaouadi@esprit.tn","text", "text", htmlBody);
        return contractPlanRepository.save(c).getContractId();
    }
*/
    @Override
   public int create(ContractPlan c, @NonNull HttpServletRequest request, int planid) throws MessagingException {
       // User user = userRepository.findById(userid).get();
        User user = userService.getUserByToken(request);
        Plan plan = planRepository.findById(planid).get();
        Double price = plan.getPrice();
        Double realizationprice = plan.getRealizationprice();
        c.setPlanUser(user);
        c.setPlanContractPlan(plan);
        c.setContractDate(LocalDate.now());
        List<ContractPlan> contractPlanList = contractPlanRepository.findAll();
        c.setPrice(price + realizationprice);

        // check if the user has an old contract with "status" field equal to "Payed"
        boolean hasOldContract = contractPlanList.stream()
                .anyMatch(contractPlan -> request == request
                        && contractPlan.getStatus() != null
                        && contractPlan.getStatus().equals("Payed"));

        // if the user has an old contract with "status" field equal to "Payed", apply a 5% discount
        if (hasOldContract) {
            c.setPrice((price + realizationprice) * 0.95);
        }

        // if the user has an old contract with null "status" field, throw an exception
        boolean hasNullStatusContract = contractPlanList.stream()
                .anyMatch(contractPlan -> request == request
                        && contractPlan.getStatus() == null);

        if (hasNullStatusContract) {
            throw new MessagingException("User already has a contract with null status");

        }

        // if the user does not have an old contract with null status, create a new contract
        return contractPlanRepository.save(c).getIdContrat();
    }

    @Override
    public void update(ContractPlan c) {
        if (contractPlanRepository.findById(c.getContractId()).isPresent())
            contractPlanRepository.save(c);
        else
            System.out.println("doesnt exist");

    }

    @Override
    public boolean delete(int id) {
        if (contractPlanRepository.existsById(id)) {
            contractPlanRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void sendEmail(String to, String subject, String text, String htmlBody) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);

        // Create the plain text part of the message
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(text);

        // Create the HTML part of the message
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlBody, "text/html");

        // Create a multipart message and add the text and HTML parts to it
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);
        multipart.addBodyPart(htmlPart);

        // Set the content of the message to the multipart message
        message.setContent(multipart);

        // Send the message
        Transport.send(message);
    }



    @Override


    public void exportcontrat(int idContrat, String filePath) throws IOException {
        try {
            ContractPlan contractPlan = contractPlanRepository.findById(idContrat).get();

            String htmlContent = "<!DOCTYPE html> \n" +
                    "<html>\n" +
                    "    <head><style>body{background-color: rgb(156, 155, 154);}h1{text-align: center ;font-weight: bold;color: rgb(34, 33, 33);font-family: cursive;}p{text-align: center;font-size: medium;} </style></head>\n" +
                    "    <body>\n" +
                    "      <h1>Tech Master</h1> \n \n" +
                    "      \n" +
                    "      <p>Date Contract: "+ contractPlan.getContractDate() +"</p>\n" +
                    "      <p>Price: "+contractPlan.getPrice() +"</p>\n" +
                    "      <p>NomClient: "+ contractPlan.getPlanUser().getLastname()+" </p>\n" +
                    "      <p>Title Of Plan: "+  contractPlan.getPlanContractPlan().getTitle() +"</p>\n" +
                    "      <p>Current Date: "+new Date() +"</p>\n" +
                    "   \n" +
                    "    </body>\n" +
                    "</html>";

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(htmlContent.getBytes());
            worker.parseXHtml(writer, document, inputStream);
            document.close();
        } catch (DocumentException e) {
            // Handle DocumentException
        }
    }

    private ContractPlanDTO mapToDTO(final ContractPlan contractPlan,
                                     final ContractPlanDTO contractPlanDTO) {
        contractPlanDTO.setContractId(contractPlan.getContractId());
        contractPlanDTO.setContractDate(contractPlan.getContractDate());
        contractPlanDTO.setPrice(contractPlan.getPrice());
        contractPlanDTO.setStatut(contractPlan.getStatut());
        contractPlanDTO.setPlanUser(contractPlan.getPlanUser() == null ? null : contractPlan.getPlanUser().getUserid());
        contractPlanDTO.setUserContartplan(contractPlan.getPlanUser() == null ? null : contractPlan.getPlanUser().getUserid());
        contractPlanDTO.setPlanContractPlan(contractPlan.getPlanContractPlan() == null ? null : contractPlan.getPlanContractPlan().getPlanid());
        return contractPlanDTO;
    }
    @Override
    public Charge createCharge(String token, String currency, int idcontract) throws StripeException {
        ContractPlan c =contractPlanRepository.findById(idcontract).orElse(new ContractPlan());
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", c.getPrice().longValue());
        chargeParams.put("currency", currency);
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        ContractPlan contractPlan = contractPlanRepository.findById(idcontract).get();
        int id = contractPlan.getIdContrat();
        String lastName = contractPlan.getPlanUser().getLastname();
        Payment p = new Payment();
        String secretKey = "sk_test_51MfQE5A4XVwRTZvQpqlDgKCgtQbHkbsjgE3Kqud02mPHSBozFeSSKxdDnhFfNOjKpc9sKiFy95lW7pGShrU6eLzM00rTq1fqev";
        if (contractPlan.getStatut() == null)
        p.setAmount(charge.getAmount());
        p.setCardNumber(secretKey);
        p.setCurrency(charge.getCurrency());
        p.setCardholderName(lastName);
        p.setCvc("test");
        contractPlan.setPaymentPlan(p);
        contractPlan.setStatut("Payed");
        p.setTypePayment("PLAN");
        p.setDatepaiement(LocalDate.now());
        p.setContractPlan(contractPlan);
        contractPlanRepository.save(contractPlan);
        return charge;
    }
    //Methode lancer chaque jour a 10h du matin pour rappeler les client qui non pas payer leur contract
    @Override
    //@Scheduled(cron = "0 22 22 * * *")
    public void messageNonPayment() throws MessagingException {
        List<ContractPlan> contractPlans =  contractPlanRepository.findAll();
        String mail ="" ;
        for (ContractPlan contractPlan : contractPlans){
            if (contractPlan.getStatut()== null){
                mail =  contractPlan.getPlanUser().getEmail();
                String htmlBody = "<!DOCTYPE html> \n" +
                        "<html>\n" +
                        "    <head><style> header{background-color: beige; font-weight: bold;font-family: cursive ; text-align: center;}div{background-color: beige;}</style></head>\n" +
                        "    <body>\n" +
                        "       <header>TechMaster</header>\n" +
                        "          <div>\n" +
                        "          <h3>Bonjour </h3>" + contractPlan.getPlanUser().getLastname()+" "+ contractPlan.getPlanUser().getFirstname()+
                        "           <a>veuillez procéder au paiement</a>   \n" +
                        "<footer>Cordialement.</footer>    \n" +
                        "          </div>\n" +
                        "       \n" +
                        "    </body>\n" +
                        "</html>";
                sendEmail(mail,"Payment", "", htmlBody);
                System.out.println(contractPlan.getPlanUser().getEmail());
            }
        }
    }
    @Override
    public double Revnnu(@NonNull HttpServletRequest request) {
        List<Plan> plansListByUser=new ArrayList<>();
        List<Plan> plansList =planRepository.findAll();
        User user = userService.getUserByToken(request);
        int userId = user.getUserid();
        System.out.println(userId);
        for (Plan plan:plansList) {
            if (plan.getUser().getUserid()==userId){
                plansListByUser.add(plan);
            }
        }
        List<ContractPlan> planContractListUser=new ArrayList<>();
        List<ContractPlan> planContractList=contractPlanRepository.findAll();
        for (ContractPlan contractPlan:planContractList){
            for (Plan plan:plansListByUser){
                if (contractPlan.getPlanContractPlan().getPlanid()==plan.getPlanid()){
                    planContractListUser.add(contractPlan);
                }}
        }

        Double totalRevenue = 0.0;
        for (ContractPlan contractPlan : planContractListUser) {
            totalRevenue += contractPlan.getPrice();
        }
        return totalRevenue;    }


}
