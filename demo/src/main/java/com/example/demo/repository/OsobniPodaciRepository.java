package com.example.demo.repository;

import com.example.demo.entity.OsobniPodaci;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OsobniPodaciRepository extends JpaRepository<OsobniPodaci, String> {

    boolean existsByKorisnickoIme(String korisnickoIme);
    Optional<OsobniPodaci> findByKorisnickoIme(String korisnickoIme);

    Page<OsobniPodaci> findAll(Pageable pageable);
    Page<OsobniPodaci> findByPrezimeContaining(String prezime, Pageable pageable);
}