package com.example.demo.repository;

import com.example.demo.entity.Uloga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UlogaRepository extends JpaRepository<Uloga, Integer> {

    @Query("SELECT COUNT(u) > 0 FROM Uloga u WHERE u.naziv_uloge = :naziv")
    boolean existsByNazivUloge(@Param("naziv") String naziv_uloge);
    
}