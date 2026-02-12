package com.example.demo.repository;

import com.example.demo.entity.VrstaDokumenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VrstaDokumentaRepository extends JpaRepository<VrstaDokumenta, Integer> {

   @Query("SELECT COUNT(v) > 0 FROM VrstaDokumenta v WHERE v.vrsta_dokumenta = :vrijednost")
    boolean existsByVrstaDokumentaCustom(@Param("vrijednost") String vrijednost);
    
}