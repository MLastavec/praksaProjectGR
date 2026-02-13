package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.RegistracijaDTO;
import com.example.demo.entity.OsobniPodaci;
import com.example.demo.service.OsobniPodaciService;

@RestController
@RequestMapping("/api/registracija")
public class RegistracijaController {

    @Autowired
    private OsobniPodaciService osobniPodaciService;

    @PostMapping
    public OsobniPodaci registracija(@RequestBody RegistracijaDTO dto) {
        return osobniPodaciService.spremiKompletnuRegistraciju(dto);
    }
}

