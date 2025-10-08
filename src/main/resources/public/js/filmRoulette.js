// Made with ChatGPT

document.addEventListener("DOMContentLoaded", () => {
    const logo = document.querySelector(".RouletteHjul");
    const allFade = document.querySelectorAll(".login-btn, .watchlist-btn, .pill, .genre-title");
    const genreButtons = document.querySelectorAll(".pill");
    let started = false;

    const startBtn = document.createElement("button");
    startBtn.textContent = "START";
    startBtn.className = "start-btn";
    document.body.appendChild(startBtn);

    genreButtons.forEach(btn => {
        btn.addEventListener("click", (e) => {
            if (started) return;
            started = true;

            // fade alt ud
            allFade.forEach(el => el.classList.add("fade-out"));

            // kort delay så fade starter først
            setTimeout(() => {
                logo.classList.add("slide-in");
            }, 600);

            // blokér hurtige ekstra-klik mens animation kører
            document.body.style.pointerEvents = "none";
            startBtn.style.pointerEvents = "auto";
        });
    });

    logo.addEventListener("transitionend", (ev) => {
        if (ev.propertyName === "left" || ev.propertyName === "transform") {
            startBtn.classList.add("visible");
            document.body.style.pointerEvents = "";
        }
    });

    startBtn.addEventListener("click", () => {
        window.location.href = "/login";
    });
});
