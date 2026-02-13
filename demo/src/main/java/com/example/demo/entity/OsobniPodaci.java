package com.example.demo.entity; 
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "osobni_podaci")
public class OsobniPodaci {

    @Id
    @Column(name = "oib")
    private String oib;

    @Column(name = "ime")
    private String ime;

    @Column(name = "prezime")
    private String prezime;
    
    @Column(name = "datum_rodenja")
    private LocalDate datum_rodenja;

    @Column(name = "adresa")
    private String adresa;

    @Column(name = "broj_telefona")
    private String broj_telefona;

    @Column(name = "email")
    private String email;

    @Column(name = "korisnicko_ime")
    private String korisnickoIme;

    @Column(name = "lozinka")
    private String lozinka;

    @CreationTimestamp
    @Column(name = "datum_kreiranja", updatable = false, nullable = false)
    private LocalDateTime datumKreiranja;

    @ManyToOne
    @JoinColumn(name = "uloga_iduloga")
    private Uloga uloga;

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

    public String getKorisnicko_ime() { return korisnickoIme; }
    public void setKorisnicko_ime(String korisnicko_ime) { this.korisnickoIme = korisnicko_ime; }

    public String getLozinka() { return lozinka; }
    public void setLozinka(String lozinka) { this.lozinka = lozinka; }

    public Uloga getUloga() { return uloga; }
    public void setUloga(Uloga uloga) { this.uloga = uloga; }

    public List<Dokument> getDokumenti() { return dokumenti; }
    public void setDokumenti(List<Dokument> dokumenti) { this.dokumenti = dokumenti; }
}

