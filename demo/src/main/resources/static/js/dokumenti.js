let trenutnaStranica = 0;

async function ucitajDokumente(stranica = 0) {
    trenutnaStranica = stranica; 
    const body = document.getElementById('dokumenti-body');
    body.innerHTML = '<tr><td colspan="5">Učitavanje...</td></tr>';

    try {
        const response = await fetch(`/api/dokument/moj-pregled?stranica=${stranica}`); 
        
        if (!response.ok) {
            throw new Error(`Status: ${response.status}`);
        }

        const podaci = await response.json();
        body.innerHTML = ''; 

        
        if (podaci.content && podaci.content.length > 0) {
            podaci.content.forEach(doc => {
                const oib = doc.osobniPodaciOib || (doc.osobniPodaci ? doc.osobniPodaci.oib : 'Nije dodijeljeno');

                const red = `
                    <tr>
                        <td>${doc.idDokument}</td>
                        <td>${doc.nazivDokumenta || 'Bez naziva'}</td>
                        <td style="font-weight:bold;">${oib}</td>
                        <td>${doc.datumKreiranja ? new Date(doc.datumKreiranja).toLocaleDateString('hr-HR') : '-'}</td>
                        <td><button class="btn-preuzmi">Preuzmi</button></td>
                    </tr>`;
                body.insertAdjacentHTML('beforeend', red);
            });
        } else {
            body.innerHTML = '<tr><td colspan="5">Nema pronađenih dokumenata.</td></tr>';
        }

        
        iscrtajPaginaciju(podaci.totalPages, podaci.number);

    } catch (error) {
        console.error("Greška:", error);
        body.innerHTML = `<tr><td colspan="5" style="color:red">Greška: ${error.message}</td></tr>`;
    }
}

function iscrtajPaginaciju(ukupnoStranica, trenutna) {
    const paginacijaDiv = document.getElementById('paginacijaDokumenti');
    if (!paginacijaDiv) return; 
    
    paginacijaDiv.innerHTML = ''; 

    
    const prevBtn = document.createElement('button');
    prevBtn.innerText = 'Prethodna';
    prevBtn.disabled = trenutna === 0;
    prevBtn.onclick = () => ucitajDokumente(trenutna - 1);
    paginacijaDiv.appendChild(prevBtn);

    
    const span = document.createElement('span');
    span.innerText = ` Stranica ${trenutna + 1} od ${ukupnoStranica} `;
    paginacijaDiv.appendChild(span);

    const nextBtn = document.createElement('button');
    nextBtn.innerText = 'Sljedeća';
    nextBtn.disabled = trenutna >= (ukupnoStranica - 1);
    nextBtn.onclick = () => ucitajDokumente(trenutna + 1);
    paginacijaDiv.appendChild(nextBtn);
}

document.addEventListener('DOMContentLoaded', () => ucitajDokumente(0));