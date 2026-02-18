let trenutnaStranica = 0; 

async function ucitajOsobnePodatke(stranica = 0) {
    try {
        trenutnaStranica = stranica;
        
        const response = await fetch(`/api/osobni-podaci/moj-pregled?stranica=${stranica}`); 
        
        if (!response.ok) throw new Error('Problem s prijavom ili serverom');
        
        const data = await response.json();
        const tbody = document.getElementById('osobniPodaciBody');
        
        if (!tbody) return;
        
        tbody.innerHTML = ''; 

        
        data.content.forEach(osoba => {
            const red = `
                <tr>
                    <td><strong>${osoba.oib}</strong></td>
                    <td>${osoba.ime}</td>
                    <td>${osoba.prezime}</td>
                    <td>${osoba.datum_rodenja || '—'}</td>
                    <td>${osoba.adresa}</td>
                    <td>${osoba.broj_telefona}</td>
                    <td>${osoba.email}</td>
                    <td>${osoba.korisnicko_ime}</td>
                    <td>${osoba.datumKreiranja ? new Date(osoba.datumKreiranja).toLocaleDateString('hr-HR') : '—'}</td>
                </tr>r
            `;
            tbody.innerHTML += red;
        });

        
        osvjeziNavigaciju(data.totalPages, data.number);

    } catch (error) {
        console.error('Greška:', error);
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

ucitajOsobnePodatke(0);