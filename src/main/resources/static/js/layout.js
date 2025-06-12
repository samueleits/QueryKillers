/* Script per gestire dinamicamente l'altezza dell'header fisso e il posizionamento della sottobarra e del contenuto.
----------------------------------------------------*/

document.addEventListener('DOMContentLoaded', function() {
    const header = document.querySelector('.header');
    const subheader = document.querySelector('.subheader');
    const body = document.body;

    function adjustLayout() {
        // Verifica se gli elementi esistono per evitare errori su pagine senza subheader
        if (header && body) {
            const headerHeight = header.offsetHeight;
            let totalOffset = headerHeight;

            if (subheader && !subheader.classList.contains('hidden-on-login')) { // Controlla se il subheader non è nascosto
                const subheaderHeight = subheader.offsetHeight;
                subheader.style.top = `${headerHeight}px`;
                totalOffset += subheaderHeight;
            } else if (subheader) {
                // Se il subheader è presente ma nascosto, assicurati che il top sia corretto per il mobile se appare
                // Oppure non aggiungere la sua altezza
                subheader.style.top = `${headerHeight}px`;
            }

            body.style.paddingTop = `${totalOffset}px`;
        }
    }

    // Esegui la funzione all'avvio della pagina
    adjustLayout();

    // Esegui la funzione ogni volta che la finestra viene ridimensionata
    window.addEventListener('resize', adjustLayout);

    // Osserva le mutazioni del DOM per gestire cambiamenti dinamici (es. visibilità subheader)
    const observer = new MutationObserver(adjustLayout);
    if (subheader) {
        observer.observe(subheader, { attributes: true, attributeFilter: ['style', 'class'] });
    }
});