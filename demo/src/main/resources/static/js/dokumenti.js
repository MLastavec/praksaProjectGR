let trenutnaStranica = 0;
let sviDokumenti = [];

const VRSTE_MAP = {
    1: 'Preslika osobne iskaznice',
    3: 'Dokaz o boravištu',
    4: 'Dokaz o državljanstvu',
    5: 'Izvod transkacija za posljednji mjesec'
};

async function ucitajDokumente(stranica = 0) {
    trenutnaStranica = stranica;
    const body = document.getElementById('dokumenti-body');
    
    if (!body) return;
    body.innerHTML = '<tr><td colspan="6">Učitavanje...</td></tr>';

    try {
        const ulogaRes = await fetch('/api/osobni-podaci/trenutna-uloga');
        const uloga = await ulogaRes.text();

        const response = await fetch(`/api/dokument/moj-pregled?stranica=${stranica}`);
        if (!response.ok) throw new Error(`Status: ${response.status}`);

        const podaci = await response.json();
        sviDokumenti = podaci.content; 
        
        body.innerHTML = ''; 

        if (sviDokumenti && sviDokumenti.length > 0) {
            sviDokumenti.forEach((doc, index) => { 
                let idVrste = doc.idVrstaDokumenta || (doc.vrstaDokumenta ? doc.vrstaDokumenta.idVrstaDokumenta : null);
                const nazivVrste = VRSTE_MAP[idVrste] || `Ostalo (ID: ${idVrste})`;
                
                const oib = doc.osobniPodaciOib || (doc.osobniPodaci ? doc.osobniPodaci.oib : 'Nije dodijeljeno');
  
                let ime = doc.ime || (doc.osobniPodaci ? doc.osobniPodaci.ime : '');
                let prezime = doc.prezime || (doc.osobniPodaci ? doc.osobniPodaci.prezime : '');
                const puniNazivVlasnika = ime ? `${ime} ${prezime}` : 'Nepoznato';
                
                const datum = doc.datumKreiranja ? new Date(doc.datumKreiranja).toLocaleDateString('hr-HR') : '-';

                let akcijeHtml = `<button class="btn-preuzmi" onclick="pokreniPreuzimanje(${index})">Preuzmi</button>`;
                if (uloga === 'ADMIN') {
                    akcijeHtml += ` <button class="btn-obrisi" onclick="obrisiDokument(${doc.idDokument})">Obriši</button>`;
                }

                const red = `
                    <tr>
                        <td>${doc.idDokument}</td>
                        <td><strong>${nazivVrste}</strong></td>
                        <td title="${doc.nazivDokumenta}" style="max-width: 150px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                            ${doc.nazivDokumenta || 'Bez naziva'}
                        </td>
                        <td style="font-weight:bold;">${oib}</td>
                        <td>${puniNazivVlasnika}</td>
                        <td>${datum}</td>
                        <td>${akcijeHtml}</td>
                    </tr>`;
                body.insertAdjacentHTML('beforeend', red);
            });
        } else {
            body.innerHTML = '<tr><td colspan="7">Nema pronađenih dokumenata.</td></tr>';
        }
        iscrtajPaginaciju(podaci.totalPages, podaci.number);
    } catch (error) {
        console.error("Greška:", error);
        body.innerHTML = `<tr><td colspan="7" style="color:red">Greška: ${error.message}</td></tr>`;
    }
}

function pokreniPreuzimanje(index) {
    const doc = sviDokumenti[index];
    const base64Str = (doc.filesBlob && doc.filesBlob.dokument) ? doc.filesBlob.dokument : doc.filesBlob;
    preuzmiDokument(base64Str, doc.nazivDokumenta);
}

function preuzmiDokument(base64Data, nazivDatoteke) {
    if (!base64Data) {
        alert("Podaci o datoteci su prazni.");
        return;
    }
    try {
        let finalBase64 = String(base64Data).trim();
        let mimeType = "application/pdf";

        if (finalBase64.startsWith("JVBERi")) mimeType = "application/pdf";
        else if (finalBase64.startsWith("iVBORw")) mimeType = "image/png";
        else if (finalBase64.startsWith("/9j/")) mimeType = "image/jpeg";

        const linkSource = `data:${mimeType};base64,${finalBase64}`;
        const downloadLink = document.createElement("a");
        downloadLink.href = linkSource;
        downloadLink.download = nazivDatoteke || "dokument";
        downloadLink.click();
    } catch (err) {
        console.error("Greška:", err);
    }
}

function iscrtajPaginaciju(ukupnoStranica, trenutna) {
    const paginacijaDiv = document.getElementById('paginacijaDokumenti');
    if (!paginacijaDiv) return;
    
    paginacijaDiv.innerHTML = `
        <button onclick="ucitajDokumente(${trenutna - 1})" ${trenutna === 0 ? 'disabled' : ''}>Prethodna</button>
        <span style="margin: 0 15px;"> Stranica ${trenutna + 1} od ${ukupnoStranica} </span>
        <button onclick="ucitajDokumente(${trenutna + 1})" ${trenutna + 1 >= (ukupnoStranica) ? 'disabled' : ''}>Sljedeća</button>
    `;
}

document.addEventListener('DOMContentLoaded', function() {
    const inputDokumenti = document.getElementById('searchDokumenti');
    if (inputDokumenti) {
        inputDokumenti.addEventListener('keyup', function() {
            const filter = inputDokumenti.value.toLowerCase();
            const rows = document.querySelectorAll('#tablicaDokumenata tbody tr');
            rows.forEach(row => {
                row.style.display = row.innerText.toLowerCase().includes(filter) ? '' : 'none';
            });
        });
    }
});

async function obrisiDokument(id) {
    if (confirm("Jeste li sigurni da želite obrisati ovaj dokument?")) {
        try {
            const response = await fetch(`/api/dokument/${id}`, { method: 'DELETE' });
            if (response.ok) {
                alert("Dokument obrisan!");
                location.reload(); 
            } else {
                alert("Greška pri brisanju: " + response.status);
            }
        } catch (error) {
            console.error("Fetch error:", error);
        }
    }
}

document.addEventListener('DOMContentLoaded', () => ucitajDokumente(0));