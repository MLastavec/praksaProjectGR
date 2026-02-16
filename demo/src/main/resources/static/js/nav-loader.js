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
});