package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;



@Entity
@Table(name = "vrsta_dokumenta")
public class VrstaDokumenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vrsta_dokumenta")
    private Integer idVrstaDokumenta;

    @Column(name = "vrsta_dokumenta")
    @Schema(description = "Naziv vrste dokumenta", example = "Faktura")
    private String vrsta_dokumenta;

    @CreationTimestamp
    @Column(name = "datum_kreiranja")
    private LocalDate datum_kreiranja;
    
    @Column(name = "kreirao")
    @Schema(description = "Korisnik koji je kreirao zapis", example = "admin")
    private String kreirao;

    public VrstaDokumenta() {}

    public Integer getIdVrstaDokumenta() { return idVrstaDokumenta; }
    public void setIdVrstaDokumenta(Integer idVrstaDokumenta) { this.idVrstaDokumenta = idVrstaDokumenta; }
    public String getVrsta_dokumenta() { return vrsta_dokumenta; }
    public void setVrsta_dokumenta(String vrsta_dokumenta) { this.vrsta_dokumenta = vrsta_dokumenta; }
    public LocalDate getDatum_kreiranja() { return datum_kreiranja; }
    public void setDatum_kreiranja(LocalDate datum_kreiranja) { this.datum_kreiranja = datum_kreiranja; }
    public String getKreirao() { return kreirao; }
    public void setKreirao(String kreirao) { this.kreirao = kreirao; }
}