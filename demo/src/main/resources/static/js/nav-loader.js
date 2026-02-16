document.addEventListener("DOMContentLoaded", function() {
    fetch('/html/navigacija.html') 
        .then(response => {
            if (!response.ok) throw new Error("Navigacija nije pronađena");
            return response.text();
        })
        .then(data => {
            document.body.insertAdjacentHTML('afterbegin', data);
        })
        .catch(err => console.error("Greška:", err));
});