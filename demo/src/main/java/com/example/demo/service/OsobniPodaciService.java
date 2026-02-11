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

     
    public OsobniPodaci getById(String id) {
        return osobniPodaciRepository.findById(id).orElse(null);
    }


}
