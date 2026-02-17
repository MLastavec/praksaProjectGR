package com.example.demo.service;

import com.example.demo.entity.Dokument;
import com.example.demo.entity.OsobniPodaci;
import com.example.demo.repository.DokumentRepository;
import com.example.demo.repository.OsobniPodaciRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections; 
import java.util.List;

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
    
    public void delete(Integer id) {
        if (!dokumentRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Greška pri brisanju: Dokument s ID-em " + id + " ne postoji!"
            );
        }
        dokumentRepository.deleteById(id);
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
}
