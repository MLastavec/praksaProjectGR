package com.example.demo.service;

import com.example.demo.entity.VrstaDokumenta;
import com.example.demo.repository.VrstaDokumentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VrstaDokumentaService {
    @Autowired
    private VrstaDokumentaRepository vrstaDokumentaRepository;

    public List<VrstaDokumenta> getAll() {
        return vrstaDokumentaRepository.findAll();
    }

    public VrstaDokumenta getById(Integer id) {
        return vrstaDokumentaRepository.findById(id).orElse(null);
    }
}
