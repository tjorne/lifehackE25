/* ______________________________________ */

// Variables

/* ______________________________________ */

let pinMode = false;
let eraseMode = false;
let activeButton = null;


//Variables for mode (visited, hated, bucket)
let currentMode = 'visited';
const visitedMarkers = [];
const hatedMarkers = [];
const bucketMarkers = [];
const pinButton = document.querySelector('#pinButton');
const visitedButton = document.querySelector('#displayVisited');
const hatedButton = document.querySelector('#displayHated');
const bucketButton = document.querySelector('#displayBucketList');
const displayMode = document.getElementById("mode-display");
//end of mode


const HIT_RADIUS = 50;
const MIN_DISTANCE = 30;
const eraserButton = document.querySelector('#eraserButton');
const STANDARD_MAX_ZOOM = 14; // For V & BL
const SPORT_MAX_ZOOM = 18; // For S & P
const ratingLabels = {
    1: "Very Bad",
    2: "Bad",
    3: "Average",
    4: "Good",
    5: "Very Good",
    6: "Perfect"
};

/* ______________________________________ */

// Startup så pins loader!!!!

/* ______________________________________ */

document.addEventListener("DOMContentLoaded", loadPins);

/* ______________________________________ */

// Worldmap

/* ______________________________________ */

const map = L.map('map', {
    center: [56.2639, 9.5018], // Denmark Location
    zoom: 4, // Initial Zoom
    minZoom: 3, // Min Zoome
    maxZoom: STANDARD_MAX_ZOOM, // Max Zoom
    maxBounds: [[-90, -180], [90, 180]], // South / North Poles
    maxBoundsViscosity: 1.0 // Can't exit map
});

// __________________________________________________________

L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: 'BeyondBorders.dk | Build: 0.1.0 | Release at 1.0.0',
    subdomains: 'abcd',
    maxZoom: 15
}).addTo(map);

/* ______________________________________ */

// Visuals

/* ______________________________________ */

const redIcon = L.icon({ /* Visited Icon */
    iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});

// __________________________________________________________

function updateActiveNavbarMode() {

    // Clear active fra alle af knapperne man 
    visitedButton && visitedButton.classList.remove('active');
    hatedButton && hatedButton.classList.remove('active');
    bucketButton && bucketButton.classList.remove('active');

    // Aktiv baseret på den mode man er på 
    switch (currentMode) {
        case 'visited':
            visitedButton && visitedButton.classList.add('active');
            break;
        case 'hated':
            hatedButton && hatedButton.classList.add('active');
            break;
        case 'bucket':
            bucketButton && bucketButton.classList.add('active');
            break;
        default:
            break;
    }
}

// __________________________________________________________

const blIcon = L.icon({ /* Bucket List Icon */
    iconUrl: '/images/gruppeD/icons/bucketlist-2-s.png',
    iconSize: [32, 32],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});

// __________________________________________________________

pinButton.addEventListener('click', () => { /* Pin */
    if (!pinMode) {
        pinMode = true;
        pinButton.classList.add('active');
    } else {
        pinMode = false;
        pinButton.classList.remove('active');
    }
});

// __________________________________________________________

visitedButton.addEventListener('click', () => {
    currentMode = 'visited';
    updateVisibleMarkers();
    updateActiveNavbarMode();
});

// __________________________________________________________

hatedButton.addEventListener('click', () => {
    currentMode = 'hated';
    updateVisibleMarkers();
    updateActiveNavbarMode();
});

// __________________________________________________________

bucketButton.addEventListener('click', () => {
    currentMode = 'bucket';
    updateVisibleMarkers();
    updateActiveNavbarMode();
});

// __________________________________________________________

map.on('click', function(e) {

    // Validation
    if (!pinMode) return;
    pinMode = false;
    pinButton.classList.remove('active');

    const getLatLng = e.latlng;

    // Popup Menu
    const popupMenu = `
        <div class="popupContent guac-justify-center">
            <input type="text" id="pinPoint-title" placeholder="Title">
            <input type="range" id="pinPoint-rating" min="1" max="6" step="1" value="3">
            <label for="pinPoint-rating">
                <span id="rating-value">3 – ${ratingLabels[3]}</span>
            </label>
            <button id="save-pin" class="save-pin">Save</button>
        </div>
    `;

    // Adds our popup to (map)
    const popUpMenu = L.popup({

        offset: [0, 5] // Fixes our popup visuals

    })
        .setLatLng(getLatLng)
        .setContent(popupMenu)
        .openOn(map);

    const ratingInput = document.getElementById("pinPoint-rating"); // 1-6
    const ratingValue = document.getElementById("rating-value"); // Vores text
    let value = 3;

    // Dynamic Rating
    if (ratingInput && ratingValue) {

        ratingInput.addEventListener("input", () => {

            value = ratingInput.value;
            ratingValue.textContent = `${value} – ${ratingLabels[value]}`;

        });
    }

    // Loads our pin -> localStorage()
    let icon = redIcon;

    switch (currentMode) {

        case 'visited':
            icon = redIcon;
            break;
        case 'hated':
            icon = L.icon({
                iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-violet.png',
                shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
                iconSize: [25, 41],
                iconAnchor: [12, 41],
                popupAnchor: [1, -34],
                shadowSize: [41, 41]
            });
            break;
        case 'bucket':
            icon = blIcon;
            break;
        default:
            alert('Vælg et mode først (Visited / Hated / Bucket)');
            return;
    }


    setTimeout(() => {

        const savebtn = document.getElementById("save-pin");
        if(!savebtn) return;

        savebtn.addEventListener("click", async () => {


            const marker = L.marker(getLatLng, { icon }).addTo(map);


            if (currentMode === 'visited') visitedMarkers.push(marker);
            if (currentMode === 'hated') hatedMarkers.push(marker);
            if (currentMode === 'bucket') bucketMarkers.push(marker);

            const title = document.getElementById("pinPoint-title").value.trim();
            //const rating = document.getElementById("rating-value").value;
            const rating = `${value} – ${ratingLabels[value]}`;


            map.closePopup(popUpMenu);

            marker.pinData = {
                title: title,
                rating: rating,
                lat: getLatLng.lat,
                lng: getLatLng.lng,
                category: currentMode,
                value: value
            };

            // TODO: Der skal så her fetches og sendes til vores thymeleaf så vi kan gemme øvrige data til vores database
            const response = await fetch("/gruppeD/pins", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    title: title,
                    rating: value,
                    category: currentMode,
                    lat: getLatLng.lat,
                    lng: getLatLng.lng
                })
            });

            const savedPin = await response.json();
            marker.pinData.id = savedPin.id;

            const popupMenu = `
                <div class="popupContent guac-justify-center">
                    <h2>${marker.pinData.title || "No title"}</h2>
                    <p>${marker.pinData.rating || "No rating"}</p>
                    <button id="del-pin" class="save-pin">Delete</button>
                </div>
            `;

            marker.bindPopup(popupMenu);

            marker.on("popupopen", function () {

                setTimeout(() => {

                    const deletebtn = document.getElementById('del-pin');
                    if (deletebtn) {

                        deletebtn.addEventListener("click", async () => {

                            map.removeLayer(marker);
                            [visitedMarkers, hatedMarkers, bucketMarkers].forEach(arr => {

                                const i = arr.indexOf(marker);
                                if (i !== -1) arr.splice(i, 1);

                            });

                            try {
                                await fetch(`/gruppeD/pins/${marker.pinData.id}`, {method: "DELETE"});

                            } catch (err) {

                                console.error("Error deleting pin:", err);

                            }
                        })
                    }
                }, 100);

            })

        })
    }, 100);

});

// __________________________________________________________

function updateVisibleMarkers() {

    [...visitedMarkers, ...hatedMarkers, ...bucketMarkers].forEach(m => map.removeLayer(m));
    [visitedButton, hatedButton, bucketButton].forEach(btn => btn.classList.remove('active'));

    switch (currentMode) {

        case 'visited':
            visitedMarkers.forEach(m => map.addLayer(m));
            visitedButton.classList.add('active');
            displayMode.textContent = "Visited";
            break;
        case 'hated':
            hatedMarkers.forEach(m => map.addLayer(m));
            hatedButton.classList.add('active');
            displayMode.textContent = "Hated";
            break;
        case 'bucket':
            bucketMarkers.forEach(m => map.addLayer(m));
            bucketButton.classList.add('active');
            displayMode.textContent = "Bucket list";
            break;
        default:
            break;

    }
}

// __________________________________________________________

async function loadPins() {

    try {

        const res = await fetch("/gruppeD/pins");
        if (!res.ok) throw new Error("Failed to fetch pins");
        const pins = await res.json();

        if (!pins || pins.length === 0) {
            return;
        }

        pins.forEach(pin => {
            const ratingString = `${pin.rating} – ${ratingLabels[pin.rating]}`;
            let icon = redIcon;

            switch (pin.category_id) {

                case 1:
                    icon = redIcon;
                    break;
                case 2:
                    icon = L.icon({
                        iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-violet.png',
                        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
                        iconSize: [25, 41],
                        iconAnchor: [12, 41],
                        popupAnchor: [1, -34],
                        shadowSize: [41, 41]
                    });
                    break;
                case 3:
                    icon = blIcon;
                    break;
                default:
                    return;

            }

            const marker = L.marker([pin.latitude, pin.longitude], { icon }).addTo(map);

            const popupMenu = `
                <div class="popupContent guac-justify-center">
                    <h2>${pin.title || "No title"}</h2>
                    <p>${ratingString || "No rating"}</p>
                    <button id="del-pin" class="save-pin">Delete</button>
                </div>
            `;

            marker.bindPopup(popupMenu);

            marker.on("popupopen", function () {

                setTimeout(() => {

                    const deletebtn = document.getElementById('del-pin');

                    if (deletebtn) {

                        deletebtn.addEventListener("click", async () => {

                            map.removeLayer(marker);

                            [visitedMarkers, hatedMarkers, bucketMarkers].forEach(arr => {

                                const i = arr.indexOf(marker);
                                if (i !== -1) arr.splice(i, 1);

                            });

                            try {

                                await fetch(`/gruppeD/pins/${pin.id}`, {method: "DELETE"});

                            } catch (err) {

                                console.error("Error deleting pin:", err);

                            }
                        })
                    }
                }, 100);

            })

            if (pin.category_id === 1) visitedMarkers.push(marker);
            if (pin.category_id === 2) hatedMarkers.push(marker);
            if (pin.category_id === 3) bucketMarkers.push(marker);

        });

    } catch (err) {
        console.error("Error loading pins:", err);
    }
    updateVisibleMarkers();
}