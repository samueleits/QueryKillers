// Aggiungi queste variabili all'inizio del tuo script
const openFilterModalBtn = document.getElementById('openFilterModalBtn');
const filterModal = new bootstrap.Modal(document.getElementById('filterModal')); // Se usi Bootstrap
const applyFiltersBtn = document.getElementById('applyFiltersBtn');
const filterTitleInput = document.getElementById('filterTitle');
const filterAuthorInput = document.getElementById('filterAuthor');
const filterGenreInput = document.getElementById('filterGenre');

// Aggiungi un listener per aprire il modal
openFilterModalBtn.addEventListener('click', function() {
    filterModal.show(); // Mostra il modal
});

// Aggiungi un listener per applicare i filtri
applyFiltersBtn.addEventListener('click', function() {
    const titleQuery = filterTitleInput.value.toLowerCase();
    const authorQuery = filterAuthorInput.value.toLowerCase();
    const genreQuery = filterGenreInput.value.toLowerCase();

    // Filtra la lista completa dei libri (allLibri)
    const filteredResults = allLibri.filter(libro => {
        const matchesTitle = titleQuery === '' || (libro.title && libro.title.toLowerCase().includes(titleQuery));
        const matchesAuthor = authorQuery === '' || (libro.author && libro.author.toLowerCase().includes(authorQuery));
        const matchesGenre = genreQuery === '' || (libro.genre && libro.genre.toLowerCase().includes(genreQuery));
        
        return matchesTitle && matchesAuthor && matchesGenre;
    });

    // Ora devi visualizzare questi filteredResults.
    // Ci sono due modi:
    // 1. Re-renderizzare la griglia dei libri esistente (il div class="books-grid").
    // 2. Reindirizzare a una pagina con i risultati filtrati dal backend (se preferisci un approccio server-side per il filtraggio).

    // OPZIONE 1 (Filtraggio Frontend): Rerenderizza la griglia
    const booksGrid = document.querySelector('.books-grid'); // Seleziona il tuo contenitore attuale dei libri
    booksGrid.innerHTML = ''; // Pulisci la griglia attuale

    if (filteredResults.length > 0) {
        filteredResults.forEach(libro => {
            const bookCard = document.createElement('div');
            bookCard.className = 'book-card';
            // Ricrea la card del libro come nel tuo Thymeleaf
            bookCard.innerHTML = `
                <img src="${libro.cover || 'https://via.placeholder.com/150x200?text=No+Img'}" alt="${libro.title}"
                     onerror="this.onerror=null;this.src='https://via.placeholder.com/150x200?text=No+Img';">
                <div class="book-info">
                    <h4 class="book-title">${libro.title}</h4>
                    <p class="book-author">${libro.author}</p>
                    <p class="book-description">${libro.description || ''}</p>
                </div>
            `;
            booksGrid.appendChild(bookCard);
        });
    } else {
        booksGrid.innerHTML = '<p class="text-center">Nessun libro trovato con i filtri applicati.</p>';
    }

    filterModal.hide(); // Nascondi il modal dopo l'applicazione dei filtri
});