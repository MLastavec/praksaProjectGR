package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RegistracijaDTO {
    
    public String oib;
    public String ime;
    public String prezime;
    public LocalDate datum_rodenja;
    public String adresa;
    public String email;
    public String brojTelefona;
    public String korisnickoIme;
    public String lozinka;
    public LocalDateTime datumKreiranja;
    public String uloga;
    public List<DokumentDTO> dokumenti;
}
