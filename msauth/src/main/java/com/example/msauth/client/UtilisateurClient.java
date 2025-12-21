package com.example.msauth.client;


import com.example.msauth.dto.UtilisateurCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "gateway",
        url = "http://localhost:5050"
)
public interface UtilisateurClient {

    @PostMapping("/MSUTILISATEUR/api/utilisateurs/lecteur")
    void createLecteur(@RequestBody UtilisateurCreateRequest request);
}