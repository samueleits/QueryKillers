document.addEventListener('DOMContentLoaded', () => {
    const loginTab = document.getElementById('login-tab');
    const registerTab = document.getElementById('register-tab');
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');

    function showForm(formToShow, tabToActivate) {
        loginForm.classList.remove('active');
        registerForm.classList.remove('active');
        loginTab.classList.remove('active');
        registerTab.classList.remove('active');
        formToShow.classList.add('active');
        tabToActivate.classList.add('active');
    }

    if (loginTab && registerTab && loginForm && registerForm) {
        loginTab.addEventListener('click', () => showForm(loginForm, loginTab));
        registerTab.addEventListener('click', () => showForm(registerForm, registerTab));
    }

    // --- Validazione conferma password ---
    const password = document.getElementById('register-password');
    const confirm = document.getElementById('register-confirm-password');
    if (registerForm && password && confirm) {
        registerForm.addEventListener('submit', function(e) {
            if (password.value !== confirm.value) {
                confirm.setCustomValidity("Le password non coincidono.");
                confirm.reportValidity();
                e.preventDefault();
            } else {
                confirm.setCustomValidity("");
            }
        });
    }
});