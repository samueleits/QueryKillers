// Gestione del toggle tra form di Login e Registrazione e invio dati (simulato)
document.addEventListener('DOMContentLoaded', () => {
    const loginTab = document.getElementById('login-tab');
    const registerTab = document.getElementById('register-tab');
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');

    // Funzione per mostrare il form corretto e attivare il tab corrispondente
    function showForm(formToShow, tabToActivate) {
        loginForm.classList.remove('active');
        registerForm.classList.remove('active');
        loginTab.classList.remove('active');
        registerTab.classList.remove('active');

        formToShow.classList.add('active');
        tabToActivate.classList.add('active');
    }

    // Event listener per i click sui tab di login/registrazione
    if (loginTab && registerTab && loginForm && registerForm) {
        loginTab.addEventListener('click', () => showForm(loginForm, loginTab));
        registerTab.addEventListener('click', () => showForm(registerForm, registerTab));
    }

    // Gestione dell'invio del form di Login (simulazione)
    if (loginForm) {
        loginForm.addEventListener('submit', async (event) => {
            event.preventDefault(); // Impedisce il ricaricamento della pagina

            const identifier = document.getElementById('login-identifier').value;
            const password = document.getElementById('login-password').value;

            console.log('Tentativo di login con:', { identifier, password });

            // Simulazione di una chiamata API per il login
            // In un'applicazione reale, qui si farebbe una richiesta fetch al backend
            localStorage.setItem('isLoggedIn', 'true');
            // Salva l'username per visualizzarlo nell'header dopo il login
            localStorage.setItem('username', identifier.split('@')[0] || 'Utente');
            window.location.href = 'index.html'; // Reindirizza alla homepage
        });
    }

    // Gestione dell'invio del form di Registrazione (simulazione)
    if (registerForm) {
        registerForm.addEventListener('submit', async (event) => {
            event.preventDefault(); // Impedisce il ricaricamento della pagina

            const email = document.getElementById('register-email').value;
            const username = document.getElementById('register-username').value;
            const password = document.getElementById('register-password').value;
            const confirmPassword = document.getElementById('register-confirm-password').value;

            if (password !== confirmPassword) {
                alert('Le password non corrispondono!');
                return;
            }

            console.log('Tentativo di registrazione con:', { email, username, password });

            // Simulazione di una chiamata API per la registrazione
            // In un'applicazione reale, qui si farebbe una richiesta fetch al backend
            alert('Registrazione completata con successo! Ora puoi accedere.');
            showForm(loginForm, loginTab); // Torna al form di login
            document.getElementById('login-identifier').value = username; // Pre-compila l'username
        });
    }
});