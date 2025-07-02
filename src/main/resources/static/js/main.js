// Questo file conterrà script JavaScript che sono validi per tutte le pagine del sito.

// Funzionalità per il dropdown della lingua
document.addEventListener('DOMContentLoaded', () => {
    const languageDropdown = document.querySelector('.language-dropdown');
    const currentLanguage = document.querySelector('.current-language');
    const languageOptions = document.querySelector('.language-options');

    // Toggle la visibilità del dropdown quando si clicca sull'elemento corrente della lingua
    if (currentLanguage && languageOptions) {
        currentLanguage.addEventListener('click', () => {
            languageDropdown.classList.toggle('active');
        });

        // Chiudi il dropdown se si clicca al di fuori di esso
        document.addEventListener('click', (event) => {
            if (!languageDropdown.contains(event.target)) {
                languageDropdown.classList.remove('active');
            }
        });

        // Gestisce la selezione di una lingua (per ora solo console.log)
        languageOptions.querySelectorAll('a').forEach(option => {
            option.addEventListener('click', (event) => {
                event.preventDefault(); // Impedisce il ricaricamento della pagina
                const selectedLang = option.dataset.lang;
                const selectedText = option.textContent;

                // Aggiorna il testo visualizzato nel dropdown
                currentLanguage.innerHTML = `${selectedText} <i class="fas fa-caret-down"></i>`;

                console.log(`Lingua selezionata: ${selectedLang}`);
                // In futuro, qui si potrà implementare la logica per cambiare la lingua effettiva del sito
                languageDropdown.classList.remove('active'); // Chiudi il dropdown dopo la selezione
            });
        });
    }

    // Se l'utente torna alla pagina dopo un login/logout, refresh dell'header
    window.addEventListener('storage', (event) => {
        if (event.key === 'isLoggedIn' || event.key === 'username') {
            updateHeaderForLoginStatus();
        }
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const profileMenuToggle = document.querySelector('.profile-menu-toggle');
    const hamburgerMenuContent = document.querySelector('.hamburger-menu-content');

    if (profileMenuToggle && hamburgerMenuContent) {
        // Function to toggle menu visibility
        const toggleMenu = () => {
            hamburgerMenuContent.classList.toggle('show');
        };

        // Event listener to open/close menu when profile icon is clicked
        profileMenuToggle.addEventListener('click', (event) => {
            event.stopPropagation(); // Prevent click from bubbling up to document
            toggleMenu();
        });

        // Event listener to close menu when clicking outside of it
        document.addEventListener('click', (event) => {
            if (!hamburgerMenuContent.contains(event.target) && !profileMenuToggle.contains(event.target)) {
                if (hamburgerMenuContent.classList.contains('show')) {
                    hamburgerMenuContent.classList.remove('show');
                }
            }
        });

        // Optional: Close menu when a menu item is clicked
        hamburgerMenuContent.querySelectorAll('.menu-item').forEach(item => {
            item.addEventListener('click', () => {
                if (hamburgerMenuContent.classList.contains('show')) {
                    hamburgerMenuContent.classList.remove('show');
                }
            });
        });
    }
});