async function ucitajOsobnePodatke() {
    try {
        // PROMIJENJENO: dodali smo /moj-pregled na kraj URL-a
        const response = await fetch('/api/osobni-podaci/moj-pregled'); 
        
        if (!response.ok) throw new Error('Problem s prijavom ili serverom');
        
        const podaci = await response.json();
        const tbody = document.getElementById('osobniPodaciBody');
        
        if (!tbody) return; // Mala zaštita ako tablica nije na toj stranici
        
        tbody.innerHTML = ''; 

        podaci.forEach(osoba => {
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
                </tr>
            `;
            tbody.innerHTML += red;
        });
    } catch (error) {
        console.error('Greška:', error);
        alert('Greška pri dohvaćanju podataka. Jeste li ulogirani?');
    }
}
ucitajOsobnePodatke();