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

    // challenge-carousel.js
document.addEventListener("DOMContentLoaded", function () {
    const slides = document.querySelectorAll(".mini-challenge-slide");
    const prevBtn = document.getElementById("prevChallengeBtn");
    const nextBtn = document.getElementById("nextChallengeBtn");

    if (!slides.length || !prevBtn || !nextBtn) return;

    let currentSlide = 0;

    function showSlide(index) {
        slides.forEach((slide, i) => {
            if (i === index) {
                slide.classList.remove("hidden-slide");
                slide.classList.add("active-slide");
            } else {
                slide.classList.remove("active-slide");
                slide.classList.add("hidden-slide");
            }
        });
    }

    prevBtn.addEventListener("click", () => {
        currentSlide = (currentSlide - 1 + slides.length) % slides.length;
        showSlide(currentSlide);
    });

    nextBtn.addEventListener("click", () => {
        currentSlide = (currentSlide + 1) % slides.length;
        showSlide(currentSlide);
    });

    showSlide(currentSlide);
});
