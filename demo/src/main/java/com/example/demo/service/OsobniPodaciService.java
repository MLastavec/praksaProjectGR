package com.example.demo.service;

import com.example.demo.entity.OsobniPodaci;
import com.example.demo.repository.OsobniPodaciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        if (osobniPodaci == null) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST, "Osobni podaci ne smiju biti null!");
        }
        if (osobniPodaci.getOib() == null || osobniPodaci.getOib().trim().isEmpty()) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST, "OIB ne smije biti prazan!");
        }
        if (osobniPodaci.getOib().trim().length() != 11) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST, "OIB mora imati točno 11 znamenki!");
        }
        if (osobniPodaciRepository.existsById(osobniPodaci.getOib())) {
        throw new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.CONFLICT, "Osoba s OIB-om " + osobniPodaci.getOib() + " već postoji!");
        }
        if (osobniPodaci.getEmail() == null || !osobniPodaci.getEmail().contains("@")) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email adresa nije ispravna!");
        }
        
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
