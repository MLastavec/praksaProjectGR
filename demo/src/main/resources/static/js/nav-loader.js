document.addEventListener("DOMContentLoaded", function() {
    fetch('/html/navigacija.html') 
        .then(res => res.text())
        .then(data => {
            document.body.insertAdjacentHTML('afterbegin', data);

            fetch('/api/dokument') 
                .then(response => {
                    const guestLinks = document.querySelectorAll('.guest-link');
                    const authLinks = document.querySelectorAll('.auth-link');

                    if (response.ok) {
                        
                        guestLinks.forEach(el => el.style.display = 'none');
                        authLinks.forEach(el => el.style.display = 'inline-block');
                    } else {
                        
                        guestLinks.forEach(el => el.style.display = 'inline-block');
                        authLinks.forEach(el => el.style.display = 'none');
                    }
                })
                .catch(() => {
                    
                    document.querySelectorAll('.auth-link').forEach(el => el.style.display = 'none');
                });
        });

        
    fetch('/api/korisnik')
    .then(res => {
        if (res.ok) {
            return res.text(); 
        }
        return null; 
    })
    .then(ime => {
        const header = document.getElementById('welcome-header');
        if (header) {
            if (ime && ime !== "Gost") {
                header.innerText = "Dobrodošli, " + ime + "!";
            } else {
                header.innerText = "Dobrodošli!"; 
            }
        }
    })
    .catch(err => {
        console.log("Niste prijavljeni.");
        document.getElementById('welcome-header').innerText = "Dobrodošli!";
    });
});