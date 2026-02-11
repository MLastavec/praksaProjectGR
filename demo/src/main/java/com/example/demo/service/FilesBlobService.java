package com.example.demo.service;

import com.example.demo.entity.FilesBlob;
import com.example.demo.repository.FilesBlobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FilesBlobService {
    @Autowired
    private FilesBlobRepository filesBlobRepository;

    public List<FilesBlob> getAll() {
        return filesBlobRepository.findAll();
    }

    public FilesBlob getById(Integer id) {
      return filesBlobRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Datoteka (Blob) s ID-em " + id + " nije pronađena!"));
    }

    public FilesBlob create(FilesBlob filesBlob) {
        return filesBlobRepository.save(filesBlob);
    }

    public FilesBlob delete(Integer id) {
       if (!filesBlobRepository.existsById(id)) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, 
                "Nemoguće obrisati datoteku: ID " + id + " nije pronađen."
            );
        }
        filesBlobRepository.deleteById(id);
        return null;
    }       
}