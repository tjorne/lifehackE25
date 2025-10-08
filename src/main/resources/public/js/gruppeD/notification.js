/*

    Notification Box Bottom Right
    Written by Guacamoleboy
    Date: 07/10-2025

*/

// Attributes

// _______________________________________________________________________

function showNotification(message, color = "green") {

    const container = document.getElementById('guac-notification-container');
    if (!container) return;

    const notificationBox = document.createElement('div');
    const rootStyles = getComputedStyle(document.documentElement);
    let bgColor;

    switch(color.toLowerCase()) {
        case "green":
            bgColor = rootStyles.getPropertyValue('--lifehack-green');
            break;
        case "orange":
        case "warning":
            bgColor = rootStyles.getPropertyValue('--lifehack-orange');
            break;
        case "red":
        default:
            bgColor = rootStyles.getPropertyValue('--lifehack-red');
            break;
    }

    notificationBox.className = 'guac-notification';
    notificationBox.innerText = message;
    notificationBox.style.backgroundColor = bgColor.trim();

    container.appendChild(notificationBox);

    requestAnimationFrame(() => {
        notificationBox.style.opacity = '1';
        notificationBox.style.transform = 'translateX(0)';
    });

    setTimeout(() => {
        notificationBox.style.opacity = '0';
        notificationBox.style.transform = 'translateX(100%)';
        setTimeout(() => container.removeChild(notificationBox), 500);
    }, 5000);

}

// _______________________________________________________________________

document.addEventListener("DOMContentLoaded", function() {

    const urlParams = new URLSearchParams(window.location.search);
    const error = urlParams.get("error");

    if (document.getElementById('map')) {
        showNotification("Welcome Back", "green");
    }

    switch(error) {
        case "wrongInfo":
            showNotification("Wrong username or password...", "red");
            break;
        case "accountCreated":
            showNotification("Account created! Please log in.", "green");
            break;
        case "wrongPassMatch":
            showNotification("Passwords don't match...", "red");
            break;
        case "accountExists":
            showNotification("Username already exists...", "orange");
            break;
        case "missingFields":
            showNotification("Missing fields...", "red");
            break;
        case "500":
            showNotification("Server error: 500", "red");
            break;
        case "accountDeleted":
            showNotification("Your account has been deleted.", "orange");
            break;
        case "deleteCancelled":
            showNotification("Deletion cancelled.", "orange");
            break;
        case "deleteMissingFields":
            showNotification("Please fill out both fields.", "red");
            break;
        case "deleteConfirmMismatch":
            showNotification("Confirmation text mismatch.", "red");
            break;
        case "deleteNameMismatch":
            showNotification("Username does not match the current user.", "red");
            break;
        case "deleteNotLoggedIn":
            showNotification("You must be logged in.", "red");
            break;
        case "deleteError":
            showNotification("Could not delete account right now.", "red");
            break;
        case "usernameChanged":
            showNotification("You changed your username.", "green");
            break;
        case "UsernameError":
            showNotification("An error has occurred!", "red");
            break;

    }

});