package com.example.demo.service;

import com.example.demo.entity.VrstaDokumenta;
import com.example.demo.repository.VrstaDokumentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class VrstaDokumentaService {
    @Autowired
    private VrstaDokumentaRepository vrstaDokumentaRepository;

    public List<VrstaDokumenta> getAll() {
        return vrstaDokumentaRepository.findAll();
    }

    public VrstaDokumenta getById(Integer id) {
        return vrstaDokumentaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Vrsta dokumenta s ID-em " + id + " ne postoji!"));
    }

    public VrstaDokumenta save(VrstaDokumenta vrsta) {
        return vrstaDokumentaRepository.save(vrsta);
    }

    public void delete(Integer id) {
        if (!vrstaDokumentaRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Nemoguće obrisati: Vrsta dokumenta s ID-em " + id + " nije pronađena!"
            );
        }
        vrstaDokumentaRepository.deleteById(id);
    }

}
