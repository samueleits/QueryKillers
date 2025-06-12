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

    // Gestione dello stato di login nell'header
    const userActionsDiv = document.querySelector('.user-actions');
    const loginButton = userActionsDiv ? userActionsDiv.querySelector('.login-button') : null;
    const currentLanguageSpan = userActionsDiv ? userActionsDiv.querySelector('.current-language') : null;

    function updateHeaderForLoginStatus() {
        // Verifica se l'utente è loggato (es. tramite localStorage)
        const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
        const username = localStorage.getItem('username'); // Recupera l'username salvato

        if (userActionsDiv) {
            // Rimuovi eventuali elementi preesistenti per evitare duplicati
            const existingProfileIcon = userActionsDiv.querySelector('.profile-icon');
            if (existingProfileIcon) {
                existingProfileIcon.remove();
            }

            if (isLoggedIn) {
                // Se loggato: nascondi il pulsante Login e mostra l'icona profilo
                if (loginButton) {
                    loginButton.style.display = 'none';
                }
                if (currentLanguageSpan) {
                     // Rimuovi l'icona caret-down se presente nel current-language (se non vuoi il dropdown)
                     const caretIcon = currentLanguageSpan.querySelector('.fa-caret-down');
                     if (caretIcon) {
                         caretIcon.remove();
                     }
                }

                // Crea e aggiungi l'icona profilo "DM"
                const profileIcon = document.createElement('a');
                profileIcon.href = 'profile.html'; // In futuro, punterà alla pagina profilo
                profileIcon.classList.add('profile-icon');
                profileIcon.textContent = username ? username.substring(0, 2).toUpperCase() : 'US'; // Prende le prime due lettere dell'username
                userActionsDiv.appendChild(profileIcon);

            } else {
                // Se non loggato: mostra il pulsante Login e assicurati che l'icona profilo non ci sia
                if (loginButton) {
                    loginButton.style.display = ''; // Reimposta a display predefinito (flex o inline-block)
                }
                if (currentLanguageSpan && !currentLanguageSpan.querySelector('.fa-caret-down')) {
                    // Aggiungi di nuovo il caret-down se era stato rimosso per il dropdown lingua
                    const caretIcon = document.createElement('i');
                    caretIcon.classList.add('fas', 'fa-caret-down');
                    currentLanguageSpan.appendChild(caretIcon);
                }
            }
        }
    }

    // Chiamata iniziale per impostare l'header allo stato di login/logout
    updateHeaderForLoginStatus();

    // Se l'utente torna alla pagina dopo un login/logout, refresh dell'header
    window.addEventListener('storage', (event) => {
        if (event.key === 'isLoggedIn' || event.key === 'username') {
            updateHeaderForLoginStatus();
        }
    });
});