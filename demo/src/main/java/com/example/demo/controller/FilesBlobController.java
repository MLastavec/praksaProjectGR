package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.FilesBlob;
import com.example.demo.service.FilesBlobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/files-blob")
public class FilesBlobController {
    
    @Autowired
    private FilesBlobService filesBlobService;

    @GetMapping
    public List<FilesBlob> getAll() {
        return filesBlobService.getAll();
    }

    @GetMapping("/{id}")
    public FilesBlob getFilesBlobById(@RequestParam Integer id) {
        return filesBlobService.getById(id);
    }
    
    
    
}
