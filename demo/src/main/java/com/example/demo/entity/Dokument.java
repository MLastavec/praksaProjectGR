package com.example.demo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "dokument")
public class Dokument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dokument")
    private Integer idDokument;

    @Column(name = "naziv_dokumenta")
    private String nazivDokumenta;

    @CreationTimestamp
    @Column(name = "datum_kreiranja", updatable = false, nullable = false)
    private LocalDateTime datumKreiranja;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "osobni_podaci_oib")
    private OsobniPodaci osobniPodaci;

    @ManyToOne
    @JoinColumn(name = "files_blob_id_files_blob")
    private FilesBlob filesBlob;

    @ManyToOne
    @JoinColumn(name = "vrsta_dokumenta_id_vrsta_dokumenta")
    private VrstaDokumenta vrstaDokumenta;

    public Dokument() {}

    public Integer getIdDokument() { return idDokument; }
    public void setIdDokument(Integer idDokument) { this.idDokument = idDokument; }

    public String getNazivDokumenta() { return nazivDokumenta; }
    public void setNazivDokumenta(String nazivDokumenta) { this.nazivDokumenta = nazivDokumenta; }

    public OsobniPodaci getOsobniPodaci() { return osobniPodaci; }
    public void setOsobniPodaci(OsobniPodaci osobniPodaci) { this.osobniPodaci = osobniPodaci; }

    public FilesBlob getFilesBlob() { return filesBlob; }
    public void setFilesBlob(FilesBlob filesBlob) { this.filesBlob = filesBlob; }

    public VrstaDokumenta getVrstaDokumenta() { return vrstaDokumenta; }
    public void setVrstaDokumenta(VrstaDokumenta vrstaDokumenta) { this.vrstaDokumenta = vrstaDokumenta; }

    public Dokument orElse(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElse'");
    }
}