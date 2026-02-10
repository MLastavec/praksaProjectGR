package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "files_blob")
public class FilesBlob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_files_blob")
    private Integer idFilesBlob;

    @Lob
    @Column(name = "dokument", columnDefinition="LONGBLOB")
    private byte[] dokument;

    public FilesBlob() {}
    
    public Integer getIdFilesBlob() { return idFilesBlob; }
    public void setIdFilesBlob(Integer idFilesBlob) { this.idFilesBlob = idFilesBlob; }
    public byte[] getDokument() { return dokument; }
    public void setDokument(byte[] dokument) { this.dokument = dokument; }

}