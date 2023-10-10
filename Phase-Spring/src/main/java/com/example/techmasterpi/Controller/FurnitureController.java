package com.example.techmasterpi.Controller;

import com.example.techmasterpi.domain.Furniture;
import com.example.techmasterpi.model.FurnitureDTO;
import com.example.techmasterpi.service.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping(value = "/api/furnitures", produces = MediaType.APPLICATION_JSON_VALUE)
public class FurnitureController {
    @Autowired

    private  FurnitureService furnitureService;


    public FurnitureController(FurnitureService furnitureService) {
        this.furnitureService = furnitureService;
    }

    @GetMapping
    public ResponseEntity<List<FurnitureDTO>> getAllFurnitures() {
        return ResponseEntity.ok(furnitureService.findAll());
    }
    @PostMapping(value="/addfurniture/{relocationid}")
    @ResponseBody
    public int addfurniture(@RequestBody FurnitureDTO furniture , @PathVariable("relocationid") int relocationid){
        return furnitureService.create(furniture,relocationid);
    }
    @PostMapping("/{relocationid}")
    @ResponseBody

    public int createFurniture(@RequestPart @Valid  FurnitureDTO furnitureDTO,@RequestPart("image") MultipartFile image,
                               @PathVariable(name ="relocationid")  Integer relocationid) throws IOException {


        if (image != null && !image.isEmpty()) {
            String imagePath = furnitureService.saveImage(image,furnitureDTO);
            furnitureDTO.setPicture(imagePath);
        }

        return furnitureService.create(furnitureDTO,relocationid);
    }
    @GetMapping("/{furnitureid}")
    public ResponseEntity<FurnitureDTO> getFurniture(
            @PathVariable(name = "furnitureid")  Integer furnitureid) {
        return ResponseEntity.ok(furnitureService.get(furnitureid));
    }
    @PutMapping("/{furnitureid}")
    public ResponseEntity<Void> updateFurniture(
            @PathVariable(name = "furnitureid")  Integer furnitureid,
            @RequestBody @Valid  FurnitureDTO furnitureDTO) {
        furnitureService.update(furnitureid, furnitureDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{furnitureid}")
    public ResponseEntity<Void> deleteFurniture(
            @PathVariable(name = "furnitureid")  Integer furnitureid) {
        furnitureService.delete(furnitureid);
        return ResponseEntity.noContent().build();
    }
}



