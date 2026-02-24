package com.example.demo.service;

import com.example.demo.entity.Dokument;
import com.example.demo.entity.OsobniPodaci;
import com.example.demo.repository.DokumentRepository;
import com.example.demo.repository.OsobniPodaciRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections; 
import java.util.List;

import org.springframework.data.domain.Pageable;


@Service
public class DokumentService {

    @Autowired
    private DokumentRepository dokumentRepository;

    @Autowired 
    private OsobniPodaciRepository osobniPodaciRepository;

    public List<Dokument> getAll() {
        return dokumentRepository.findAll();
    }

    public Dokument getById(Integer id) {
        return dokumentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Dokument s ID-em " + id + " ne postoji u bazi podataka!"));
    }

    public Dokument create(Dokument dokument) {
        if (dokument == null) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Podaci nisu poslani.");
        }
        return dokumentRepository.save(dokument);
    } 
    
    @Transactional
    public void delete(Integer id) {
        Dokument dokument = dokumentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dokument nije pronađen"));
        dokument.setObrisan(true);
        dokumentRepository.save(dokument);
    }

    public List<Dokument> dohvatiDokumenteOvisnoOUlozi(Authentication auth) {
        if (auth == null) return Collections.emptyList();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return dokumentRepository.findAll(); 
        } else {
            String username = auth.getName();
            return osobniPodaciRepository.findByKorisnickoIme(username)
                    .map(OsobniPodaci::getDokumenti)
                    .orElse(Collections.emptyList());
        }
    }

   public Page<Dokument> dohvatiDokumenteStraniceno(Authentication auth, int stranica) {
    Pageable pageable = PageRequest.of(stranica, 10);
    
    if (auth == null) return Page.empty();

    boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    if (isAdmin) {
        return dokumentRepository.findAll(pageable);
    } else {
        String username = auth.getName();
        return osobniPodaciRepository.findByKorisnickoIme(username)
                .map(osoba -> dokumentRepository.findByOsobniPodaci_Oib(osoba.getOib(), pageable))
                .orElse(Page.empty());
    }
}

    @Transactional
    public void restoreDokument(Integer id) { 
        Dokument dokument = dokumentRepository.findByIdIgnoreRestriction(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dokument nije pronađen"));
        
        dokument.setObrisan(false);
        dokumentRepository.save(dokument);
    }

    public List<Dokument> dohvatiArhivu() {
        return dokumentRepository.dohvatiSveObrisaneNative();
    }


}
