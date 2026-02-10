package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "uloga")
public class Uloga {

    @Id
    @Column(name = "idULOGA")
    private Integer idUloga;
    
    private String naziv_uloge;

    
    public Uloga() {
    }

    public int getIdUloga() {
        return idUloga;
    }

    public void setIdUloga(int idUloga) {
        this.idUloga = idUloga;
    }

    public String getNaziv_uloge() {
        return naziv_uloge;
    }

    public void setNaziv_uloge(String naziv_uloge) {
        this.naziv_uloge = naziv_uloge;
    }
}
