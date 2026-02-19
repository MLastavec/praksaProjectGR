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

     public String getOib() { return oib; }
    public void setOib(String oib) { this.oib = oib; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public LocalDate getDatum_rodenja() { return datum_rodenja; }
    public void setDatum_rodenja(LocalDate datum_rodenja) { this.datum_rodenja = datum_rodenja; }

    public String getAdresa() { return adresa; }
    public void setAdresa(String adresa) { this.adresa = adresa; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBrojTelefona() { return brojTelefona; }
    public void setBrojTelefona(String brojTelefona) { this.brojTelefona = brojTelefona; }

    public String getKorisnickoIme() { return korisnickoIme; }
    public void setKorisnickoIme(String korisnickoIme) { this.korisnickoIme = korisnickoIme; }

    public String getLozinka() { return lozinka; }
    public void setLozinka(String lozinka) { this.lozinka = lozinka; }

    public LocalDateTime getDatumKreiranja() { return datumKreiranja; }
    public void setDatumKreiranja(LocalDateTime datumKreiranja) { this.datumKreiranja = datumKreiranja; }

    public String getUloga() { return uloga; }
    public void setUloga(String uloga) { this.uloga = uloga; }

    public List<DokumentDTO> getDokumenti() { return dokumenti; }
    public void setDokumenti(List<DokumentDTO> dokumenti) { this.dokumenti = dokumenti; }

}
