let trenutnaStranica = 0;
let trenutnaOsoba = {};

async function ucitajOsobnePodatke(stranica = 0) {
    try {
        trenutnaStranica = stranica;

        const response = await fetch(`/api/osobni-podaci/moj-pregled?stranica=${stranica}`);
        if (!response.ok) throw new Error('Problem s dohvatom podataka');

        const data = await response.json();
        const tbody = document.getElementById('osobniPodaciBody');
        if (!tbody) return;

        tbody.innerHTML = ''; 

        data.content.forEach(osoba => {
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
                    <td><button onclick="otvoriUrediModal('${osoba.oib}')">Uredi</button></td>
                </tr>
            `;
            tbody.innerHTML += red;
        });

        osvjeziNavigaciju(data.totalPages, data.number);

    } catch (error) {
        console.error(error);
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
    console.log("1. Kliknut gumb za OIB:", oib); 
    
    try {
        const response = await fetch(`/api/osobni-podaci/${oib}`);
        console.log("2. Odgovor servera status:", response.status);

        if (!response.ok) throw new Error('Korisnik nije pronađen na serveru');

        const osoba = await response.json();
        console.log("3. Podaci stigli:", osoba);

        const modal = document.getElementById('editModal');
        if (!modal) {
            alert("GREŠKA: JavaScript ne vidi 'editModal'. Provjeri HTML id!");
            return;
        }
        trenutnaOsoba = osoba;
        if (document.getElementById('editOib')) document.getElementById('editOib').value = osoba.oib || '';
        if (document.getElementById('editIme')) document.getElementById('editIme').value = osoba.ime || '';
        if (document.getElementById('editPrezime')) document.getElementById('editPrezime').value = osoba.prezime || '';
        if (document.getElementById('editAdresa')) document.getElementById('editAdresa').value = osoba.adresa || '';
        if (document.getElementById('editTelefon')) document.getElementById('editTelefon').value = osoba.broj_telefona || '';
        if (document.getElementById('editEmail')) document.getElementById('editEmail').value = osoba.email || '';

        console.log("4. Pokušavam prikazati modal...");
        modal.style.display = 'block';
        modal.style.visibility = 'visible'; 
        modal.style.opacity = '1';

    } catch (error) {
        console.error("Kritična greška:", error);
        alert("Nešto je puklo: " + error.message);
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
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(podaci)
        });

        if (response.ok) {
            alert("Uspješno spremljeno!");
            location.reload();
        } else {
            const errorText = await response.text();
            alert("Server vratio grešku: " + errorText);
        }
    } catch (e) {
        console.error(e);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById('searchInput');
    
    if (searchInput) {
        searchInput.addEventListener('keyup', function() {
            const filter = searchInput.value.toLowerCase();
            const rows = document.querySelectorAll('#tablicaKorisnika tbody tr');

            rows.forEach(row => {
                const text = row.innerText.toLowerCase();
                if (text.includes(filter)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        });
        console.log("Filtriranje spremno!");
    } else {
        console.error("Nisam našao searchInput polje!");
    }
});

ucitajOsobnePodatke(0);