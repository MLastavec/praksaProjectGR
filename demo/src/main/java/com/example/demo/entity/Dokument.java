package com.example.demo.entity;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "dokument")
@SQLRestriction("obrisan = 0")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Dokument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dokument")
    private Integer idDokument;

    @Column(name = "naziv_dokumenta")
    @Schema(description = "Naziv dokumenta", example = "Osobna iskaznica")
    private String nazivDokumenta;

    @CreationTimestamp
    @Column(name = "datum_kreiranja", updatable = false, nullable = false)
    private LocalDateTime datumKreiranja;

    @JsonIgnoreProperties({"dokumenti", "hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "osobni_podaci_oib")
    private OsobniPodaci osobniPodaci;

    @JsonIgnore 
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "files_blob_id_files_blob")
    private FilesBlob filesBlob;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vrsta_dokumenta_id_vrsta_dokumenta")
    private VrstaDokumenta vrstaDokumenta;

    @Column(name = "obrisan")
    private boolean obrisan = false;

    
    public Dokument() {}

    public Integer getIdDokument() { return idDokument; }
    public void setIdDokument(Integer idDokument) { this.idDokument = idDokument; }

    public String getNazivDokumenta() { return nazivDokumenta; }
    public void setNazivDokumenta(String nazivDokumenta) { this.nazivDokumenta = nazivDokumenta; }

    public LocalDateTime getDatumKreiranja() { return datumKreiranja; }
    public void setDatumKreiranja(LocalDateTime datumKreiranja) { this.datumKreiranja = datumKreiranja; }

    public OsobniPodaci getOsobniPodaci() { return osobniPodaci; }
    public void setOsobniPodaci(OsobniPodaci osobniPodaci) { this.osobniPodaci = osobniPodaci; }

    public FilesBlob getFilesBlob() { return filesBlob; }
    public void setFilesBlob(FilesBlob filesBlob) { this.filesBlob = filesBlob; }

    public VrstaDokumenta getVrstaDokumenta() { return vrstaDokumenta; }
    public void setVrstaDokumenta(VrstaDokumenta vrstaDokumenta) { this.vrstaDokumenta = vrstaDokumenta; }

    public boolean isObrisan() { return obrisan; }
    public void setObrisan(boolean obrisan) { this.obrisan = obrisan; }
}