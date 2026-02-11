package com.example.demo.service;

import com.example.demo.entity.Dokument;
import com.example.demo.repository.DokumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DokumentService {

    @Autowired
    private DokumentRepository dokumentRepository;

    public List<Dokument> getAll() {
        return dokumentRepository.findAll();
    }

    public Dokument getById(Integer id) {
        return dokumentRepository.findById(id).orElse(null);
    }
}
