/*

    Script runs along side with guacamoleboy-wow.css
    Please do NOT change unless you're authorized or
               know what you're doing..

                  Last edited by
                   Guacamoleboy
                    01/10-2025

*/

// __________________________________________________________________

document.addEventListener("DOMContentLoaded", function() {
    // Animationer
    const elements = document.querySelectorAll(".guac-animate");

    function isInViewport(el) {
        const rect = el.getBoundingClientRect();
        return (
            rect.top <= (window.innerHeight || document.documentElement.clientHeight) &&
            rect.bottom >= 0
        );
    }

    function checkAnimations() {
        elements.forEach(el => {
            if (isInViewport(el) && !el.classList.contains("guac-visible")) {
                el.classList.add("guac-visible");
                const animation = el.dataset.guacAnimation;
                if (animation) {
                    el.classList.add(`guac-${animation}`);
                }
            }
        });
    }

    window.addEventListener("scroll", checkAnimations);
    window.addEventListener("resize", checkAnimations);
    checkAnimations();

});