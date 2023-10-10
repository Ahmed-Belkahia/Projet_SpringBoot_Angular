package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.*;
import com.example.techmasterpi.model.DeliveryDTO;
import com.example.techmasterpi.repos.*;
import com.example.techmasterpi.util.NotFoundException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional
public class DeliveryService implements  IDeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository ;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RelocationRepository relocationRepository;
    @Autowired
    private TaxRepository taxRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private RelocationService relocationService;
    @Autowired
    private UserService userService;
    private static final String ACCOUNT_SID = "ACb367373300879130a16021c5b875eff1";
    private static final String AUTH_TOKEN = "be25cc3b2cb5c3581dbc563c71d8be8f";

    // Votre numéro de téléphone Twilio
    private static final String TWILIO_PHONE_NUMBER = "+12708195535";
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Configuration freemarkerConfig;


    public DeliveryService(DeliveryRepository deliveryRepository,UserRepository userRepository,RelocationRepository relocationRepository){
    this.deliveryRepository = deliveryRepository;
    this.userRepository = userRepository;
    this.relocationRepository = relocationRepository;

}

    public Charge createCharge(String token,  String currency,int idDelevry) throws Exception {
        Delivery D=deliveryRepository.findById(idDelevry).orElse(new Delivery());
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", D.getTotaleprice());
        chargeParams.put("currency", currency);
        chargeParams.put("source", token);


        Charge charge = Charge.create(chargeParams);

        Payment facture = new Payment();
       // String secretKey = "sk_test_51MfQE5A4XVwRTZvQpqlDgKCgtQbHkbsjgE3Kqud02mPHSBozFeSSKxdDnhFfNOjKpc9sKiFy95lW7pGShrU6eLzM00rTq1fqev";
        facture.setAmount(charge.getAmount());
        facture.setCardNumber(charge.getPaymentMethodDetails().getCard().getLast4());
        facture.setCurrency(charge.getCurrency());
        facture.setCardholderName(D.getRelocationDelivery().getUserRelocation().getFirstname());
        facture.setCvc("test");
        facture.setDatepaiement(LocalDate.now());
        facture.setTypePayment("Delivery");
        D.setPaymentDel(facture);
        D.setState("Payed");
        D.setStateDeleviry("on the way");
        facture.setDeliveryPayment(D);
        deliveryRepository.save(D);





        return charge;
    }
    public void sendEmail(String to, String subject, String html) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);
        mailSender.send(message);
    }
    public String bestMonthDelivery() {
        List<User> users = userRepository.findAll();
        List<Long> ca = new ArrayList<>();
        Long cau = 0L;
        List<String> bestUserNames = new ArrayList<>();
        for (User u : users) {
            ca.add(calculechifferdaffaire(u.getUserid()));
        }
        for (int i = 0; i < ca.size(); i++) {
            if (ca.get(i) > cau) {
                cau = ca.get(i);
                bestUserNames.clear();
                bestUserNames.add(users.get(i).getFirstname()+" est son chiifre d'affaire est "+cau);
            } else if (ca.get(i).equals(cau)) {
                bestUserNames.add(users.get(i).getFirstname()+" est son chiifre d'affaire est "+cau);
            }
        }
        if (bestUserNames.isEmpty()) {
            return "Aucun utilisateur trouvé";
        } else if (bestUserNames.size() == 1) {
            return "Notre meilleur agent est " + bestUserNames.get(0);
        } else {
            String names = String.join(", ", bestUserNames);
            return "Nos meilleurs agents sont " + names;
        }
    }

    public Long calculechifferdaffaire(int id){
        List<Delivery> delivery = deliveryRepository.findAll();
        Long somme=0L;
        for (Delivery d:delivery){
            if((d.getUserDelevery().getUserid()==id) && (d.getState().equals("Payed"))){
                somme += d.getTotaleprice();
            }

        }

        return somme;
    }
   @Scheduled(cron = "0 0 0 1 * *")
    public void calculeTax() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            boolean hasTax = false;
            long tax1 = (long) (calculechifferdaffaire(user.getUserid())*0.01);

            Tax tax = user.getUsertax();

            if (tax != null ) {
                hasTax = true;

                Long tt =tax.getPricetax() +tax1;
                tax.setPricetax(tax1);
                tax.setTotaletax(tt);
                tax.setStatustax("NOT PAYED");
                taxRepository.updateTaxToNull(tax.getTaxid());
                taxRepository.save(tax);
            }

            if (!hasTax) {
                Tax newTax = new Tax();
                newTax.setTaxuser(user);
                newTax.setPricetax(tax1);
                newTax.setTotaletax(0L);
                Long tt = newTax.getTotaletax() + tax1;
                newTax.setTotaletax(tt);
                newTax.setStatustax("NOT PAYED");
                taxRepository.save(newTax);
                user.setUsertax(newTax);
                userRepository.save(user);
            }
        }
    }



    public List<DeliveryDTO> findAll() {
        final List<Delivery> deliverys = deliveryRepository.findAll(Sort.by("deliveryid"));

        return deliverys.stream()
                .map((delivery) -> mapToDTO(delivery, new DeliveryDTO()))
                .collect(Collectors.toList());
    }

    public Long calculefrais(Delivery delivery) throws IOException {

        double fraisdaistance= relocationService.calculerdistance(delivery.getRelocationDelivery().getLocationdep(),delivery.getRelocationDelivery().getLocationarr())*0.5;
        double fraisPoids = delivery.getRelocationDelivery().getRelocationFourtnitureFurnitures()
                .stream()
                .mapToDouble(furniture -> furniture.getFurnitureweight())
                .sum() * 0.4;
        double taxfiscale = (fraisdaistance+fraisdaistance)*0.1;
        Long total = (long) (fraisPoids+fraisdaistance+taxfiscale)*10;
        return total;
    }
    public User finbBestRelocateur(){
            List<Object[]> result = userRepository.findUserByBestDelivery();
            User bestUser = null;
            Long bestCount = 0L;
            for (Object[] tuple : result) {
                User user = (User) tuple[0];
                Long count = (Long) tuple[1];
                if (count > bestCount) {
                    bestUser = user;
                    bestCount = count;
                }
            }
            return bestUser;
        }

    public byte[] genererFacturePDF(Payment facture) throws Exception {
        // Convertir le contenu HTML en PDF en utilisant Flying Saucer
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(genererContenuHTML(facture));
        renderer.layout();

        // Créer un flux de sortie pour le contenu PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Générer le PDF et écrire le contenu dans le flux de sortie
        renderer.createPDF(outputStream);
        Path fichierFacture = Paths.get("C:\\Users\\yassi\\OneDrive\\Desktop\\"+facture.getId()+".pdf");
        Files.write(fichierFacture, outputStream.toByteArray());


        return outputStream.toByteArray();
    }

    private String genererContenuHTML(Payment facture) throws Exception {
        // Configuration de Freemarker
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
        freemarkerConfig.setDefaultEncoding("UTF-8");

        // Préparer les données pour le template Freemarker
        Map<String, Object> model = new HashMap<>();
        model.put("facture", facture);

        // Charger le template Freemarker
        Template template = freemarkerConfig.getTemplate("facture.html");

        // Générer le contenu HTML de la facture en utilisant le template Freemarker
        StringWriter writer = new StringWriter();
        template.process(model, writer);
        String contenuHTML = writer.toString();

        return contenuHTML;
    }



    public DeliveryDTO get(Integer deliveryid) {
        return deliveryRepository.findById(deliveryid)
                .map(delivery -> mapToDTO(delivery, new DeliveryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(DeliveryDTO deliveryDTO, @NonNull HttpServletRequest request, int idRelocation) throws MessagingException, IOException {

        final Delivery delivery = new Delivery();
        mapToEntity(deliveryDTO, delivery);
        User u = userService.getUserByToken(request);
      if(taxRepository.findByTaxuser_Userid(u.getUserid()).getStatustax().equals("Payed")){
        delivery.setState("NOT PAYED");
       // User u = userRepository.findById(idUser).orElse(new User());

        Relocation r = relocationRepository.findById(idRelocation).orElse(new Relocation());
        delivery.setStateDeleviry("Waitting for pay");
        delivery.setUserDelevery(u);
        delivery.setRelocationDelivery(r);
        deliveryRepository.save(delivery);
           delivery.setTotaleprice(calculefrais(delivery));
           deliveryRepository.save(delivery);
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Notification of your Offer</title>\n" +
                "    <style>\n" +
                "      /* Styles pour le corps de l'e-mail */\n" +
                "      body {\n" +
                "        font-family: Arial, sans-serif;\n" +
                "        font-size: 16px;\n" +
                "        color: #333;\n" +
                "      }\n" +
                "      /* Styles pour les en-têtes */\n" +
                "      h1, h2, h3 {\n" +
                "        color: #555;\n" +
                "      }\n" +
                "      /* Styles pour les boutons */\n" +
                "      .button {\n" +
                "        display: inline-block;\n" +
                "        background-color: #008CBA;\n" +
                "        color: #fff;\n" +
                "        padding: 10px 20px;\n" +
                "        border-radius: 5px;\n" +
                "        text-decoration: none;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <h1>Notification of your Offer</h1>\n" +
                "    <p>Mr/Ms. " + r.getUserRelocation().getFirstname() + ",</p>\n" +
                "    <p>We inform you that your request has been processed and we have found an offer for your relocation</p>\n" +
                "    <p>Here is a summary of your offer:</p>\n" +
                "    <ul>\n" +
                "      <li>Company Name :"+ delivery.getUserDelevery().getCompanyname()+"</li>\n" +
                "      <li>Phone number : "+delivery.getUserDelevery().getPhone()+"</li>\n" +
                "      <li>Track :"+ delivery.getTrack()+"</li>\n" +
                "      <li>Service :"+ delivery.getServicedetail()+"</li>\n" +
                "    </ul>\n" +
                "    <p>The total amount of your order is"+ delivery.getTotaleprice()+" €.</p>\n" +
                "    <p>If you have any questions or concerns, please don't hesitate to contact us.</p>\n" +
                "    <p>Thank you for your confidence.</p>\n" +
                "    <p>Cordially,</p>\n" +
                "    <h2>TECHMASTER</h2>\n" +
                "    <p><a href=\"[lien vers votre site web]\" class=\"button\">Check your offer</a></p>\n" +
                "  </body>\n" +
                "</html>\n";

        sendEmail(delivery.getRelocationDelivery().getUserRelocation().getEmail(),"proposal for a contract",html);
       // sendSMS("+21654480469","hello");
        return delivery.getDeliveryid();}
      else {System.out.println("you have to pay your tax");
       return 0;}

    }

    public void update(Integer deliveryid, DeliveryDTO deliveryDTO) {
        final Delivery delivery = deliveryRepository.findById(deliveryid)
                .orElseThrow(NotFoundException::new);
        mapToEntity(deliveryDTO, delivery);

           // sendSMS("+21655105372","your package has arrived.");

        deliveryRepository.save(delivery);
    }
    public void sendSMS(String toPhoneNumber, String messageBody){
        Twilio.init("ACc2294319aa2eaba8e91273055538a50e", "3d8aaf138dd120e037c4c12ae42a6ccf");
        Message message = Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber("+18654137235"), messageBody).create();
        System.out.println("SMS envoyé avec succès avec l'ID : " + message.getSid());
    }

    public void delete( Integer deliveryid) {
        deliveryRepository.deleteById(deliveryid);
    }

    public Charge createChargeTax(String token, String currency,int id) throws StripeException {

        //User u=userService.getUserByToken(request);
        Tax tax=taxRepository.findById(id).orElse(new Tax());
        Map<String, Object> chargeParams = new HashMap<>();


        chargeParams.put("amount", tax.getPricetax());
        chargeParams.put("currency", currency);
        chargeParams.put("source", token);

        Charge charge = Charge.create(chargeParams);

        Payment p = new Payment();
        String secretKey = "sk_test_51MfQE5A4XVwRTZvQpqlDgKCgtQbHkbsjgE3Kqud02mPHSBozFeSSKxdDnhFfNOjKpc9sKiFy95lW7pGShrU6eLzM00rTq1fqev";
        p.setAmount(charge.getAmount());
        p.setCurrency(charge.getCurrency());
        p.setCardholderName(tax.getTaxuser().getFirstname());
        p.setCardNumber(charge.getPaymentMethodDetails().getCard().getLast4());
        //p.setCvc(charge.getPaymentMethodDetails().getCard().getChecks().getCvcCheck());
        p.setCvc("null");
        p.setTypePayment("Tax");
        p.setDatepaiement(LocalDate.now());
        //p.getDeliveryPayment().getUserDelevery().getEmail();
        tax.setPayment(p);
        tax.setStatustax("Payed");
        p.setTax(tax);
        taxRepository.save(tax);
        return charge;
    }

    private DeliveryDTO mapToDTO(Delivery delivery, DeliveryDTO deliveryDTO) {
        deliveryDTO.setDeliveryid(delivery.getDeliveryid());
        deliveryDTO.setTotaleprice(delivery.getTotaleprice());
        deliveryDTO.setServicedetail(delivery.getServicedetail());
        deliveryDTO.setTrack(delivery.getTrack());
        deliveryDTO.setState(delivery.getState());
        deliveryDTO.setStateDeleviry(delivery.getStateDeleviry());
        deliveryDTO.setUserDelevery(delivery.getUserDelevery() == null ? null : delivery.getUserDelevery().getUserid());
        deliveryDTO.setRelocationDelivery(delivery.getRelocationDelivery() == null ? null : delivery.getRelocationDelivery().getRelocationid());
        return deliveryDTO;
    }


    public Delivery mapToEntity(DeliveryDTO deliveryDTO,  Delivery delivery) {
        delivery.setTotaleprice(deliveryDTO.getTotaleprice());
        delivery.setServicedetail(deliveryDTO.getServicedetail());
        delivery.setTrack(deliveryDTO.getTrack());
        delivery.setState(deliveryDTO.getState());
        delivery.setStateDeleviry(deliveryDTO.getStateDeleviry());
        final User userDelevery = deliveryDTO.getUserDelevery() == null ? null : userRepository.findById(deliveryDTO.getUserDelevery())
                .orElseThrow(() -> new NotFoundException("userDelevery not found"));
        delivery.setUserDelevery(userDelevery);

        final Relocation relocationDelivery = deliveryDTO.getRelocationDelivery() == null ? null : relocationRepository.findById(deliveryDTO.getRelocationDelivery())
                .orElseThrow(() -> new NotFoundException("relocationDelivery not found"));
        delivery.setRelocationDelivery(relocationDelivery);

        final Payment paymentDel = deliveryDTO.getPaymentDel() == null ? null : paymentRepository.findById(deliveryDTO.getPaymentDel())
                .orElseThrow(() -> new NotFoundException("PaiementDelevery not found"));
        delivery.setPaymentDel(paymentDel);
        return delivery;
    }

}
