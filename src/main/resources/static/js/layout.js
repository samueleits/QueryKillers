/* Script per gestire dinamicamente l'altezza dell'header fisso e il posizionamento della sottobarra e del contenuto.
----------------------------------------------------*/

document.addEventListener('DOMContentLoaded', function() {
    const header = document.querySelector('.header');
    const subheader = document.querySelector('.subheader');
    const body = document.body;

    function adjustLayout() {
        if (header && subheader && body) {
            // Ottieni l'altezza effettiva dell'header, inclusi padding e border
            const headerHeight = header.offsetHeight;
            // Ottieni l'altezza effettiva del subheader
            const subheaderHeight = subheader.offsetHeight;

            // Imposta la posizione 'top' del subheader subito sotto l'header
            subheader.style.top = `${headerHeight}px`;

            // Imposta il padding-top del body per compensare l'altezza combinata di header e subheader
            body.style.paddingTop = `${headerHeight + subheaderHeight}px`;
        }
    }

    // Esegui la funzione all'avvio della pagina
    adjustLayout();

    // Esegui la funzione ogni volta che la finestra viene ridimensionata
    // Questo è cruciale per la responsività, poiché l'altezza dell'header può cambiare
    window.addEventListener('resize', adjustLayout);
});