package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.OsobniPodaci;
import com.example.demo.service.OsobniPodaciService;

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
}
