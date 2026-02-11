package com.example.demo.controller;

import com.example.demo.entity.Uloga;
import com.example.demo.service.UlogaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/uloge")
public class UlogaController {

    @Autowired
    private UlogaService service;

    @GetMapping
    public List<Uloga> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Uloga getById(@PathVariable Integer id) {
        return service.getByIdUloga(id);
    }
}
