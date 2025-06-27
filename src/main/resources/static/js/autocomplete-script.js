// File: src/main/resources/static/js/autocomplete-script.js

// Prende i dati dalla variabile globale definita nell'HTML da Thymeleaf
const allLibri = window.allLibriData; // <--- QUESTA È LA RIGA CORRETTA

const bookSearchInput = document.getElementById('bookSearchInput');
const autocompleteResults = document.getElementById('autocomplete-results');
let debounceTimeout;

bookSearchInput.addEventListener('input', function() {
    clearTimeout(debounceTimeout);
    const query = this.value.toLowerCase();

    if (query.length < 2) {
        autocompleteResults.innerHTML = '';
        autocompleteResults.style.display = 'none';
        return;
    }

    debounceTimeout = setTimeout(() => {
        const filteredLibri = allLibri.filter(libro => {
            // Assicurati che 'libro.title' sia valido prima di chiamare .toLowerCase()
            return libro && libro.title && libro.title.toLowerCase().includes(query);
        });

        autocompleteResults.innerHTML = '';
        if (filteredLibri.length > 0) {
            filteredLibri.forEach(libro => {
                const div = document.createElement('div');
                div.style.display = 'flex';
                div.style.alignItems = 'center';

                const img = document.createElement('img');
                img.className = 'book-thumbnail';
                // Usiamo libro.cover qui, assumendo che l'entità abbia 'cover' e non 'copertina'
                img.src = (libro.cover && libro.cover.trim() !== '') ? libro.cover : 'https://via.placeholder.com/50x70?text=No+Img';
                img.alt = 'Copertina';
                img.onerror = function() {
                    this.onerror = null;
                    this.src = 'https://via.placeholder.com/50x70?text=No+Img';
                };
                div.appendChild(img);

                const textDiv = document.createElement('div');
                textDiv.className = 'book-details';
                textDiv.textContent = `${libro.title} (Autore: ${libro.author})`;
                div.appendChild(textDiv);

                div.dataset.libroId = libro.id;

                div.addEventListener('click', function() {
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

document.addEventListener('click', function(event) {
    if (!bookSearchInput.contains(event.target) && !autocompleteResults.contains(event.target)) {
        autocompleteResults.style.display = 'none';
    }
});

console.log(allLibriData);
console.log("Autocomplete script loaded");
