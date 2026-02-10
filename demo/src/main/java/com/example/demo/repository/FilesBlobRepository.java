package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.FilesBlob;

@Repository
public interface FilesBlobRepository extends JpaRepository<FilesBlob, Integer> {

}