package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class IndexController {
   @GetMapping("/")
    public String index() {
        return "forward:/html/index.html"; 
    }

    @GetMapping("/osobnipodaci")
    public String osobnipodaci() {
        return "forward:/html/osobni_podaci.html"; 
    }

    @GetMapping("/prijava")
    public String prijava() {
        return "forward:/html/prijava.html";
    }
    
    @GetMapping("/dokumenti")
    public String dokumenti() {
        return "forward:/html/dokumenti.html";
    }

    @GetMapping("/registracija")
    public String registracija() {
        return "forward:/html/registracija.html";
    }
    
    @GetMapping("/arhiva")
    public String prikaziArhivu() {
        return "forward:/html/arhiva.html";
    }
    
}
