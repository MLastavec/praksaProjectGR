package com.example.demo.service;

import com.example.demo.dto.RegistracijaDTO;
import com.example.demo.entity.Dokument;
import com.example.demo.entity.FilesBlob;
import com.example.demo.entity.OsobniPodaci;
import com.example.demo.entity.Uloga;
import com.example.demo.entity.VrstaDokumenta;
import com.example.demo.repository.OsobniPodaciRepository;
import com.example.demo.repository.UlogaRepository;
import com.example.demo.repository.VrstaDokumentaRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication; 
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;

@Service
public class OsobniPodaciService {

    @Autowired
    private OsobniPodaciRepository osobniPodaciRepository;

    @Autowired
    private VrstaDokumentaRepository vrstaDokumentaRepository;

    @Autowired
    private UlogaRepository ulogaRepository;

    public List<OsobniPodaci> getAll() {
        return osobniPodaciRepository.findAll();
    }

    public OsobniPodaci getById(String oib) {
        return osobniPodaciRepository.findById(oib)
            .orElseThrow(() -> new RuntimeException("Greška: Podaci za OIB " + oib + " ne postoje u sustavu!"));
    }

    public OsobniPodaci create(OsobniPodaci osobniPodaci) {
        if (osobniPodaci == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Osobni podaci ne smiju biti null!");
        }
        if (osobniPodaci.getOib() == null || osobniPodaci.getOib().trim().length() != 11) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OIB mora imati točno 11 znamenki!");
        }
        if (osobniPodaciRepository.existsById(osobniPodaci.getOib())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Osoba s OIB-om " + osobniPodaci.getOib() + " već postoji!");
        }
        if (osobniPodaci.getUloga() == null) {
            Uloga ulogaKorisnik = ulogaRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Uloga ROLE_USER nije pronađena!"));
                osobniPodaci.setUloga(ulogaKorisnik);
        }
        if (osobniPodaci.getLozinka() == null || osobniPodaci.getLozinka().isEmpty()) {
            osobniPodaci.setLozinka("privremena123"); 
        }
        return osobniPodaciRepository.save(osobniPodaci);
    }


    @Transactional
    public OsobniPodaci spremiSve(RegistracijaDTO dto, MultipartFile f1, MultipartFile f2, MultipartFile f3, MultipartFile f4) throws IOException {
        
        if (osobniPodaciRepository.existsByKorisnickoIme(dto.korisnickoIme)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Korisničko ime '" + dto.korisnickoIme + "' je zauzeto!");
        }

        OsobniPodaci osoba = new OsobniPodaci();
        osoba.setOib(dto.oib);
        osoba.setIme(dto.ime);
        osoba.setPrezime(dto.prezime);
        osoba.setDatum_rodenja(dto.datum_rodenja); 
        osoba.setAdresa(dto.adresa);
        osoba.setEmail(dto.email);
        osoba.setBroj_telefona(dto.brojTelefona);
        osoba.setKorisnickoIme(dto.korisnickoIme);
        osoba.setLozinka(dto.lozinka);

        Uloga ulogaKorisnik = ulogaRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Uloga s ID 1 nije pronađena!"));
        osoba.setUloga(ulogaKorisnik);

        List<Dokument> listaDokumenata = new ArrayList<>();
        MultipartFile[] files = {f1, f2, f3, f4};
        int[] vrsteIds = {1, 3, 4, 5}; 

        for (int idx = 0; idx < files.length; idx++) {
            final int i = idx;
            if (files[i] != null && !files[i].isEmpty()) {
                Dokument d = new Dokument();
                d.setNazivDokumenta(files[i].getOriginalFilename());
                d.setOsobniPodaci(osoba);

                FilesBlob fb = new FilesBlob();
                fb.setDokument(files[i].getBytes()); 
                d.setFilesBlob(fb);

                VrstaDokumenta vd = vrstaDokumentaRepository.findById(vrsteIds[i])
                        .orElseThrow(() -> new RuntimeException("Vrsta dokumenta ID " + vrsteIds[i] + " ne postoji!"));
                d.setVrstaDokumenta(vd);

                listaDokumenata.add(d);
            }
        }
        osoba.setDokumenti(listaDokumenata);
        return osobniPodaciRepository.save(osoba);
    }

    public List<OsobniPodaci> dohvatiSveIliSamoSvoje(Authentication auth) {
        if (auth == null) return Collections.emptyList();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return osobniPodaciRepository.findAll(); 
        } else {
            return osobniPodaciRepository.findByKorisnickoIme(auth.getName()).map(List::of).orElse(Collections.emptyList());
        }
    }

    public Page<OsobniPodaci> dohvatiStraniceno(Authentication auth, int stranica) {
        Pageable pageable = PageRequest.of(stranica, 10);
        if (auth == null) return Page.empty();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return osobniPodaciRepository.findAll(pageable);
        } else {
            return osobniPodaciRepository.findByKorisnickoIme(auth.getName())
                    .map(o -> new PageImpl<>(List.of(o), pageable, 1))
                    .orElse(new PageImpl<>(Collections.emptyList(), pageable, 0));
        }
    }

   public OsobniPodaci azurirajKorisnika(String oib, OsobniPodaci noviPodaci) {
        OsobniPodaci postojeci = osobniPodaciRepository.findById(oib)
                .orElseThrow(() -> new RuntimeException("Korisnik s OIB-om " + oib + " ne postoji."));
        postojeci.setIme(noviPodaci.getIme());
        postojeci.setPrezime(noviPodaci.getPrezime());
        postojeci.setAdresa(noviPodaci.getAdresa());
        postojeci.setBroj_telefona(noviPodaci.getBroj_telefona()); 
        postojeci.setEmail(noviPodaci.getEmail());

        return osobniPodaciRepository.save(postojeci);
    }

    public OsobniPodaci findByOib(String oib) {
        return osobniPodaciRepository.findById(oib)
                .orElseThrow(() -> new RuntimeException("Korisnik s OIB-om " + oib + " ne postoji."));
    }

   
    @Transactional
    public void deleteById(String oib) {
        osobniPodaciRepository.findById(oib).ifPresent(osoba -> {
            osoba.setObrisan(true);
            osobniPodaciRepository.saveAndFlush(osoba);
        });
    }

    public List<OsobniPodaci> dohvatiSveObrisane() {
    return osobniPodaciRepository.dohvatiSveObrisaneNative();
    }

   @Transactional
    public void restoreById(String oib) {
        OsobniPodaci osoba = osobniPodaciRepository.findByOibIgnoreRestriction(oib) 
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Korisnik nije u arhivi"));

        osoba.setObrisan(false);
        osobniPodaciRepository.saveAndFlush(osoba);
    }
}