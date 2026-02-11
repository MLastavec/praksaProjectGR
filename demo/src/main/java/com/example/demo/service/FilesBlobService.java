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

    public List<FilesBlob> getAll() {
        return filesBlobRepository.findAll();
    }

    public FilesBlob getById(Integer id) {
        return filesBlobRepository.findById(id).orElse(null);
    }
}