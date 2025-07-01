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