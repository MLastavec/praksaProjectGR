package com.example.demo.controller;

import java.security.Principal;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @GetMapping("/api/korisnik")
    public String getTrenutniKorisnik(Principal principal) {
        return (principal == null) ? "Gost" : principal.getName();
    }
}