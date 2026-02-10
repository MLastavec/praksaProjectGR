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

    public List<OsobniPodaci> getAllOsobniPodaci() {
        return osobniPodaciRepository.findAll();
    }

    public OsobniPodaci saveOsobniPodaci(OsobniPodaci osobniPodaci) {
        return osobniPodaciRepository.save(osobniPodaci);
    }
}
