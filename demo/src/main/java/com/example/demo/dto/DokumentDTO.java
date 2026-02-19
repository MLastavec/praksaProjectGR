package com.example.demo.dto;

public class DokumentDTO {
    public String nazivDokumenta;
    public Integer idVrstaDokumenta; 
    public String filesBlob;

    public String getNazivDokumenta() {
        return nazivDokumenta;
    }

    public void setNazivDokumenta(String nazivDokumenta) {
        this.nazivDokumenta = nazivDokumenta;
    }

    public Integer getIdVrstaDokumenta() {
        return idVrstaDokumenta;
    }

    public void setIdVrstaDokumenta(Integer idVrstaDokumenta) {
        this.idVrstaDokumenta = idVrstaDokumenta;
    }

    public String getFilesBlob() {
        return filesBlob;
    }

    public void setFilesBlob(String filesBlob) {
        this.filesBlob = filesBlob;
    }
}
