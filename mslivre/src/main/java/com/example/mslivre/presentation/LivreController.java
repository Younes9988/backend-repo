package com.example.mslivre.presentation;

import com.example.mslivre.dto.LivreDTO;
import com.example.mslivre.service.ILivreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/livres")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LivreController {

    private final ILivreService livreService;

    // Directory where images will be saved
    private static final String UPLOAD_DIR = "uploads/livres/";

    @GetMapping("")
    public ResponseEntity<List<LivreDTO>> getAllLivres() {
        return ResponseEntity.ok(livreService.findAllLivre());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivreDTO> getLivreById(@PathVariable Long id) {
        return livreService.findLivreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATED: Now consumes Multipart Form Data (JSON + File)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LivreDTO> createLivre(
            @RequestPart("livre") String livreJson,    // The JSON data as a String
            @RequestPart("file") MultipartFile file    // The image file
    ) throws IOException {

        // 1. Convert JSON string to DTO
        ObjectMapper objectMapper = new ObjectMapper();
        // Required to handle LocalDate parsing correctly if using Java 8 time module
        objectMapper.findAndRegisterModules();
        LivreDTO livreDTO = objectMapper.readValue(livreJson, LivreDTO.class);

        // 2. Handle Image Upload
        if (!file.isEmpty()) {
            String fileName = saveImage(file);
            // Set the generated file path/name in the DTO before saving to DB
            livreDTO.setImage(fileName);
        }

        // 3. Call Service
        LivreDTO createdLivre = livreService.addLivre(livreDTO);
        return new ResponseEntity<>(createdLivre, HttpStatus.CREATED);
    }

    @PutMapping("/updateLivre/{id}")
    public ResponseEntity<LivreDTO> updateLivre(@PathVariable Long id, @RequestBody LivreDTO livreDTO) {
        System.out.println("🔥 UPDATE LIVRE CALLED — id=" + id + ", newCopies=" + livreDTO.getNombreCopies());
        // Note: If you want to update the image later, you would need a similar Multipart method for PUT
        try {
            return ResponseEntity.ok(livreService.updateLivre(livreDTO, id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteLivre/{id}")
    public ResponseEntity<Void> deleteLivre(@PathVariable Long id) {
        try {
            livreService.deleteLivre(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // --- Helper Method to Save File ---
    private String saveImage(MultipartFile file) throws IOException {
        // Create directory if it doesn't exist
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename to prevent overwrites (e.g., "uuid_filename.jpg")
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        // Save file
        Files.copy(file.getInputStream(), filePath);

        // Return the path (or URL) to be stored in the database
        return UPLOAD_DIR + fileName;
    }
}