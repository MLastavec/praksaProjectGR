async function ucitajSveArhive() {
    try {
        const resOsobe = await fetch('/api/osobni-podaci/podaci-arhive');
        if (resOsobe.ok) {
            const osobe = await resOsobe.json();
            popuniTablicuOsoba(osobe);
        }

        const resDok = await fetch('/api/dokument/arhiva');
        if (resDok.ok) {
            const dokumenti = await resDok.json();
            popuniTablicuDokumenata(dokumenti);
        } else {
            console.error("Greška pri dohvaćanju dokumenata:", resDok.status);
        }
    } catch (error) {
        console.error("Došlo je do greške:", error);
    }
}

function popuniTablicuOsoba(osobe) {
    const tablica = document.getElementById('arhivaTablica');
    if (!tablica) return;
    tablica.innerHTML = '';

    osobe.forEach(osoba => {
        tablica.innerHTML += `
            <tr>
                <td>${osoba.oib}</td>
                <td>${osoba.ime}</td>
                <td>${osoba.prezime}</td>
                <td>
                    <button onclick="vratiKorisnika('${osoba.oib}')" class="btn btn-success btn-sm">Vrati u sustav</button>
                </td>
            </tr>`;
    });
}

function popuniTablicuDokumenata(dokumenti) {
    const tablica = document.getElementById('arhivaDokumenataTablica');
    if (!tablica) return;
    tablica.innerHTML = '';

    if (dokumenti.length === 0) {
        tablica.innerHTML = '<tr><td colspan="3">Nema obrisanih dokumenata.</td></tr>';
        return;
    }

    dokumenti.forEach(dok => {
        const oibVlasnika = (dok.osobniPodaci && dok.osobniPodaci.oib) ? dok.osobniPodaci.oib : 'Nije dostupno';
        
        tablica.innerHTML += `
            <tr>
                <td>${dok.nazivDokumenta || 'Bez naziva'}</td>
                <td>${oibVlasnika}</td>
                <td>${dok.osobniPodaci ? dok.osobniPodaci.ime + ' ' + dok.osobniPodaci.prezime : 'Nije dostupno'}</td>
                <td>
                    <button onclick="vratiDokument(${dok.idDokument})" class="btn btn-primary btn-sm">Vrati Dokument</button>
                </td>
            </tr>`;
    });
}

async function vratiKorisnika(oib) {
    if (confirm(`Želite li vratiti korisnika s OIB-om ${oib}?`)) {
        const response = await fetch(`/api/osobni-podaci/${oib}/restore`, { method: 'POST' });
        if (response.ok) {
            alert("Korisnik je vraćen!");
            ucitajSveArhive();
        } else {
            alert("Greška pri vraćanju korisnika.");
        }
    }
}

async function vratiDokument(id) {
    if (confirm("Želite li vratiti ovaj dokument?")) {
        const response = await fetch(`/api/dokument/${id}/restore`, { method: 'POST' });

        if (response.ok) {
            alert("Dokument je vraćen!");
            ucitajSveArhive(); 
        } else {
            const errorText = await response.text();
            alert("Greška: " + errorText);
        }
    }
}

document.addEventListener('DOMContentLoaded', ucitajSveArhive);