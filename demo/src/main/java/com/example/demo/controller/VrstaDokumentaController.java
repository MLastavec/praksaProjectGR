package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.VrstaDokumenta;
import com.example.demo.service.VrstaDokumentaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/vrsta-dokumenta")
public class VrstaDokumentaController {
    
    @Autowired
    private VrstaDokumentaService vrstaDokumentaService;

    @GetMapping
    public List<VrstaDokumenta> getAll() {
        return vrstaDokumentaService.getAll();
    }

    @GetMapping("/{id}")
    public VrstaDokumenta getById(@PathVariable Integer id) {
        return vrstaDokumentaService.getById(id);
    }
}
