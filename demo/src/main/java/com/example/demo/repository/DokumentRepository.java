package com.example.demo.repository;

import com.example.demo.entity.Dokument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DokumentRepository extends JpaRepository<Dokument, Integer> {
    Page<Dokument> findByOsobniPodaci_Oib(String oib, Pageable pageable);
}