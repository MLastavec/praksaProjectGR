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

    public List<Dokument> findAll() {
        return dokumentRepository.findAll();
    }

    public Dokument save(Dokument dokument) {
        return dokumentRepository.save(dokument);
    }
}
