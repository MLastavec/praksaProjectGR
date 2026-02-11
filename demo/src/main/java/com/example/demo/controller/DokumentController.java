package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Dokument;
import com.example.demo.service.DokumentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/dokument")
public class DokumentController {
    
    @Autowired
    private DokumentService dokumentService;

    @GetMapping
    public List<Dokument> getAll() {
        return dokumentService.getAll();
    }
    
    @GetMapping("/{id}")
    public Dokument getDokumentById(@PathVariable Integer id) {
        return dokumentService.getById(id);
    }
    
    @PostMapping
    public Dokument create(@RequestBody Dokument dokument) {
        return dokumentService.create(dokument);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        dokumentService.delete(id);
    }
}
