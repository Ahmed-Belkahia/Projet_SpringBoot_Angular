package com.example.techmasterpi.Controller;



import com.example.techmasterpi.domain.Relocation;
import com.example.techmasterpi.model.RelocationDTO;
import com.example.techmasterpi.service.RelocationService;

import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.Date;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping(value = "/api/relocations", produces = MediaType.APPLICATION_JSON_VALUE)
public class RelocationController {

    @Autowired
    private RelocationService relocationService;

    public RelocationController(RelocationService relocationService) {
        this.relocationService = relocationService;
    }

    @GetMapping
    public ResponseEntity<List<RelocationDTO>> getAllRelocations() {
        return ResponseEntity.ok(relocationService.findAll());
    }



    @GetMapping("/{relocationid}")
    public ResponseEntity<RelocationDTO> getRelocation(
            @PathVariable(name = "relocationid")  Integer relocationid) throws IOException, InterruptedException, ApiException {
        relocationService.calculerdistance(relocationService.get(relocationid).getLocationdep(),relocationService.get(relocationid).getLocationarr());
        return ResponseEntity.ok(relocationService.get(relocationid));
    }
    @GetMapping("/findrelocationbyuser/{userid}")
    public ResponseEntity<List<RelocationDTO>> getRelocationByUser(
            @PathVariable(name = "userid")  Integer userid){
        return ResponseEntity.ok(relocationService.findRelocation(userid));
    }
    @GetMapping("/findrelocationbyStatus")
    public ResponseEntity<List<RelocationDTO>> getRelocationByStatus(
            @PathParam("etat")  String etat){
        return ResponseEntity.ok(relocationService.findRelocationbyetat(etat));
    }
    @GetMapping("/findrelocationbyStatusUser/{userid}")
    public ResponseEntity<List<RelocationDTO>> getRelocationByStatusAndUser(
            @PathVariable(name = "userid")  Integer userid ,
            @PathParam("etat")  String etat){
        return ResponseEntity.ok(relocationService.findRelocationbyetatAndUser(etat,userid));
    }
    @GetMapping("/findrelocationbydateUser/{userid}/{date}")
    public ResponseEntity<List<RelocationDTO>> getRelocationByDateAndUser(
            @PathVariable(name = "userid")  Integer userid ,
            @PathVariable(name="date") @DateTimeFormat(pattern = "yyyy-MM-dd")Date date){
        return ResponseEntity.ok(relocationService.findRelocationbyDtaeAndUser(date,userid));
    }
    @GetMapping("/findrelocationbydate/{userid}")
    public ResponseEntity<List<RelocationDTO>> getRelocationByDate(
            @PathVariable(name = "userid")  Integer userid){
        return ResponseEntity.ok(relocationService.findRelocationbyDate(userid));
    }


    @PostMapping("/{userid}")
    @ResponseBody
    public int createRelocation(
            @RequestBody @Valid  RelocationDTO relocationDTO,
            @PathVariable(name = "userid")  Integer userid) {
        return relocationService.create(relocationDTO,userid);
    }
    @GetMapping("/search")
    public ResponseEntity<List<RelocationDTO>> search(@RequestParam Map<String, Object> searchCriteria) {
        List<RelocationDTO> relocations = relocationService.dynamicSearch(searchCriteria);

        return ResponseEntity.ok(relocations);
    }

    @PutMapping("/{relocationid}")
    public ResponseEntity<Void> updateRelocation(
            @PathVariable(name = "relocationid")  Integer relocationid,
            @RequestBody @Valid final RelocationDTO relocationDTO) {
        relocationService.update(relocationid, relocationDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{relocationid}")
    public ResponseEntity<Void> deleteRelocation(
            @PathVariable(name = "relocationid")  Integer relocationid) {
        relocationService.delete(relocationid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/geocode")
    public ResponseEntity<GeocodingResult[]> geocode(@RequestParam("address") String address) {
        try {
            if (relocationService == null) {
                throw new RuntimeException("Geocoding service is null");
            }
            GeocodingResult[] results = relocationService.geocode(address);
            if (results == null) {
                throw new RuntimeException("Geocoding results are null");
            }
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
