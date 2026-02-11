package com.example.demo.service;

import com.example.demo.entity.OsobniPodaci;
import com.example.demo.repository.OsobniPodaciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OsobniPodaciService {

    @Autowired
    private OsobniPodaciRepository osobniPodaciRepository;

    public List<OsobniPodaci> getAll() {
        return osobniPodaciRepository.findAll();
    }

     
    public OsobniPodaci getById(String oib) {
        return osobniPodaciRepository.findById(oib)
            .orElseThrow(() -> new RuntimeException("Greška: Podaci za OIB " + oib + " ne postoje u sustavu!"));
    }

    public OsobniPodaci create(OsobniPodaci osobniPodaci) {
        return osobniPodaciRepository.save(osobniPodaci);
    }

    public OsobniPodaci delete(String oib) {
       if (!osobniPodaciRepository.existsById(oib)) {
        throw new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.NOT_FOUND, 
            "Brisanje neuspješno: Osoba s OIB-om " + oib + " ne postoji u bazi!"
        );
    }
        osobniPodaciRepository.deleteById(oib);
        return null;
}

}
