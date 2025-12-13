package com.example.mslivre.mapper;

import com.example.mslivre.dto.LivreDTO;
import com.example.mslivre.model.Livre;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

@Component
public class LivreMapper {

    // Convert Entity to DTO
    public LivreDTO fromEntity(Livre livre) {
        LivreDTO dto = new LivreDTO();
        BeanUtils.copyProperties(livre, dto);
        return dto;
    }

    // Convert DTO to Entity
    public Livre toEntity(LivreDTO dto) {
        Livre livre = new Livre();
        BeanUtils.copyProperties(dto, livre);
        return livre;
    }
}
