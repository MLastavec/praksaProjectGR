package com.example.demo.service;

import com.example.demo.dto.DokumentDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

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
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST, "Osobni podaci ne smiju biti null!");
        }
        if (osobniPodaci.getOib() == null || osobniPodaci.getOib().trim().isEmpty()) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST, "OIB ne smije biti prazan!");
        }
        if (osobniPodaci.getOib().trim().length() != 11) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST, "OIB mora imati točno 11 znamenki!");
        }
        if (osobniPodaciRepository.existsById(osobniPodaci.getOib())) {
        throw new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.CONFLICT, "Osoba s OIB-om " + osobniPodaci.getOib() + " već postoji!");
        }
        if (osobniPodaci.getEmail() == null || !osobniPodaci.getEmail().contains("@")) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email adresa nije ispravna!");
        }
        
        return osobniPodaciRepository.save(osobniPodaci);
    }


    public OsobniPodaci delete(String oib) {
       if (!osobniPodaciRepository.existsById(oib)) {
        throw new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.NOT_FOUND, 
            "Brisanje neuspješno: Osoba s OIB-om " + oib + " ne postoji u bazi!"
        );
    }
        osobniPodaciRepository.deleteById(oib);
        return null;
}

   @Transactional
    public OsobniPodaci spremiKompletnuRegistraciju(RegistracijaDTO dto) {

        if (osobniPodaciRepository.existsByKorisnickoIme(dto.korisnickoIme)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, 
                "Korisničko ime '" + dto.korisnickoIme + "' je već zauzeto!"
            );
        }

        String lozinka = dto.lozinka;
        if (lozinka.length() < 6) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Lozinka mora imati barem 6 znakova!"
            );
        }
        if (!lozinka.matches(".*[0-9].*")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Lozinka mora sadržavati barem jednu brojku!"
            );
        }
        if (!lozinka.matches(".*[A-Z].*")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Lozinka mora sadržavati barem jedno veliko slovo!"
            );
        }

        OsobniPodaci osoba = new OsobniPodaci();
        osoba.setOib(dto.oib);
        osoba.setIme(dto.ime);
        osoba.setPrezime(dto.prezime);
        osoba.setDatum_rodenja(dto.datum_rodenja); 
        osoba.setAdresa(dto.adresa);
        osoba.setEmail(dto.email);
        osoba.setBroj_telefona(dto.brojTelefona);
        osoba.setKorisnicko_ime(dto.korisnickoIme);
        osoba.setLozinka(dto.lozinka);

        Uloga ulogaKorisnik = ulogaRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Greška: Uloga s ID 1 nije pronađena!"));
        osoba.setUloga(ulogaKorisnik);

        if (dto.dokumenti != null && !dto.dokumenti.isEmpty()) {
            List<Dokument> listaDokumenata = new ArrayList<>();
            
            for (DokumentDTO dDto : dto.dokumenti) {
                Dokument d = new Dokument();
                d.setNazivDokumenta(dDto.nazivDokumenta);
                d.setOsobniPodaci(osoba); 

                if (dDto.filesBlob != null && !dDto.filesBlob.isEmpty()) {
                    FilesBlob fb = new FilesBlob();
                    fb.setDokument(dDto.filesBlob.getBytes()); 
                    d.setFilesBlob(fb);
                }

                VrstaDokumenta vd = vrstaDokumentaRepository.findById(dDto.idVrstaDokumenta)
                        .orElseThrow(() -> new RuntimeException("Vrsta dokumenta ne postoji!"));
                d.setVrstaDokumenta(vd);

                listaDokumenata.add(d);
            }
            osoba.setDokumenti(listaDokumenata);
        }

        return osobniPodaciRepository.save(osoba);
    }


}
