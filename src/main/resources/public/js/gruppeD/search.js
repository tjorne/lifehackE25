// Constants
const searchToggleButton = document.getElementById("search-toggle");
const searchOverlay = document.getElementById("search-overlay");
const searchInput = document.getElementById("search-wrapper-input");

// ________________________________________________________________

searchToggleButton.addEventListener("click", (e) => {
    e.preventDefault();
    searchOverlay.classList.add("active");
    document.getElementById("search-wrapper-input").focus();
});

// ________________________________________________________________

// Hides overlay again
searchOverlay.addEventListener("click", (e) => {
    if (e.target === searchOverlay) {
        searchOverlay.classList.remove("active");
    }
});

// ________________________________________________________________

searchInput.addEventListener("keydown", async (e) => {
    if (e.key === "Enter") {

        e.preventDefault();
        const query = searchInput.value.trim();

        if (!query) return;

        try {

            const res = await fetch(`/gruppeD/api/search?query=${encodeURIComponent(query)}`);
            const cities = await res.json();

            if (cities.length > 0) {
                const city = cities[0];
                const isCountry = city.isCountry === true || city.isCountry === "true";
                const zoomLevel = isCountry ? 6 : 10;
                map.setView([city.latitude, city.longitude], zoomLevel);
                searchOverlay.classList.remove("active");
            } else {
                alert("No city or found");
            }

        } catch (err) {
            console.error("Debug console error | ", err);
        }
    }

});

// ________________________________________________________________

document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") {
        searchOverlay.classList.remove("active");
    }
});

// ________________________________________________________________

function zoomToCity(city) {
    const zoomLevel = city.isCountry ? 6 : 10;
    map.setView([city.latitude, city.longitude], zoomLevel);
}