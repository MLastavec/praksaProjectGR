package com.example.demo.repository;

import com.example.demo.entity.Uloga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UlogaRepository extends JpaRepository<Uloga, Integer> {

    /*boolean existsByNazivUloge(String naziv_uloge);*/
    
}