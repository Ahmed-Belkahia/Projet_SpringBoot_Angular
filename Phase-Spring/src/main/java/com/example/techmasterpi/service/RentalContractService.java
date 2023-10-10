package com.example.techmasterpi.service;


import com.example.techmasterpi.domain.PaymentRentalContrat;
import com.example.techmasterpi.domain.RentalContract;
import com.example.techmasterpi.domain.RentalOffer;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.model.RentalContractDTO;
import com.example.techmasterpi.repos.RentalContractRepository;
import com.example.techmasterpi.repos.RentalOfferRepository;
import com.example.techmasterpi.repos.UserRepository;
import com.example.techmasterpi.util.NotFoundException;

import com.itextpdf.text.Document;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.time.LocalDate;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

@Slf4j

@Service
public class RentalContractService implements IRentalContract{
    @Autowired
    private Session session;
    @Autowired
    RentalContractRepository rentalContractRepository;
@Autowired
    UserRepository userRepository;
@Autowired
RentalOfferRepository rentalOfferRepository;
@Autowired
UserService userService;
    @Override
    public int addRentalContract(RentalContract rentalContract, @NonNull HttpServletRequest request, int rentalOfferId) throws MessagingException {
        LocalDate enddate=rentalContract.getStartdate().plusMonths(rentalContract.getNbmonth());
      rentalContract.setEnddate(enddate); //generation automatique de ladate fin

      /*  User user=userRepository.findById(userId).get();
        RentalOffer rentalOffer=rentalOfferRepository.findById(rentalOfferId).get();
        rentalContract.setUserRentalcontract(user);
        rentalContract.setRentalofferRentalContract(rentalOffer);*/

        if (!contractIsValid(rentalContract))
           return  -1;
        User user = userService.getUserByToken(request);
       // User user=userRepository.findById(userId).get();
        RentalOffer rentalOffer=rentalOfferRepository.findById(rentalOfferId).get();
        Set<RentalContract> rentalContractList=rentalOffer.getRentalofferRentalContractRentalContracts();
    rentalContract.setUserRentalcontract(user);
      rentalContract.setRentalofferRentalContract(rentalOffer);
        rentalContract.setPrice(rentalContract.getRentalofferRentalContract().getMonthlyrent()*rentalContract.getNbmonth());



        return rentalContractRepository.save(rentalContract).getContractid();
    }

    public boolean contractIsValid(RentalContract rentalContract){
        List<RentalContract> rentalContractList=rentalContractRepository.
        getActiveRentalContractByDates(rentalContract.getStartdate(),rentalContract.getEnddate());
        return rentalContractList.isEmpty();
    }

    public boolean contractIsValidd(LocalDate startdate,LocalDate enddate){
        List<RentalContract> rentalContractList=rentalContractRepository.
                getActiveRentalContractByDates(startdate,enddate);
System.out.println(rentalContractList.isEmpty());
        return rentalContractList.isEmpty();
    }

    @Override
    public boolean updateRentalContract(RentalContract rentalContract) {
        if(rentalContractRepository.existsById(rentalContract.getContractid())){
            rentalContractRepository.save(rentalContract).equals(rentalContract);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean deleteRentalContract(int id) {
        if(rentalContractRepository.existsById(id)){
            rentalContractRepository.deleteById(id);
            return true;
        }else{
            return  false;
        }
    }

   /* @Override
    public RentalContract getById(int id) {
        return rentalContractRepository.findById(id).get();
    }*/
    @Override
    public RentalContractDTO getById(final Integer contractid) {
        return rentalContractRepository.findById(contractid)
                .map(rentalContract -> mapToDTO(rentalContract, new RentalContractDTO()))
                .orElseThrow(NotFoundException::new);
    }
   /* @Override
    public List<RentalContract> getAll() {
        return rentalContractRepository.findAll();
    }*/
    @Override
    public List<RentalContractDTO> findAll() {
        final List<RentalContract> rentalContracts = rentalContractRepository.findAll(Sort.by("contractid"));
        return rentalContracts.stream()
                .map((rentalContract) -> mapToDTO(rentalContract, new RentalContractDTO()))
                .collect(Collectors.toList());
    }

    private RentalContractDTO mapToDTO(final RentalContract rentalContract,
                                       final RentalContractDTO rentalContractDTO) {
        rentalContractDTO.setContractid(rentalContract.getContractid());
        rentalContractDTO.setStartdate(rentalContract.getStartdate());
        rentalContractDTO.setEnddate(rentalContract.getEnddate());
        rentalContractDTO.setPrice(rentalContract.getPrice());
        rentalContractDTO.setNbmonth(rentalContract.getNbmonth());
        rentalContractDTO.setRentalIOfferContractRental(rentalContract.getRentalofferRentalContract() == null ? null : rentalContract.getRentalofferRentalContract().getOffreid());
        rentalContractDTO.setUserRentalcontract(rentalContract.getUserRentalcontract() == null ? null : rentalContract.getUserRentalcontract().getUserid());
        rentalContractDTO.setRentalofferRentalContract(rentalContract.getRentalofferRentalContract() == null ? null : rentalContract.getRentalofferRentalContract().getOffreid());
        return rentalContractDTO;
    }
    @Override
    public double getChiffreAffaireByUser(@NonNull HttpServletRequest request) {
        List<RentalOffer> rentalOfferListByUser=new ArrayList<>();
        List<RentalOffer> rentalOfferList =rentalOfferRepository.findAll();
        User user = userService.getUserByToken(request);

        for (RentalOffer rentalOffer:rentalOfferList) {
if (rentalOffer.getUser().getUserid()==user.getUserid()){
rentalOfferListByUser.add(rentalOffer);
}
        }

List<RentalContract> rentalContractListUser=new ArrayList<>();
        List<RentalContract> rentalContractList=rentalContractRepository.findAll();
        for (RentalContract rentalContract:rentalContractList){
            for (RentalOffer rentalOffer:rentalOfferListByUser){
            if (rentalContract.getRentalofferRentalContract().getOffreid()==rentalOffer.getOffreid()){
                rentalContractListUser.add(rentalContract);
            }}
        }

        Double totalRevenue = 0.0;
        for (RentalContract rentalContract : rentalContractListUser) {
            totalRevenue += rentalContract.getPrice();
        }
        return totalRevenue;    }



    /*public void sendEmail(String to, String subject, String text) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(text);
        Transport.send(message);
    }*/
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
    /* @Override
    public void exportcontrat(  int idContrat, String filePath) throws IOException {
        try {


            Document document = new Document(PageSize.A4) {
            };
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            RentalContract contract =  rentalContractRepository.findById(idContrat).get();
            String startdate = "Start Date Of Contract: " + contract.getStartdate().toString();
            String enddate = "End Date Of Contract: " + contract.getEnddate().toString();


            String currentDate = formatter.format(new Date());

            document.open();

            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontTitle.setSize(20);

            Paragraph paragraph = new Paragraph("Tech Master ", fontTitle);
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);

            Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
            fontParagraph.setSize(16);



            Paragraph paragraphStartDate = new Paragraph(startdate, fontParagraph);
            paragraphStartDate.setAlignment(Paragraph.ALIGN_CENTER);
            Paragraph paragraphEndDate = new Paragraph(enddate, fontParagraph);
            paragraphEndDate.setAlignment(Paragraph.ALIGN_CENTER);



            document.add(paragraph);
            document.add(paragraphStartDate);
            document.add(paragraphEndDate);
            String htmlString = "<h1>Example HTML</h1><p>This is an example of HTML content in a PDF.</p>";

            InputStream htmlInput = new ByteArrayInputStream(htmlString.getBytes());

            // Convertir le HTML en PDF et l'ajouter au document
            ConverterProperties converterProperties = new ConverterProperties();
            HtmlConverter.convertToPdf(htmlInput, document, converterProperties);

            document.close();

        }
        catch (DocumentException e) {
        }
    }
    */

    /*@Override
    public List<RentalContract> rappelFinContrat() {

        List<RentalContract> rentalContractList= rentalContractRepository.findAll();
        List<RentalContract> rentalContractsEndDate=new ArrayList<>();
        LocalDate date = LocalDate.now();

        for (RentalContract r:rentalContractList){
            LocalDate avantTroisJoursDateFin =r.getEnddate().minusDays(3);
            if (
                    (avantTroisJoursDateFin != null && date.isEqual(avantTroisJoursDateFin)) ||
                     (avantTroisJoursDateFin != null&&date.isAfter(avantTroisJoursDateFin)&& date.isBefore(r.getEnddate()))
            ){
                rentalContractsEndDate.add(r);
           }}
      return  rentalContractsEndDate;}*/

/*
    @Override
    @Scheduled(cron = "0 05 21 * * *")

    public void rappelFinContrat() throws MessagingException {

        List<RentalContract> rentalContractList= rentalContractRepository.findAll();
        List<RentalContract> rentalContractsEndDate=new ArrayList<>();
        LocalDate date = LocalDate.now();

        for (RentalContract r:rentalContractList){
            LocalDate avantTroisJoursDateFin =r.getEnddate().minusDays(3);
            if (
                    (avantTroisJoursDateFin != null && date.isEqual(avantTroisJoursDateFin)) ||
                            (avantTroisJoursDateFin != null&&date.isAfter(avantTroisJoursDateFin)&& date.isBefore(r.getEnddate()))
            ){
                rentalContractsEndDate.add(r);
                System.out.println("Ce contrat est d'id :" + r.getContractid());
               User user=userRepository.findById(r.getUserRentalcontract().getUserid()).get();
String email= user.getEmail();
System.out.println(email);
String msg="Je vous rappel que la fin de votre contrat est le :"+r.getEnddate();
                String htmlBody = "<!DOCTYPE html> \n" +
                        "<html>\n" +
                        "    <head><style> header{background-color: beige; font-weight: bold;font-family: cursive ; text-align: center;}div{background-color: beige;}</style></head>\n" +
                        "    <body>\n" +
                        "       <header>TechMaster</header>\n" +
                        "          <div>\n" +
                        "          <h3>Bonjour </h3>" + user.getLastname()+" "+ user.getFirstname()+
                        "           <a>On vous rappel que la fin de votre contrat aura lieu le :</a>   \n" +r.getEnddate()+
                        "<footer>Bonne journée.</footer>    \n" +
                        "          </div>\n" +
                        "       \n" +
                        "    </body>\n" +
                        "</html>";
                sendEmail(email,"Rappel Fin Abonnement", "", htmlBody);
//this.sendEmail(email,"Rappel Fin Abonnement",msg);
            }}
       }
// ...
*/
/*
    @Override
    public void rappelFinContrat() throws MessagingException {

        List<RentalContract> rentalContractList= rentalContractRepository.findAll();
        List<RentalContract> rentalContractsEndDate=new ArrayList<>();
        LocalDate date = LocalDate.now();

        for (RentalContract r:rentalContractList){
            LocalDate avantTroisJoursDateFin =r.getEnddate().minusDays(3);
            if (
                    (avantTroisJoursDateFin != null && date.isEqual(avantTroisJoursDateFin)) ||
                            (avantTroisJoursDateFin != null&&date.isAfter(avantTroisJoursDateFin)&& date.isBefore(r.getEnddate()))
            ){
                rentalContractsEndDate.add(r);
                System.out.println("Ce contrat est d'id :" + r.getContractid());
                User user=userRepository.findById(r.getUserRentalcontract().getUserid()).get();
                String email= user.getEmail();
                System.out.println(email);
                String msg="Je vous rappel que la fin de votre contrat est le :"+r.getEnddate();
                String htmlBody = "<!DOCTYPE html> \n" +
                        "<html>\n" +
                        "    <head><style> header{background-color: beige; font-weight: bold;font-family: cursive ; text-align: center;}div{background-color: beige;}</style></head>\n" +
                        "    <body>\n" +
                        "       <header>TechMaster</header>\n" +
                        "          <div>\n" +
                        "          <h3>Bonjour </h3>" + user.getLastname()+" "+ user.getFirstname()+
                        "           <a>On vous rappel que la fin de votre contrat aura lieu le :</a>   \n" +r.getEnddate()+
                        "<footer>Bonne journée.</footer>    \n" +
                        "          </div>\n" +
                        "       \n" +
                        "    </body>\n" +
                        "</html>";
                sendEmail(email,"Rappel Fin Abonnement", "", htmlBody);
//this.sendEmail(email,"Rappel Fin Abonnement",msg);
            }}
    }*/
    /*
    public List<RentalContract> GetContratFin(int idUser) {

        List<RentalContract> rentalContractList= rentalContractRepository.findAll();
        List<RentalContract> rentalContractsEndDate=new ArrayList<>();
        LocalDate date = LocalDate.now();
List<User> userFinContrat=new ArrayList<>();
    List<Integer> idUserFinContrat=new ArrayList<>();

        for (RentalContract r:rentalContractList){
            LocalDate avantTroisJoursDateFin =r.getEnddate().minusDays(3);
            System.out.println("avantTroisJoursDateFin"+avantTroisJoursDateFin);
            System.out.println("local date"+date);

            if (
                    (avantTroisJoursDateFin != null && date.isEqual(avantTroisJoursDateFin)) ||
                            (avantTroisJoursDateFin != null&&date.isAfter(avantTroisJoursDateFin)&& date.isBefore(r.getEnddate()))
            ){
                rentalContractsEndDate.add(r);
            }}

System.out.println(rentalContractsEndDate);
    for(RentalContract r:rentalContractsEndDate){
        if(!userFinContrat.contains(r))
            idUserFinContrat.add(r.getUserRentalcontract().getUserid());
    }
        return  idUserFinContrat;}
*/
    @Override

    public List<Integer> rappelFinContratAngular() {

        List<RentalContract> rentalContractList= rentalContractRepository.findAll();
        List<RentalContract> rentalContractsEndDate=new ArrayList<>();
        LocalDate date = LocalDate.now();
        List<User> userFinContrat=new ArrayList<>();
        List<Integer> idUserFinContrat=new ArrayList<>();

        for (RentalContract r:rentalContractList){
            LocalDate avantTroisJoursDateFin =r.getEnddate().minusDays(3);
            System.out.println("avantTroisJoursDateFin"+avantTroisJoursDateFin);
            System.out.println("local date"+date);

            if (
                    (avantTroisJoursDateFin != null && date.isEqual(avantTroisJoursDateFin)) ||
                            (avantTroisJoursDateFin != null&&date.isAfter(avantTroisJoursDateFin)&& date.isBefore(r.getEnddate()))
            ){
                rentalContractsEndDate.add(r);
            }}
        System.out.println(rentalContractsEndDate);
        for(RentalContract r:rentalContractsEndDate){
            if(!userFinContrat.contains(r))
                idUserFinContrat.add(r.getUserRentalcontract().getUserid());
        }
        return  idUserFinContrat;}

    public List<RentalContractDTO> getContratYoufaBaadTroisJoirs(@NonNull HttpServletRequest request){

        List<RentalContract> rentalContractList= rentalContractRepository.findAll();
        LocalDate date = LocalDate.now();
        List<RentalContract> rentalContractsEndDate=new ArrayList<>();

        for (RentalContract r:rentalContractList){
            LocalDate avantTroisJoursDateFin =r.getEnddate().minusDays(3);
            User user = userService.getUserByToken(request);
            if (r.getUserRentalcontract().getUserid()==user.getUserid()){
        if (
                (avantTroisJoursDateFin != null && date.isEqual(avantTroisJoursDateFin)) ||
                        (avantTroisJoursDateFin != null&&date.isAfter(avantTroisJoursDateFin)&& date.isBefore(r.getEnddate()))
        ){
            rentalContractsEndDate.add(r);
        }
    }
}
        return(rentalContractsEndDate.stream()
                .map((rentalContract) -> mapToDTO(rentalContract, new RentalContractDTO()))
                .collect(Collectors.toList()));
    }

    @Autowired
    private Stripe stripe;

    @Override
    public Charge createCharge(String token, int amount, String currency, int idcontract) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", currency);
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        RentalContract rentalContract = rentalContractRepository.findById(idcontract).get();
        int id = rentalContract.getContractid();
        String lastName = rentalContract.getRentalofferRentalContract().getUser().getLastname();
        PaymentRentalContrat p = new PaymentRentalContrat();
        String secretKey = "sk_test_51MfQE5A4XVwRTZvQpqlDgKCgtQbHkbsjgE3Kqud02mPHSBozFeSSKxdDnhFfNOjKpc9sKiFy95lW7pGShrU6eLzM00rTq1fqev";
        p.setAmount(charge.getAmount());
        p.setCardNumber(secretKey);
        p.setCurrency(charge.getCurrency());
        p.setCardholderName(lastName);
        p.setCvc("test");

        rentalContract.setPayment(p);

        p.setRentalContract(rentalContract);
        rentalContractRepository.save(rentalContract);
        return charge;
    }
}
