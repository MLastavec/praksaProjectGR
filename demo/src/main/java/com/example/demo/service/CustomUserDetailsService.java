package com.example.demo.service;

import com.example.demo.entity.OsobniPodaci;
import com.example.demo.repository.OsobniPodaciRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final OsobniPodaciRepository repository;

    public CustomUserDetailsService(OsobniPodaciRepository repository) {
        this.repository = repository;
    }

   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OsobniPodaci osoba = repository.findByKorisnickoIme(username)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik "+ username + " nije pronađen!" ));

        String ulogaNaziv;
        
        if (osoba.getUloga() != null && osoba.getUloga().getIdUloga() == 1) { 
            ulogaNaziv = "USER";
        } else {
            ulogaNaziv = "ADMIN";
        }

        return User.builder()
                .username(osoba.getKorisnicko_ime()) 
                .password(osoba.getLozinka()) 
                .roles(ulogaNaziv) 
                .build();

                
    }

    
}
