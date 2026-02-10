package com.example.demo.service;

import com.example.demo.entity.FilesBlob;
import com.example.demo.repository.FilesBlobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FilesBlobService {
    @Autowired
    private FilesBlobRepository filesBlobRepository;

    public List<FilesBlob> getAllFilesBlob() {
        return filesBlobRepository.findAll();
    }

    public FilesBlob saveFilesBlob(FilesBlob filesBlob) {
        return filesBlobRepository.save(filesBlob);
    }
}