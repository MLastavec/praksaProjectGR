package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dokument")
public class Dokument {

    @Id
    private Integer idDokument;

    private String naziv_dokumenta;

    @ManyToOne
    @JoinColumn(name = "OSOBNI_PODACI_OIB")
    private OsobniPodaci osobniPodaci;

    @ManyToOne
    @JoinColumn(name = "FILES_BLOB_idFilesBlob")
    private FilesBlob filesBlob;

    @ManyToOne
    @JoinColumn(name = "VRSTA_DOKUMENTA_idVrstaDokumenta")
    private VrstaDokumenta vrstaDokumenta;

    public Dokument() {}

    public Integer getIdDokument() { return idDokument; }
    public void setIdDokument(Integer idDokument) { this.idDokument = idDokument; }
    public String getNaziv_dokumenta() { return naziv_dokumenta; }
    public void setNaziv_dokumenta(String naziv_dokumenta) { this.naziv_dokumenta = naziv_dokumenta; }
    public OsobniPodaci getOsobniPodaci() { return osobniPodaci; }
    public void setOsobniPodaci(OsobniPodaci osobniPodaci) { this.osobniPodaci = osobniPodaci; }
    public FilesBlob getFilesBlob() { return filesBlob; }
    public void setFilesBlob(FilesBlob filesBlob) { this.filesBlob = filesBlob; }
    public VrstaDokumenta getVrstaDokumenta() { return vrstaDokumenta; }
    public void setVrstaDokumenta(VrstaDokumenta vrstaDokumenta) { this.vrstaDokumenta = vrstaDokumenta; }
}