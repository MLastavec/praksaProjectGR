package com.example.demo.entity; 
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "osobni_podaci")
public class OsobniPodaci {

    @Id
    @Column(name = "oib")
    @Schema(description = "Osobni identifikacijski broj (OIB)", example = "12345678901")
    private String oib;

    @Column(name = "ime")
    @Schema(description = "Ime korisnika", example = "Ivan")
    private String ime;

    @Column(name = "prezime")
    @Schema(description = "Prezime korisnika", example = "Horvat")
    private String prezime;
    
    @Column(name = "datum_rodenja")
    @org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd") 
    private LocalDate datum_rodenja;

    @Column(name = "adresa")
    @Schema(description = "Adresa korisnika", example = "Ulica 123, Grad")
    private String adresa;

    @Column(name = "broj_telefona")
    @Schema(description = "Broj telefona korisnika", example = "+38591234567")
    private String broj_telefona;

    @Column(name = "email")
    @Schema(description = "Email adresa korisnika", example = "ivan.horvat@example.com")
    private String email;

    @Column(name = "korisnicko_ime")
    @Schema(description = "Korisničko ime za prijavu", example = "ivanhorvat")
    private String korisnickoIme;

    @Column(name = "lozinka")
    @Schema(description = "Lozinka za prijavu", example = "lozinka123")
    private String lozinka;

    @CreationTimestamp
    @Column(name = "datum_kreiranja", updatable = false, nullable = false)
    private LocalDateTime datumKreiranja;

    @ManyToOne
    @JoinColumn(name = "uloga_iduloga")
    private Uloga uloga;
    
    @JsonIgnore 
    @JsonManagedReference
    @OneToMany(mappedBy = "osobniPodaci", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Dokument> dokumenti;

    public OsobniPodaci() {}

   
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

    public String getBroj_telefona() { return broj_telefona; }
    public void setBroj_telefona(String broj_telefona) { this.broj_telefona = broj_telefona; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getKorisnickoIme() { return korisnickoIme; }
    public void setKorisnickoIme(String korisnickoIme) { this.korisnickoIme = korisnickoIme; }

    public String getLozinka() { return lozinka; }
    public void setLozinka(String lozinka) { this.lozinka = lozinka; }

    public Uloga getUloga() { return uloga; }
    public void setUloga(Uloga uloga) { this.uloga = uloga; }

    public void setDatumKreiranja(LocalDateTime datumKreiranja) { 
    this.datumKreiranja = datumKreiranja; 
    }

    public List<Dokument> getDokumenti() { return dokumenti; }
    public void setDokumenti(List<Dokument> dokumenti) { this.dokumenti = dokumenti; }

}

