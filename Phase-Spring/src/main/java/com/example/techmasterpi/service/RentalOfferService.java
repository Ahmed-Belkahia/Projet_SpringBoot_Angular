package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.RentalContract;
import com.example.techmasterpi.domain.RentalOffer;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.model.RentalOfferDTO;
import com.example.techmasterpi.repos.RentalContractRepository;
import com.example.techmasterpi.repos.RentalOfferRepository;
import com.example.techmasterpi.repos.UserRepository;
import com.example.techmasterpi.util.NotFoundException;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RentalOfferService implements IRentalOffer {

    @Autowired
    RentalOfferRepository rentalOfferRepository;
@Autowired
    UserRepository userRepository;
    @Autowired
    private RentalContractRepository rentalContractRepository;
/*
    @Override
    public List<String> scrapping(double price1, double price2) {
        final String url =
                "http://www.tunisie-annonce.com/AnnoncesImmobilier.asp?rech_cod_rub=101&rech_cod_typ=10101";
        String resultat="";
        List<String> resultats=new ArrayList<>();
        Double priceDouble=0.0;
        List<Double> priceList=new ArrayList<>();
        try {
            final Document document = Jsoup.connect(url).get();

            Element table = document.select("table").get(6);

            Elements rows = table.select("tr.Tableau1");

            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements cols = row.select("td");
                String price = cols.get(7).text().replaceAll("\\s+", "");
                priceDouble=Double.parseDouble(price);
                String description = cols.get(5).text();
                if (price1<price2){
                    if (priceDouble>price1&&priceDouble<price2){
                        resultat="prix de location mensuel de la maison:******"+" "+description+" "+ "est :*****" + " " + price;
                        resultats.add(resultat);
                    }}


            }
            System.out.println(resultats);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultats;
    }
*/
    @Override
    public List<RentalOfferDTO> getOffersByRangePrice(double price1, double price2) {
        List<RentalOffer> rentalOfferList=new ArrayList<>();

        if (price1<price2){
           rentalOfferList= rentalOfferRepository.findRentalOfferByRangePrice(price1,price2);

        }
        return rentalOfferList.stream()
                .map((rentalOffer) -> mapToDTO(rentalOffer, new RentalOfferDTO()))
                .collect(Collectors.toList());    }

    @Override
    public List<RentalOfferDTO> findTopNByOrderByOffredateDesc(Integer n) {
        List<RentalOffer> result = new ArrayList<>();
       List<RentalOffer>offers= rentalOfferRepository.findAll();
        offers.stream()
                .sorted(Comparator.comparing(RentalOffer::getOffredate).reversed())
                .limit(n)
                .forEach(result::add);

        return result.stream()
                .map((rentalOffer) -> mapToDTO(rentalOffer, new RentalOfferDTO()))
                .collect(Collectors.toList());
    }
    @Override
    public List<RentalOfferDTO> findAllOrderByPriceDesc(){
        final List<RentalOffer> rentalOffers = rentalOfferRepository.findAllOrderByPriceDESC();
        return rentalOffers.stream()
                .map((rentalOffer) -> mapToDTO(rentalOffer, new RentalOfferDTO()))
                .collect(Collectors.toList());
    }
    @Override
    public int getNumberOffreByUser(int idUser){
        List<RentalOffer>rentalOfferList=rentalOfferRepository.findRentalOfferByUser(idUser);
        int        numberOfferByUser=0;
        for (RentalOffer rentalOffer:rentalOfferList){
            numberOfferByUser+=1;
        }
        return numberOfferByUser;
    }
    @Override
    public List<RentalOfferDTO> getAvailableOffers(LocalDate startdate,LocalDate enddate){
        List<RentalOffer>rentalOfferListAvailable=new ArrayList<>();
        List<RentalContract>rentalContractList=rentalContractRepository.getActiveRentalContractByDates(startdate,enddate);
        List<RentalOffer> list = rentalOfferRepository.findAll().stream().filter(o ->
                !rentalContractList.stream().anyMatch(c -> o.getRentalofferRentalContractRentalContracts().contains(c))).collect(Collectors.toList());


        return list.stream()
                .map((rentalOffer) -> mapToDTO(rentalOffer, new RentalOfferDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalOfferDTO> findRentalOfferByTitle(String title) {
        final List<RentalOffer> rentalOffers = rentalOfferRepository.findByRentalOfferByTitle(title);
        return rentalOffers.stream()
                .map((rentalOffer) -> mapToDTO(rentalOffer, new RentalOfferDTO()))
                .collect(Collectors.toList());    }

    @Autowired
    UserService userService;
    @Override
    public int addRentalOffer(RentalOffer rentalOffer, @NotNull HttpServletRequest request) {
       User user = userService.getUserByToken(request);        // User user=userRepository.findById(idUser).get();
        rentalOffer.setUser(user);
        return rentalOfferRepository.save(rentalOffer).getOffreid();
    }

    @Override
    public boolean updateRentalOffer(RentalOffer rentalOffer) {

        if(rentalOfferRepository.existsById(rentalOffer.getOffreid())){
            rentalOfferRepository.save(rentalOffer).equals(rentalOffer);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean deleteRentalOffer(int id) {
        if(rentalOfferRepository.existsById(id)){
            rentalOfferRepository.deleteById(id);
            return true;
        }else{
            return  false;
        }
    }

   // @Override
    //public RentalOffer getById(int id) {
     //   return rentalOfferRepository.findById(id).get();
    //}

   @Override
    public RentalOfferDTO getById(final Integer offreid) {

        return rentalOfferRepository.findById(offreid)
                .map(rentalOffer -> mapToDTO(rentalOffer, new RentalOfferDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public List<String> disponibility(int offerid){
RentalOffer rentalOffer=rentalOfferRepository.findById(offerid).get();
String rentalOfferTitle=rentalOffer.getTitle();
int rentalOfferId=rentalOffer.getOffreid();
        String informationDisponibilite;
Set<RentalContract> rentalContractList=rentalOffer.getRentalofferRentalContractRentalContracts();
List<String> listDisponibilite=new ArrayList<>();
List<String> dateLocations;
for (RentalContract rentalContract:rentalContractList){
   LocalDate startDate= rentalContract.getStartdate();
    LocalDate endDate= rentalContract.getEnddate();
    informationDisponibilite="L'offre:"+rentalOfferTitle+" "+"de numéro :"+rentalOfferId +" "+"est loué de :"+startDate +" à "+endDate;
    listDisponibilite.add(informationDisponibilite);

}
        return listDisponibilite;
    }

    //@Override
    //public List<RentalOffer> getAll() {
     //   return rentalOfferRepository.findAll();
    //}
@Override
    public List<RentalOfferDTO> getAll() {
        final List<RentalOffer> rentalOffers = rentalOfferRepository.findAll(Sort.by("offreid"));
        return rentalOffers.stream()
                .map((rentalOffer) -> mapToDTO(rentalOffer, new RentalOfferDTO()))
                .collect(Collectors.toList());
    }
    @Override
    public List<RentalOfferDTO> findAllOrderByPriceAsc() {
        final List<RentalOffer> rentalOffers = rentalOfferRepository.findAllOrderByPriceAsc();
        return rentalOffers.stream()
                .map((rentalOffer) -> mapToDTO(rentalOffer, new RentalOfferDTO()))
                .collect(Collectors.toList());

    }
    private RentalOfferDTO mapToDTO(final RentalOffer rentalOffer,
                                    final RentalOfferDTO rentalOfferDTO) {
        rentalOfferDTO.setOffreid(rentalOffer.getOffreid());
        rentalOfferDTO.setTitle(rentalOffer.getTitle());
        rentalOfferDTO.setDescription(rentalOffer.getDescription());
        rentalOfferDTO.setAdress(rentalOffer.getAdress());
        rentalOfferDTO.setOffredate(rentalOffer.getOffredate());
        rentalOfferDTO.setPicture(rentalOffer.getPicture());
        rentalOfferDTO.setMonthlyrent(rentalOffer.getMonthlyrent());
        rentalOfferDTO.setTyperentalloffer(rentalOffer.getTyperentalloffer());
        return rentalOfferDTO;
    }
    @Override
    public double getChiffreAffaireByOffer(int offerId) {
       RentalOffer rentalOffer= rentalOfferRepository.findById(offerId).get();
        Set<RentalContract> rentalContractList=rentalOffer.getRentalofferRentalContractRentalContracts();
double revenueOffer=0.0;
        for (RentalContract rentalContract:rentalContractList){
            revenueOffer+=  rentalOffer.getMonthlyrent()*rentalContract.getNbmonth();
        }
        return revenueOffer;
    }




    public static String saveImage(MultipartFile image, RentalOffer rentalOffer) throws IOException {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        Path path = Paths.get("uploads");
        Files.createDirectories(path);
        try (InputStream inputStream = image.getInputStream()) {
            Path filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            rentalOffer.setPicture(filePath.toString());
            return filePath.toString();
        } catch (IOException e) {
            throw new IOException("Could not save file " + fileName, e);
        }
    }

}
