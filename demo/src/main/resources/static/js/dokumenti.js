async function ucitajDokumente() {
    const body = document.getElementById('dokumenti-body');
    body.innerHTML = '<tr><td colspan="5">Učitavanje...</td></tr>';

    try {
        const response = await fetch('/api/dokument/moj-pregled'); 
        
        if (!response.ok) {
            throw new Error(`Status: ${response.status}`);
        }

        const podaci = await response.json();
        body.innerHTML = ''; 

        podaci.forEach(doc => {
            
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
    } catch (error) {
        console.error("Greška:", error);
        body.innerHTML = `<tr><td colspan="5" style="color:red">Greška: ${error.message} (Provjeri konzolu)</td></tr>`;
    }
}