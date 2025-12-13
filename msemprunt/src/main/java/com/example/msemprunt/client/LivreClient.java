package com.example.msemprunt.client;

import com.example.msemprunt.dto.LivreDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MSLIVRE", path = "/api/livres")
public interface LivreClient {

    @GetMapping("/{id}")
    LivreDTO getLivreById(@PathVariable("id") Long id);
    @PutMapping("/updateLivre/{id}")
    LivreDTO updateLivre(@PathVariable Long id, @RequestBody LivreDTO dto);
}