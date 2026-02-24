package com.example.demo.repository;

import com.example.demo.entity.OsobniPodaci;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OsobniPodaciRepository extends JpaRepository<OsobniPodaci, String> {

    boolean existsByKorisnickoIme(String korisnickoIme);
    Optional<OsobniPodaci> findByKorisnickoIme(String korisnickoIme);

    Page<OsobniPodaci> findAll(Pageable pageable);
    Page<OsobniPodaci> findByPrezimeContaining(String prezime, Pageable pageable);
    Optional<OsobniPodaci> findByOib(String oib);

    @Query(value = "SELECT * FROM osobni_podaci WHERE obrisan = 1", nativeQuery = true)
    List<OsobniPodaci> dohvatiSveObrisane();

    @Query(value = "SELECT * FROM osobni_podaci WHERE obrisan = 1", nativeQuery = true)
    List<OsobniPodaci> dohvatiSveObrisaneNative();
    @Query(value = "SELECT * FROM osobni_podaci WHERE oib = :oib", nativeQuery = true)
    Optional<OsobniPodaci> findByOibIgnoreRestriction(@Param("oib") String oib);
}