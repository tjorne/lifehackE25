// Makes sure our placeholder values reset on page reload.
// Else it'll keep same value as previously selected which is.. Not pretty?

// - Guac

// ___________________________________________________________________________________

window.addEventListener('DOMContentLoaded', () => {
    const select = document.getElementById('selectOption');
    const input = document.getElementById('inputDest');

    select.value = '';
    input.value = '';
});

// ___________________________________________________________________________________

window.addEventListener('DOMContentLoaded', () => {
    const bookingForm = document.getElementById('bookingForm');
    if (bookingForm) {
        const inputs = bookingForm.querySelectorAll('input');
        const selects = bookingForm.querySelectorAll('select');

        inputs.forEach(input => input.value = '');
        selects.forEach(select => select.value = '');
    }
});