package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; 
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Dokument;
import com.example.demo.service.DokumentService;

@RestController
@RequestMapping("/api/dokument")
public class DokumentController {
    
    @Autowired
    private DokumentService dokumentService;

    @GetMapping
    public List<Dokument> getAll() {
        return dokumentService.getAll();
    }
    
    @GetMapping("/{id}")
    public Dokument getDokumentById(@PathVariable Integer id) {
        return dokumentService.getById(id);
    }
    
    @PostMapping
    public Dokument create(@RequestBody Dokument dokument) {
        return dokumentService.create(dokument);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        dokumentService.delete(id);
    }

    @GetMapping("/moj-pregled")
    public Page<Dokument> getDokumenti(
        Authentication auth, 
        @RequestParam(defaultValue = "0") int stranica
    ) {
        return dokumentService.dohvatiDokumenteStraniceno(auth, stranica);
    }
}