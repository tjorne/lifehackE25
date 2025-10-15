document.addEventListener('DOMContentLoaded', () => {
    const loginBtn = document.getElementById('loginBtn');
    const historyBtn = document.getElementById('historyBtn');
    const loginModal = document.getElementById('loginModal');
    const historyModal = document.getElementById('historyModal');
    const closeLoginModal = document.getElementById('closeLoginModal');
    const closeHistoryModal = document.getElementById('closeHistoryModal');
    const fillTest = document.getElementById('fillTest');

    if (loginBtn) {
        loginBtn.addEventListener('click', () => {
            loginModal.classList.remove('hidden');
        });
    }

    if (historyBtn) {
        historyBtn.addEventListener('click', () => {
            historyModal.classList.remove('hidden');
        });
    }

    if (closeLoginModal) {
        closeLoginModal.addEventListener('click', () => {
            loginModal.classList.add('hidden');
        });
    }

    if (closeHistoryModal) {
        closeHistoryModal.addEventListener('click', () => {
            historyModal.classList.add('hidden');
        });
    }

    loginModal?.addEventListener('click', (e) => {
        if (e.target === loginModal) {
            loginModal.classList.add('hidden');
        }
    });

    historyModal?.addEventListener('click', (e) => {
        if (e.target === historyModal) {
            historyModal.classList.add('hidden');
        }
    });

    if (fillTest) {
        fillTest.addEventListener('click', (e) => {
            e.preventDefault();
            const u = document.getElementById('email');
            const p = document.getElementById('password');
            if (u) u.value = 'test';
            if (p) p.value = '1234';
        });
    }
});