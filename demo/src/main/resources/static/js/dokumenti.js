let trenutnaStranica = 0;

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
        
        if (!response.ok) {
            throw new Error(`Status: ${response.status}`);
        }

        const podaci = await response.json();
        body.innerHTML = ''; 

        if (podaci.content && podaci.content.length > 0) {
            podaci.content.forEach(doc => {
                let idVrste = doc.idVrstaDokumenta;
                
                if (!idVrste && doc.vrstaDokumenta) {
                    idVrste = doc.vrstaDokumenta.idVrstaDokumenta;
                }

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
                            <button class="btn-preuzmi" onclick="preuzmiDokument(${doc.idDokument})">
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
        console.error("Greška pri dohvaćanju:", error);
        body.innerHTML = `<tr><td colspan="6" style="color:red">Greška pri učitavanju: ${error.message}</td></tr>`;
    }
}

function preuzmiDokument(id) {
    window.open(`/api/dokument/preuzmi/${id}`, '_blank');
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