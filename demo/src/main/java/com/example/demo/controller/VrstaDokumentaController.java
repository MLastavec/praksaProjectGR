package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.VrstaDokumenta;
import com.example.demo.service.VrstaDokumentaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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

    @PostMapping
    public VrstaDokumenta create(@RequestBody VrstaDokumenta vrsta) {
        return vrstaDokumentaService.save(vrsta);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        vrstaDokumentaService.delete(id);
    }
}
