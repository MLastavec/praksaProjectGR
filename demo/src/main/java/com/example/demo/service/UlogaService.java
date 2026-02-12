package com.example.demo.service;

import com.example.demo.entity.Uloga;
import com.example.demo.repository.UlogaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UlogaService {

    @Autowired
    private UlogaRepository ulogaRepository;

    public List<Uloga> getAll() {
        return ulogaRepository.findAll();
    }

    public Uloga getByIdUloga  (Integer id) {
       return ulogaRepository.findById(id)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, 
                "Uloga s ID-em " + id + " nije definirana u sustavu!"
            ));
    }
    // zakomentirano jer ne radi
    /*public Uloga create(Uloga uloga) {
        if (uloga == null) {
        throw new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.BAD_REQUEST, "Uloga ne smije biti null!");
    } 
        if (uloga.getNaziv_uloge() == null || uloga.getNaziv_uloge().trim().isEmpty()) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST, "Naziv uloge je obavezan!");
        }
        if (ulogaRepository.existsByNazivUloge(uloga.getNaziv_uloge())) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.CONFLICT, "Uloga s tim nazivom već postoji!");
        }

        return ulogaRepository.save(uloga);
    }*/

    public void deleteByIdUloga(Integer id) {
        ulogaRepository.deleteById(id);
    }
}