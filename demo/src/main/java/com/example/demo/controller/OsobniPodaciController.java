package com.example.demo.controller;

import com.example.demo.entity.OsobniPodaci;
import com.example.demo.service.OsobniPodaciService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public void delete(@PathVariable String oib) {
        osobniPodaciService.delete(oib);
    }
    
    
    @GetMapping("/api/auth/status")
    @ResponseBody
    public String getStatus() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
