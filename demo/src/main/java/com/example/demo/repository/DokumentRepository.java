package com.example.demo.repository;

import com.example.demo.entity.Dokument;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DokumentRepository extends JpaRepository<Dokument, Integer> { 
    
    Page<Dokument> findByOsobniPodaci_Oib(String oib, Pageable pageable);

    @Query(value = "SELECT * FROM dokument WHERE obrisan = 1", nativeQuery = true)
    List<Dokument> dohvatiSveObrisaneNative();

    
    @Query(value = "SELECT * FROM dokument WHERE id_dokument = :id", nativeQuery = true)
    Optional<Dokument> findByIdIgnoreRestriction(@Param("id") Integer id);
}