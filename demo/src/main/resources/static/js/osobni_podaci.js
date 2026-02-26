let trenutnaStranica = 0;
let trenutnaOsoba = {};

async function ucitajOsobnePodatke(stranica = 0) {
    try {
        trenutnaStranica = stranica;

        const ulogaRes = await fetch('/api/osobni-podaci/trenutna-uloga');
        const uloga = await ulogaRes.text();

        const btnDodaj = document.querySelector('button[onclick="otvoriModalDodavanja()"]');
        if (btnDodaj) {
            btnDodaj.style.display = (uloga === 'ADMIN') ? 'inline-block' : 'none';
        }

        const response = await fetch(`/api/osobni-podaci/moj-pregled?stranica=${stranica}`);
        if (!response.ok) throw new Error('Problem s dohvatom podataka');

        const data = await response.json();
        const tbody = document.getElementById('osobniPodaciBody');
        if (!tbody) return;

        tbody.innerHTML = ''; 

        data.content.forEach(osoba => {
            let akcijeHtml = '—';
            if (uloga === 'ADMIN') {
                akcijeHtml = `
                    <button onclick="otvoriUrediModal('${osoba.oib}')">Uredi</button>
                    <button class="btn-obrisi" onclick="obrisiKorisnika('${osoba.oib}')">Obriši</button>
                `;
            }

            const red = `
                <tr>
                    <td><strong>${osoba.oib}</strong></td>
                    <td>${osoba.ime || ''}</td>
                    <td>${osoba.prezime || ''}</td>
                    <td>${osoba.datum_rodenja || '—'}</td>
                    <td>${osoba.adresa || ''}</td>
                    <td>${osoba.broj_telefona || ''}</td>
                    <td>${osoba.email || ''}</td>
                    <td>${osoba.korisnickoIme || ''}</td>
                    <td>${akcijeHtml}</td>
                </tr>
            `;
            tbody.innerHTML += red;
        });

        osvjeziNavigaciju(data.totalPages, data.number);

    } catch (error) {
        console.error("Greška pri učitavanju:", error);
    }
}

function osvjeziNavigaciju(ukupnoStranica, trenutna) {
    const navDiv = document.getElementById('paginacijaOsobni');
    if (!navDiv) return;

    navDiv.innerHTML = `
        <button onclick="ucitajOsobnePodatke(${trenutna - 1})" ${trenutna === 0 ? 'disabled' : ''}>Prethodna</button>
        <span>Stranica ${trenutna + 1} od ${ukupnoStranica}</span>
        <button onclick="ucitajOsobnePodatke(${trenutna + 1})" ${trenutna + 1 >= ukupnoStranica ? 'disabled' : ''}>Sljedeća</button>
    `;
}

async function otvoriUrediModal(oib) {
    try {
        const response = await fetch(`/api/osobni-podaci/${oib}`);
        if (!response.ok) throw new Error('Korisnik nije pronađen');

        const osoba = await response.json();
        const modal = document.getElementById('editModal');
        if (!modal) return;

        trenutnaOsoba = osoba;
        document.getElementById('editOib').value = osoba.oib || '';
        document.getElementById('editIme').value = osoba.ime || '';
        document.getElementById('editPrezime').value = osoba.prezime || '';
        document.getElementById('editAdresa').value = osoba.adresa || '';
        document.getElementById('editTelefon').value = osoba.broj_telefona || '';
        document.getElementById('editEmail').value = osoba.email || '';

        modal.style.display = 'block';
    } catch (error) {
        alert("Greška: " + error.message);
    }
}

async function spremiPromjene() {
    const oib = document.getElementById('editOib').value;
    const podaci = {
        oib: oib,
        ime: document.getElementById('editIme').value,
        prezime: document.getElementById('editPrezime').value,
        adresa: document.getElementById('editAdresa').value,
        broj_telefona: document.getElementById('editTelefon').value,
        email: document.getElementById('editEmail').value
    };

    try {
        const response = await fetch(`/api/osobni-podaci/${oib}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(podaci)
        });

        if (response.ok) {
            alert("Uspješno spremljeno!");
            location.reload();
        } else {
            alert("Greška pri spremanju.");
        }
    } catch (e) {
        console.error(e);
    }
}

async function obrisiKorisnika(oib) {
    if (confirm(`Jeste li sigurni da želite obrisati korisnika s OIB-om: ${oib}?`)) {
        const response = await fetch(`/api/osobni-podaci/${oib}`, { method: 'DELETE' });
        if (response.ok) {
            alert("Korisnik obrisan!");
            location.reload(); 
        } else {
            alert("Greška pri brisanju.");
        }
    }
}

function otvoriModalDodavanja() {
    document.getElementById('dodajKorisnikaModal').style.display = 'block';
    document.getElementById('modalOverlay').style.display = 'block';
}

function zatvoriModalDodavanja() {
    document.getElementById('dodajKorisnikaModal').style.display = 'none';
    document.getElementById('modalOverlay').style.display = 'none';
    document.getElementById('dodajKorisnikaForm').reset();
}


const formaDodaj = document.getElementById('dodajKorisnikaForm');
if (formaDodaj) {
    formaDodaj.addEventListener('submit', async function(e) {
        e.preventDefault();
        const noviKorisnik = {
            oib: document.getElementById('novoOib').value,
            ime: document.getElementById('novoIme').value,
            prezime: document.getElementById('novoPrezime').value,
            datum_rodenja: datumInput.value,
            adresa: document.getElementById('novoAdresa').value,
            broj_telefona: document.getElementById('novoTelefon').value,
            email: document.getElementById('novoEmail').value,
            korisnickoIme: document.getElementById('novoKorisnickoIme').value
        };

        try {
            const response = await fetch('/api/osobni-podaci', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(noviKorisnik)
            });

            if (response.ok) {
                alert("Korisnik uspješno dodan!");
                zatvoriModalDodavanja();
                ucitajOsobnePodatke(); 
            } else {
                const msg = await response.text();
                alert("Greška: " + msg);
            }
        } catch (err) {
            console.error(err);
        }
    });
}


document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById('searchInput');
    if (searchInput) {
        searchInput.addEventListener('keyup', function() {
            const filter = searchInput.value.toLowerCase();
            const rows = document.querySelectorAll('#tablicaKorisnika tbody tr');
            rows.forEach(row => {
                row.style.display = row.innerText.toLowerCase().includes(filter) ? '' : 'none';
            });
        });
    }
});

ucitajOsobnePodatke(0);