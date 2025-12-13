package com.example.msemprunt.client;

import com.example.msemprunt.dto.UtilisateurDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MSUTILISATEUR", path = "/api/utilisateurs")
public interface UtilisateurClient {

    @GetMapping("/{id}")
    UtilisateurDTO getUtilisateurById(@PathVariable("id") Long id);
}
