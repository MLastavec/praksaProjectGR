package com.example.demo.entity; 
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "osobni_podaci")
public class OsobniPodaci {

    @Id
    private String oib;

    private String ime;
    private String prezime;
    
    private LocalDate datum_rodenja;
    private String adresa;
    private String broj_telefona;
    private String email;
    private String korisnicko_ime;
    private String lozinka;

    
    @ManyToOne
    @JoinColumn(name = "ULOGA_idULOGA")
    private Uloga uloga;

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

    public String getKorisnicko_ime() { return korisnicko_ime; }
    public void setKorisnicko_ime(String korisnicko_ime) { this.korisnicko_ime = korisnicko_ime; }

    public String getLozinka() { return lozinka; }
    public void setLozinka(String lozinka) { this.lozinka = lozinka; }

    public Uloga getUloga() { return uloga; }
    public void setUloga(Uloga uloga) { this.uloga = uloga; }
}