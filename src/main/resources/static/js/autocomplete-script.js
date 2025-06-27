// File: static/js/autocomplete-script.js

// Recupera la lista di tutti i libri (iniettata da Thymeleaf)
const allLibri = window.allLibriData;

const bookSearchInput = document.getElementById('bookSearchInput');
const autocompleteResults = document.getElementById('autocomplete-results');
let debounceTimeout;

// AUTOCOMPLETE: Ricerca dinamica tra tutti i libri
bookSearchInput.addEventListener('input', function () {
    clearTimeout(debounceTimeout);
    const query = this.value.toLowerCase();

    if (query.length < 2) {
        autocompleteResults.innerHTML = '';
        autocompleteResults.style.display = 'none';
        return;
    }

    debounceTimeout = setTimeout(() => {
        const filteredLibri = allLibri.filter(libro =>
            libro && libro.title && libro.title.toLowerCase().includes(query)
        );

        autocompleteResults.innerHTML = '';
        if (filteredLibri.length > 0) {
            filteredLibri.forEach(libro => {
                const div = document.createElement('div');
                div.style.display = 'flex';
                div.style.alignItems = 'center';

                const img = document.createElement('img');
                img.className = 'book-thumbnail';
                img.src = (libro.cover && libro.cover.trim() !== '') 
                    ? libro.cover 
                    : 'https://via.placeholder.com/50x70?text=No+Img';
                img.alt = 'Copertina';
                img.onerror = () => img.src = 'https://via.placeholder.com/50x70?text=No+Img';
                div.appendChild(img);

                const textDiv = document.createElement('div');
                textDiv.className = 'book-details';
                textDiv.textContent = `${libro.title} (Autore: ${libro.author})`;
                div.appendChild(textDiv);

                div.dataset.libroId = libro.id;

                div.addEventListener('click', () => {
                    bookSearchInput.value = libro.title;
                    autocompleteResults.innerHTML = '';
                    autocompleteResults.style.display = 'none';
                });

                autocompleteResults.appendChild(div);
            });
            autocompleteResults.style.display = 'block';
        } else {
            autocompleteResults.style.display = 'none';
        }
    }, 300);
});

// Chiudi suggerimenti se clicchi fuori
document.addEventListener('click', function (event) {
    if (!bookSearchInput.contains(event.target) && !autocompleteResults.contains(event.target)) {
        autocompleteResults.style.display = 'none';
    }
});

// TOGGLE LETTO: cambia lo stato "letto" del libro (POST)
function toggleLetto(button) {
    const bookId = button.getAttribute("data-book-id");

    const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch('/toggle-read', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            [header]: token
        },
        body: `bookId=${bookId}`
    })
    .then(res => res.text())
    .then(data => {
        alert(data); // Puoi migliorare: cambia testo/stile bottone
    })
    .catch(err => {
        alert("Errore durante il toggle dello stato di lettura: " + err);
    });
}
