// Function to open the provider popup
function openProviderPopup(movieId) {
    fetch(`/providers/${movieId}`)
        .then(response => response.text())
        .then(html => {
            // Create overlay background
            const overlay = document.createElement('div');
            overlay.classList.add('popup-overlay');

            // Create popup container
            const popup = document.createElement('div');
            popup.classList.add('popup-window');
            popup.innerHTML = html;

            // Add close button
            const closeBtn = document.createElement('button');
            closeBtn.classList.add('popup-close');
            closeBtn.innerText = "×";
            closeBtn.onclick = () => {
                document.body.removeChild(overlay);
            };

            popup.prepend(closeBtn);
            overlay.appendChild(popup);
            document.body.appendChild(overlay);
        })
        .catch(error => {
            console.error("Error fetching provider data:", error);
        });
}
