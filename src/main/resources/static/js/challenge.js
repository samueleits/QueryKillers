// Cambio i colori delle challenge basandomi sul numero della challenge
document.addEventListener('DOMContentLoaded', function() {
        // Definisci una palette di colori standard
        const colors = [
            '#E1F5FE',
            '#E8F5E9', 
            '#FFFDE7', 
            '#FCE4EC', 
            '#E3F2FD',
            '#FFF3E0', 
            '#EDE7F6', 
            '#E0F2F7', 
            '#F1F8E9',
            '#FBE9E7'
        ];

        const challengeItems = document.querySelectorAll('.challenge-item');

        challengeItems.forEach((item, index) => {
            // Calcola l'indice del colore usando il modulo (%)
            // Questo farà in modo che i colori si ripetano se ci sono più challenge che colori nella lista
            const colorIndex = index % colors.length;
            item.style.backgroundColor = colors[colorIndex];
        });
    });