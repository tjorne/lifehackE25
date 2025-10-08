// Knows where our user came from.
// So if a user comes from /worldmap and gets 404 somehow the button
// Will lead back to /worldmap since it's the last visited page.
// If the page was /diddy it would redirect to that.

// Last updated: 06/10-2025
// - Guac

const backBtn = document.getElementById('backBtn');
backBtn.addEventListener('click', (e) => {
    e.preventDefault();
    if (document.referrer) {
        window.location.href = document.referrer;
    } else {
        window.location.href = '/';
    }

});