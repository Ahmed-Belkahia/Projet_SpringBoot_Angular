package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.Payment;
import com.example.techmasterpi.domain.SellContract;
import com.example.techmasterpi.domain.SellerOffer;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.model.SellContractDTO;
import com.example.techmasterpi.model.StatutContrat;
import com.example.techmasterpi.repos.SellContractRepository;
import com.example.techmasterpi.repos.SellerOfferRepository;
import com.example.techmasterpi.repos.UserRepository;


import com.example.techmasterpi.util.NotFoundException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class    ContratService implements IContratService{
    @Autowired
    private SellContractRepository sellContractRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerOfferRepository sellerOfferRepository;
    @Autowired
    private Session session;



    @Override
    public void ajouterContrat(SellContract sellerContrat,int user_id,int sellid) throws MessagingException{
        User u=userRepository.findById(user_id).get();
        sellerContrat.setUserContractsale(u);
        SellerOffer v=sellerOfferRepository.findById(sellid).get();
        sellerContrat.setContractSell(v);
        sellerContrat.setPrice(v.getPrice());

        sellContractRepository.save(sellerContrat);
        //sendEmail(sellerContrat.getUserContractsale().getEmail(), "verification","Votre Contrat a ete creer ave succes");

    }

    @Override
    public void updateContrat(SellContract contract) {
        if (sellContractRepository.findById(contract.getContractsellid()).isPresent())
            sellContractRepository.save(contract);
        else
            System.out.println("doesnt exist");

    }

    @Override
    public void deleteContrat(int id) {
        sellContractRepository.deleteById(id);

    }
    public SellContractDTO get(final Integer contractsellid) {
        return sellContractRepository.findById(contractsellid)
                .map(sellContract -> mapToDTO(sellContract, new SellContractDTO()))
                .orElseThrow(NotFoundException::new);
    }

//    @Override
//    public SellContract get(int id) {
//        return sellContractRepository.findById(id).get();
//    }

//    @Override
//    public List<SellContract> findAllContrat() {
//        return sellContractRepository.findAll();
//    }
public List<SellContractDTO> findAll() {
    final List<SellContract> sellContracts = sellContractRepository.findAll(Sort.by("contractsellid"));
    return sellContracts.stream()
            .map((sellContract) -> mapToDTO(sellContract, new SellContractDTO()))
            .collect(Collectors.toList());
}

    public void sendEmail(String to, String subject, String text) throws MessagingException {
        Message message =new MimeMessage(session);
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(text);
        Transport.send(message);
    }
//    @PostConstruct
//    @Scheduled(cron = "0 0 0/12 * * *") // toutes les 12 heures
//    @Scheduled(fixedRate = 86400000) // 24 heures en millisecondes


    private SellContractDTO mapToDTO(final SellContract sellContract,
                                     final SellContractDTO sellContractDTO) {
        sellContractDTO.setContractsellid(sellContract.getContractsellid());
        sellContractDTO.setPrice(sellContract.getPrice());
        sellContractDTO.setSelldate(sellContract.getSelldate());
        sellContractDTO.setContractSell(sellContract.getContractSell() == null ? null : sellContract.getContractSell().getSellid());
        sellContractDTO.setUserContractsale(sellContract.getUserContractsale() == null ? null : sellContract.getUserContractsale().getUserid());
        return sellContractDTO;
    }

    private SellContract mapToEntity(final SellContractDTO sellContractDTO,
                                     final SellContract sellContract) {
        sellContract.setPrice(sellContractDTO.getPrice());
        sellContract.setSelldate(sellContractDTO.getSelldate());
        final SellerOffer contractSell = sellContractDTO.getContractSell() == null ? null : sellerOfferRepository.findById(sellContractDTO.getContractSell())
                .orElseThrow(() -> new NotFoundException("contractSell not found"));
        sellContract.setContractSell(contractSell);
        final User userContractsale = sellContractDTO.getUserContractsale() == null ? null : userRepository.findById(sellContractDTO.getUserContractsale())
                .orElseThrow(() -> new NotFoundException("userContractsale not found"));
        sellContract.setUserContractsale(userContractsale);
        return sellContract;
    }
    public Charge createCharge(String token, String currency, int id) throws Exception {
        Map<String, Object> chargeParams = new HashMap<>();
        SellContract s=sellContractRepository.findById(id).orElse(new SellContract());

        chargeParams.put("amount", Double.valueOf(s.getPrice()).intValue());
        chargeParams.put("currency", currency);
        chargeParams.put("source", token);

        Charge charge = Charge.create(chargeParams);

        Payment p = new Payment();
        String secretKey = "sk_test_51Mih7VJ1i63gmsmgBp4ueaHp6dijkl16T564UcdYJjn077zcKgjSU9iCSMVMAZ576RMkdIvPd4onxh1O3boRZZXc00woUyyMH4";
        p.setAmount(charge.getAmount());
        p.setCardNumber(secretKey);
        p.setCurrency(charge.getCurrency());
        p.setCardholderName("");
        p.setCvc("test");
        s.setPayment(p);
        p.setTypePayment("SELL");
        p.setDatepaiement(LocalDate.now());
        s.setCity("Payed");
        p.setSellContract(s);
        sellContractRepository.save(s);
        SellerOffer se= sellerOfferRepository.findById(s.getContractSell().getSellid()).orElse(new SellerOffer());
        se.setSold(true);
        sellerOfferRepository.save(se);
        return charge;
    }
}
