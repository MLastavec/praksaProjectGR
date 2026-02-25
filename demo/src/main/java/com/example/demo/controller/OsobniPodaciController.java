package com.example.demo.controller;

import com.example.demo.entity.OsobniPodaci;
import com.example.demo.service.OsobniPodaciService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
            System.out.println("Pokušavam obrisati korisnika: " + oib);
            osobniPodaciService.deleteById(oib); 
            return ResponseEntity.ok().body("Korisnik uspješno obrisan (logički).");
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(500).body("Greška na serveru: " + e.getMessage());
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

    @GetMapping("/podaci-arhive")
    public List<OsobniPodaci> getArhivaPodaci() {
        return osobniPodaciService.dohvatiSveObrisane();
    }


    @PostMapping("/{oib}/restore")
    public ResponseEntity<?> vratiKorisnika(@PathVariable String oib) {
        try {
            osobniPodaciService.restoreById(oib);
            return ResponseEntity.ok().body("Korisnik vraćen!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Greška: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OsobniPodaci noviKorisnik) {
        try {
            OsobniPodaci spremljeno = osobniPodaciService.create(noviKorisnik);
            return ResponseEntity.ok(spremljeno);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Greška pri spremanju: " + e.getMessage());
        }
    }

   @GetMapping("/trenutna-uloga")
    public String getTrenutnaUloga(Authentication auth) {
        if (auth != null && auth.getName().equals("lanic")) {
            return "ADMIN";
        }
        return "USER";
    }
}
