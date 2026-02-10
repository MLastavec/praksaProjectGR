package com.example.demo.repository;

import com.example.demo.entity.OsobniPodaci;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OsobniPodaciRepository extends JpaRepository<OsobniPodaci, String> {
    
}