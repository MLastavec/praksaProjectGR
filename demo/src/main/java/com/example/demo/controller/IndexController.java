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
    
}
