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
        const response = await fetch(`/api/dokument/moj-pregled?stranica=${stranica}`);
        if (!response.ok) throw new Error(`Status: ${response.status}`);

        const podaci = await response.json();
        sviDokumenti = podaci.content; 
        
        body.innerHTML = ''; 

        if (sviDokumenti && sviDokumenti.length > 0) {
            sviDokumenti.forEach((doc, index) => { 
                let idVrste = doc.idVrstaDokumenta || (doc.vrstaDokumenta ? doc.vrstaDokumenta.idVrstaDokumenta : null);
                const nazivVrste = VRSTE_MAP[idVrste] || `Ostalo/Nepoznato (ID: ${idVrste})`;
                const oib = doc.osobniPodaciOib || (doc.osobniPodaci ? doc.osobniPodaci.oib : 'Nije dodijeljeno');
                const datum = doc.datumKreiranja ? new Date(doc.datumKreiranja).toLocaleDateString('hr-HR') : '-';

                const red = `
                    <tr>
                        <td>${doc.idDokument}</td>
                        <td><strong>${nazivVrste}</strong></td>
                        <td title="${doc.nazivDokumenta}" style="max-width: 150px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                            ${doc.nazivDokumenta || 'Bez naziva'}
                        </td>
                        <td style="font-weight:bold;">${oib}</td>
                        <td>${datum}</td>
                        <td>
                            <button class="btn-preuzmi" onclick="pokreniPreuzimanje(${index})">
                                Preuzmi
                            </button>
                        </td>
                    </tr>`;
                body.insertAdjacentHTML('beforeend', red);
            });
        } else {
            body.innerHTML = '<tr><td colspan="6">Nema pronađenih dokumenata.</td></tr>';
        }
        iscrtajPaginaciju(podaci.totalPages, podaci.number);
    } catch (error) {
        console.error("Greška:", error);
        body.innerHTML = `<tr><td colspan="6" style="color:red">Greška: ${error.message}</td></tr>`;
    }
}

function pokreniPreuzimanje(index) {
    const doc = sviDokumenti[index];
    const base64Str = (doc.filesBlob && doc.filesBlob.dokument) ? doc.filesBlob.dokument : doc.filesBlob;
    
    preuzmiDokument(base64Str, doc.nazivDokumenta);
}

function preuzmiDokument(base64Data, nazivDatoteke) {
    if (!base64Data) {
        alert("Podaci o datoteci su prazni ili nepostojeći.");
        return;
    }

    try {
        let finalBase64 = String(base64Data).trim();
        let mimeType = "application/pdf";

        try {
            window.atob(finalBase64); 
        } catch (e) {
            console.warn("Podatak nije Base64, enkodiram...");
            finalBase64 = btoa(finalBase64);
            mimeType = "text/plain";
        }

        if (mimeType !== "text/plain") {
            if (finalBase64.startsWith("JVBERi")) mimeType = "application/pdf";
            else if (finalBase64.startsWith("iVBORw")) mimeType = "image/png";
            else if (finalBase64.startsWith("/9j/")) mimeType = "image/jpeg";
        }

        const linkSource = `data:${mimeType};base64,${finalBase64}`;
        const downloadLink = document.createElement("a");
        let ext = mimeType.split("/")[1].replace("plain", "txt").replace("jpeg", "jpg");
        let ime = nazivDatoteke ? nazivDatoteke.split('.')[0] : "dokument";
        
        downloadLink.href = linkSource;
        downloadLink.download = `${ime}.${ext}`;
        document.body.appendChild(downloadLink);
        downloadLink.click();
        document.body.removeChild(downloadLink);

    } catch (err) {
        console.error("Kritična greška:", err);
        alert("Greška pri generiranju datoteke.");
    }
}

function iscrtajPaginaciju(ukupnoStranica, trenutna) {
    const paginacijaDiv = document.getElementById('paginacijaDokumenti');
    if (!paginacijaDiv) return;
    
    paginacijaDiv.innerHTML = '';

    const prevBtn = document.createElement('button');
    prevBtn.innerText = 'Prethodna';
    prevBtn.className = 'btn-paginacija';
    prevBtn.disabled = (trenutna === 0);
    prevBtn.onclick = () => ucitajDokumente(trenutna - 1);
    paginacijaDiv.appendChild(prevBtn);

    const span = document.createElement('span');
    span.style.margin = "0 15px";
    span.innerText = ` Stranica ${trenutna + 1} od ${ukupnoStranica} `;
    paginacijaDiv.appendChild(span);

    const nextBtn = document.createElement('button');
    nextBtn.innerText = 'Sljedeća';
    nextBtn.className = 'btn-paginacija';
    nextBtn.disabled = (trenutna >= (ukupnoStranica - 1));
    nextBtn.onclick = () => ucitajDokumente(trenutna + 1);
    paginacijaDiv.appendChild(nextBtn);
}

document.addEventListener('DOMContentLoaded', () => ucitajDokumente(0));