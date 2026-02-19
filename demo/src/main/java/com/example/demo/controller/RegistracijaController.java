package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.RegistracijaDTO;
import com.example.demo.entity.OsobniPodaci;
import com.example.demo.service.OsobniPodaciService;

@RestController
@RequestMapping("/api/registracija")
public class RegistracijaController {

    @Autowired
    private OsobniPodaciService osobniPodaciService;

    @PostMapping(value = "/registracija", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE) 
    public OsobniPodaci registracija(
            @ModelAttribute RegistracijaDTO dto, 
            @RequestParam("fileOsobna") MultipartFile f1,
            @RequestParam("fileBoraviste") MultipartFile f2,
            @RequestParam("fileDrzavljanstvo") MultipartFile f3,
            @RequestParam("fileTransakcije") MultipartFile f4) throws java.io.IOException {
        
     
        return osobniPodaciService.spremiSve(dto, f1, f2, f3, f4);
    }
}

