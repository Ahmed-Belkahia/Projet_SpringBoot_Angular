package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.Furniture;
import com.example.techmasterpi.domain.Relocation;
import com.example.techmasterpi.model.FurnitureDTO;
import com.example.techmasterpi.repos.FurnitureRepository;
import com.example.techmasterpi.repos.RelocationRepository;
import com.example.techmasterpi.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FurnitureService implements IFurnitureService{
    @Autowired
    private FurnitureRepository furnitureRepository;
    @Autowired
    private final RelocationRepository relocationRepository;

    public FurnitureService(RelocationRepository relocationRepository,FurnitureRepository furnitureRepository){
        this.relocationRepository = relocationRepository;
        this.furnitureRepository= furnitureRepository;
    }
    public List<FurnitureDTO> findAll() {
        final List<Furniture> furnitures = furnitureRepository.findAll(Sort.by("furnitureid"));
        return furnitures.stream()
                .map((furniture) -> mapToDTO(furniture, new FurnitureDTO()))
                .collect(Collectors.toList());
    }

    public FurnitureDTO get(Integer furnitureid) {
        return furnitureRepository.findById(furnitureid)
                .map(furniture -> mapToDTO(furniture, new FurnitureDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(FurnitureDTO furnitureDTO,int relocationid) {
        Relocation r = relocationRepository.findById(relocationid).get();
        Furniture furniture = new Furniture();
        mapToEntity(furnitureDTO, furniture);
        furniture.setRelocationFourtniture(r);
        return furnitureRepository.save(furniture).getFurnitureid();
    }

    public void update(Integer furnitureid, FurnitureDTO furnitureDTO) {
        final Furniture furniture = furnitureRepository.findById(furnitureid)
                .orElseThrow(NotFoundException::new);
        mapToEntity(furnitureDTO, furniture);
        furnitureRepository.save(furniture);
    }

    public void delete(final Integer furnitureid) {
        furnitureRepository.deleteById(furnitureid);
    }

    public static String saveImage(MultipartFile image,  FurnitureDTO furniture) throws IOException {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        Path path = Paths.get("uploads");
        Files.createDirectories(path);
        try (InputStream inputStream = image.getInputStream()) {
            Path filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            furniture.setPicture(filePath.toString());
            return filePath.toString();
        } catch (IOException e) {
            throw new IOException("Could not save file " + fileName, e);
        }

    }
    private FurnitureDTO mapToDTO(Furniture furniture, FurnitureDTO furnitureDTO) {
        furnitureDTO.setFurnitureid(furniture.getFurnitureid());
        furnitureDTO.setFurnitureweight(furniture.getFurnitureweight());
        furnitureDTO.setPrice(furniture.getPrice());
        furnitureDTO.setSurface(furniture.getSurface());
        furnitureDTO.setPicture(furniture.getPicture());
        furnitureDTO.setRelocationFourtniture(furniture.getRelocationFourtniture() == null ? null : furniture.getRelocationFourtniture().getRelocationid());
        return furnitureDTO;
    }

    private Furniture mapToEntity(FurnitureDTO furnitureDTO, Furniture furniture) {
        furniture.setFurnitureweight(furnitureDTO.getFurnitureweight());
        furniture.setPrice(furnitureDTO.getPrice());
        furniture.setSurface(furnitureDTO.getSurface());
        furniture.setPicture(furnitureDTO.getPicture());
        final Relocation relocationFourtniture = furnitureDTO.getRelocationFourtniture() == null ? null : relocationRepository.findById(furnitureDTO.getRelocationFourtniture())
                .orElseThrow(() -> new NotFoundException("relocationFourtniture not found"));
        furniture.setRelocationFourtniture(relocationFourtniture);
        return furniture;
    }
}
