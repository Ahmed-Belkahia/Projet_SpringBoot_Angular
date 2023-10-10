package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.Relocation;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.model.RelocationDTO;
import com.example.techmasterpi.repos.FurnitureRepository;
import com.example.techmasterpi.repos.RelocationRepository;
import com.example.techmasterpi.repos.UserRepository;
import com.example.techmasterpi.util.NotFoundException;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RelocationService implements IFurnitureService{
    private RelocationRepository relocationRepository;
    private UserRepository userRepository;
    private final FurnitureRepository furnitureRepository;
    private  GeoApiContext geoApiContext;
    @Autowired
    private EntityManager entityManager;



    public RelocationService( RelocationRepository relocationRepository,
                              UserRepository userRepository,
                              FurnitureRepository furnitureRepository) {
        this.relocationRepository = relocationRepository;
        this.userRepository = userRepository;
        this.furnitureRepository = furnitureRepository;
    }
   /* public Long calculedistance() throws IOException, InterruptedException, ApiException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCIiPyo23fGm_BBdxwzJPs1Hy2_bCut8no")
                .build();

// Créer un objet DistanceMatrixApiRequest pour effectuer une demande à l'API Distance Matrix
        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);

// Ajouter le lieu de départ et le lieu d'arrivée à la demande
        DistanceMatrix trix = req.origins("Paris, France")
                .destinations("Marseille, France")
                .await();

// Récupérer la distance entre les deux lieux à partir de l'objet DistanceMatrix
        DistanceMatrixRow[] rows = trix.rows;
        DistanceMatrixElement[] elements = rows[0].elements;
        long distance = elements[0].distance.inMeters;

// Afficher la distance calculée
        System.out.println("Distance : " + distance + " mètres");
        return distance;
    }*/

    public List<RelocationDTO> findAll() {
         List<Relocation> relocations = relocationRepository.findAll(Sort.by("relocationid"));
        return relocations.stream()
                .map((relocation) -> mapToDTO(relocation, new RelocationDTO()))
                .collect(Collectors.toList());
    }
    public List<RelocationDTO> dynamicSearch(Map<String, Object> searchCriteria) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Relocation> query = builder.createQuery(Relocation.class);
            Root<Relocation> root = query.from(Relocation.class);

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
                TypedQuery<Relocation> typedQuery = entityManager.createQuery(query);
                return typedQuery.getResultList().stream().map((relocation -> mapToDTO(relocation, new RelocationDTO()))).collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();

            return Collections.emptyList();
        }
    }

    public double calculerdistance(String pays1,String pays2) throws IOException {


        double distance = getDistance(pays1, pays2);
        System.out.println("La distance entre " + pays1 + " et " + pays2 + " est de " + distance + " km.");

        return distance;
    }

    public static double getDistance(String location1, String location2) throws IOException {
        GeoPoint geoPoint1 = getGeoPoint(location1);
        GeoPoint geoPoint2 = getGeoPoint(location2);

        return GeoDistance.ARC.calculate(geoPoint1.getLat(), geoPoint1.getLon(), geoPoint2.getLat(), geoPoint2.getLon(),
                DistanceUnit.KILOMETERS);
    }
    private static GeoPoint getGeoPoint(String location) throws IOException {
        String url = "https://nominatim.openstreetmap.org/search?q=" + location.replaceAll("\\s", "+") + "&format=json&addressdetails=1&limit=1";
        String json = Jsoup.connect(url).ignoreContentType(true).execute().body();
        JSONArray results = new JSONArray(json);
        if (results.length() == 0) {
            throw new RuntimeException("Aucun résultat pour l'adresse " + location);
        }
        JSONObject result = results.getJSONObject(0);
        String lat = result.getString("lat");
        String lon = result.getString("lon");
        return new GeoPoint(Double.parseDouble(lat), Double.parseDouble(lon));
    }



    public List<RelocationDTO> findRelocation(int idUser){
        List<Relocation> relocations = relocationRepository.findRelocationByUserRelocation(idUser);

        return relocations.stream().map((relocation -> mapToDTO(relocation,new RelocationDTO()))).collect(Collectors.toList());
    }
    public List<RelocationDTO> findRelocationbyetat(String s){
        List<Relocation> relocations = relocationRepository.findRelocationParEtat(s);
        return relocations.stream().map((relocation -> mapToDTO(relocation,new RelocationDTO()))).collect(Collectors.toList());
    }
    public List<RelocationDTO> findRelocationbyetatAndUser(String s,int iduer){
        List<Relocation> relocations = relocationRepository.findRelocationParEtatForUser(s,iduer);
        return relocations.stream().map((relocation -> mapToDTO(relocation,new RelocationDTO()))).collect(Collectors.toList());
    }
    public List<RelocationDTO> findRelocationbyDtaeAndUser(Date date, int iduer){
        List<Relocation> relocations = relocationRepository.findRelocationParDateForUser(date , iduer);
        return relocations.stream().map((relocation -> mapToDTO(relocation,new RelocationDTO()))).collect(Collectors.toList());
    }
    public List<RelocationDTO> findRelocationbyDate( int iduer){
        List<Relocation> relocations = relocationRepository.findRelocationByLocalDate(iduer);
        return relocations.stream().map((relocation -> mapToDTO(relocation,new RelocationDTO()))).collect(Collectors.toList());
    }

    public RelocationDTO get(Integer relocationid) {
        return relocationRepository.findById(relocationid)
                .map(relocation -> mapToDTO(relocation, new RelocationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(RelocationDTO relocationDTO, int userid) {
    User u =userRepository.findById(userid).orElse(new User());
         Relocation relocation = new Relocation();
        mapToEntity(relocationDTO, relocation);
        relocation.setUserRelocation(u);
        return relocationRepository.save(relocation).getRelocationid();
    }

    public void update(Integer relocationid, RelocationDTO relocationDTO) {
         Relocation relocation = relocationRepository.findById(relocationid)
                .orElseThrow(NotFoundException::new);
        mapToEntity(relocationDTO, relocation);
        relocationRepository.save(relocation);
    }

    public void delete(final Integer relocationid) {
        relocationRepository.deleteById(relocationid);
    }




    private RelocationDTO mapToDTO( Relocation relocation,  RelocationDTO relocationDTO) {
        relocationDTO.setRelocationid(relocation.getRelocationid());
        relocationDTO.setRelocationdate(relocation.getRelocationdate());
        relocationDTO.setLocationdep(relocation.getLocationdep());
        relocationDTO.setLocationarr(relocation.getLocationarr());
        relocationDTO.setRelocationState(relocation.getRelocationState());
        relocationDTO.setUserRelocation(relocation.getUserRelocation() == null ? null : relocation.getUserRelocation().getUserid());
        return relocationDTO;
    }

    private Relocation mapToEntity(RelocationDTO relocationDTO, Relocation relocation) {
        relocation.setRelocationdate(relocationDTO.getRelocationdate());
        relocation.setLocationdep(relocationDTO.getLocationdep());
        relocation.setLocationarr(relocationDTO.getLocationarr());
        final User userRelocation = relocationDTO.getUserRelocation() == null ? null : userRepository.findById(relocationDTO.getUserRelocation())
                .orElseThrow(() -> new NotFoundException("userRelocation not found"));
        relocation.setUserRelocation(userRelocation);
        return relocation;
    }
    public GeocodingResult[] geocode(String address) throws Exception {
        return GeocodingApi.geocode(geoApiContext, address).await();
    }

}
