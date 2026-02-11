package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.FilesBlob;
import com.example.demo.service.FilesBlobService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
    public FilesBlob getFilesBlobById(@PathVariable Integer id) {
        return filesBlobService.getById(id);
    }
    
    @PostMapping
    public FilesBlob createFilesBlob(@RequestBody FilesBlob filesBlob) {
        return filesBlobService.create(filesBlob);
    }

   @DeleteMapping("/{id}")
    public FilesBlob delete(@PathVariable Integer id) {
        return filesBlobService.delete(id);
    }
      
}
