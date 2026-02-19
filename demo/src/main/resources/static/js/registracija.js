document.getElementById('regForm').addEventListener('submit', async function(e) {
    e.preventDefault(); 
    
    const formData = new FormData();

    formData.append('oib', document.getElementById('oib').value);
    formData.append('ime', document.getElementById('ime').value);
    formData.append('prezime', document.getElementById('prezime').value);
    formData.append('datum_rodenja', document.getElementById('datum_rodenja').value);
    formData.append('adresa', document.getElementById('adresa').value);
    formData.append('email', document.getElementById('email').value);
    formData.append('brojTelefona', document.getElementById('brojTelefona').value);
    formData.append('korisnickoIme', document.getElementById('korisnickoIme').value);
    formData.append('lozinka', document.getElementById('lozinka').value);

    formData.append('fileOsobna', document.getElementById('dokument_osobna').files[0]);
    formData.append('fileBoraviste', document.getElementById('dokument_boraviste').files[0]);
    formData.append('fileDrzavljanstvo', document.getElementById('dokument_drzavljanstvo').files[0]);
    formData.append('fileTransakcije', document.getElementById('dokument_transakcije').files[0]);

    try {
        const response = await fetch('/api/registracija/registracija', {
            method: 'POST',
            body: formData 
        });

        if (response.ok) {
            alert('Registracija uspješna! Možete se prijaviti.');
            window.location.replace('/prijava');
        } else {
            const errorText = await response.text();
            alert('Greška kod registracije: ' + errorText);
        }
    } catch (error) {
        console.error('Greška:', error);
        alert('Došlo je do pogreške prilikom povezivanja sa serverom.');
    }
});