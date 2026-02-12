package com.example.demo.service;

import com.example.demo.entity.Dokument;
import com.example.demo.repository.DokumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DokumentService {

    @Autowired
    private DokumentRepository dokumentRepository;

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
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, 
                "Greška pri brisanju: Dokument s ID-em " + id + " ne postoji!"
            );
        }
        
        dokumentRepository.deleteById(id);
    }

}
