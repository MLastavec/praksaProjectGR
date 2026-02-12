package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.OsobniPodaci;
import com.example.demo.service.OsobniPodaciService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/osobni-podaci")
public class OsobniPodaciController {
    
    @Autowired
    private OsobniPodaciService osobniPodaciService;

    @GetMapping
    public List<OsobniPodaci> getAll() {
        return osobniPodaciService.getAll();
    }

    @GetMapping("/{oib}")
    public OsobniPodaci getById(@PathVariable String oib) {
        return osobniPodaciService.getById(oib);
    }

    @PostMapping
    public OsobniPodaci create(@RequestBody OsobniPodaci osobniPodaci) {
        return osobniPodaciService.create(osobniPodaci);
    }

    @PostMapping("/registracija-kompletna")
    public ResponseEntity<OsobniPodaci> createSveOdjednom(@RequestBody OsobniPodaci podaci) {
        OsobniPodaci spremljeno = osobniPodaciService.saveSve(podaci);
        return new ResponseEntity<>(spremljeno, HttpStatus.CREATED);
    }

    @DeleteMapping("/{oib}")
    public OsobniPodaci delete(@PathVariable String oib) {
        return osobniPodaciService.delete(oib);
    }
    
}
