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

                        fetch('/api/osobni-podaci/trenutna-uloga')
                            .then(res => res.text())
                            .then(uloga => {
                                const sviLinkovi = document.getElementsByTagName('a');
                                for (let link of sviLinkovi) {
                                    if (link.innerText.includes("Arhiva") || link.innerText.includes("ARHIVA")) {
                                        if (uloga === "ADMIN") {
                                            link.style.setProperty('display', 'inline-block', 'important');
                                            link.style.visibility = 'visible';
                                        } else {
                                            link.style.setProperty('display', 'none', 'important');
                                        }
                                    }
                                }
                            });
                    } else {
                        guestLinks.forEach(el => el.style.display = 'inline-block');
                        authLinks.forEach(el => el.style.display = 'none');
                    }
                });
        });

    fetch('/api/korisnik')
    .then(res => res.ok ? res.text() : null)
    .then(ime => {
        const header = document.getElementById('welcome-header');
        if (header) {
            header.innerText = (ime && ime !== "Gost") ? "Dobrodošli, " + ime + "!" : "Dobrodošli!";
        }
    })
    .catch(() => {
        if (document.getElementById('welcome-header')) {
            document.getElementById('welcome-header').innerText = "Dobrodošli!";
        }
    });
});