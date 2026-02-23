package com.example.demo.controller;

import com.example.demo.entity.OsobniPodaci;
import com.example.demo.service.OsobniPodaciService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/osobni-podaci")
public class OsobniPodaciController {

    @Autowired
    private OsobniPodaciService osobniPodaciService;

    @GetMapping("/moj-pregled")
    public Page<OsobniPodaci> getMojPregled(
        Authentication auth, 
        @RequestParam(defaultValue = "0") int stranica 
    ) {
        return osobniPodaciService.dohvatiStraniceno(auth, stranica);
    }


    @GetMapping("/svi")
    public Page<OsobniPodaci> getAll(@RequestParam(defaultValue = "0") Authentication stranica) {
        return osobniPodaciService.dohvatiStraniceno(stranica, 10);
    }

    @DeleteMapping("/{oib}")
    public ResponseEntity<?> obrisiKorisnika(@PathVariable String oib) {
        try {
            osobniPodaciService.deleteById(oib);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Greška pri brisanju: " + e.getMessage());
        }
    }
    
    
    @GetMapping("/auth-status")
    @ResponseBody
    public String getStatus() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/{oib}")
    public OsobniPodaci dohvatiPoOibu(@PathVariable String oib) {
        return osobniPodaciService.findByOib(oib); 
    }
    
    @PutMapping(value = "/{oib}", consumes = "application/json", produces = "application/json")
    public OsobniPodaci azuriraj(@PathVariable String oib, @RequestBody OsobniPodaci noviPodaci) {
        System.out.println("Primljen zahtjev za OIB: " + oib);
        return osobniPodaciService.azurirajKorisnika(oib, noviPodaci);
    }
}
