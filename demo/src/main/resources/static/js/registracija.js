document.getElementById('regForm').addEventListener('submit', async function(e) {
    e.preventDefault(); 

    const dto = {
        oib: document.getElementById('oib').value,
        ime: document.getElementById('ime').value,
        prezime: document.getElementById('prezime').value,
        datum_rodenja: document.getElementById('datum_rodenja').value,
        adresa: document.getElementById('adresa').value,
        email: document.getElementById('email').value,
        brojTelefona: document.getElementById('brojTelefona').value,
        korisnickoIme: document.getElementById('korisnickoIme').value,
        lozinka: document.getElementById('lozinka').value,
        dokumenti: [] 
    };

    try {
        const response = await fetch('/api/registracija/registracija', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dto)
        });

        if (response.ok) {
            alert('Uspjeh! Registracija je prošla. Sad se prijavi.');
            window.location.replace('/prijava');
        } else {
            
            const errorText = await response.text();
            alert('Greška kod registracije: ' + errorText);
        }
    } catch (error) {
        console.error('Greška:', error);
        alert('Ups! Nešto je pošlo po zlu. Provjeri je li server upaljen.');
    }
});