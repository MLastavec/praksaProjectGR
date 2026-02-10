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

    public List<Uloga> findAll() {
        return ulogaRepository.findAll();
    }

    public Uloga save(Uloga uloga) {
        return ulogaRepository.save(uloga);
    }
}