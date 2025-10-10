document.addEventListener('DOMContentLoaded', () => {

    const menuItems = document.querySelectorAll('.settings-nav li');
    const contentArea = document.querySelector('.settings-content');
    const contentMap = {
        'profile': `
            <h2>Profile Settings</h2>
            <p>Change your username, email & other general information!<br><br>NOT WORKING</p>
            <div class="settings-btn-wrapper">
                <div class="changeUsernameWrapper">
                    <button type="button" id="changeUsername" class="guac-btn guac-btn-profileDefault">
                        Change Username
                    </button>
                    <div class="changeUsernameHidden deleteInputWrapper" style="display:none;">
                        <input type="text" id="oldUsername" placeholder="Current Username" required>
                        <input type="text" id="newUsername" placeholder="New Username" required>
                        <button type="button" id="updateInfo" class="guac-btn guac-btn-profileDefault">
                            Update
                        </button>
                    </div>
                </div>
                <div class="changeUsernameWrapper">
                    <button type="button" id="changePassword" class="guac-btn guac-btn-profileDefault">
                        Change Password
                    </button>
                    <div class="changeUsernameHidden deleteInputWrapper" style="display:none;">
                        <input type="password" id="oldPassword" placeholder="Old Password" required>
                        <input type="password" id="newPassword" placeholder="New Password" required>
                        <input type="password" id="newPasswordConfirm" placeholder="New Password Confirm" required>
                        <button type="button" id="updateInfo" class="guac-btn guac-btn-profileDefault">
                            Update
                        </button>
                    </div>
                </div>
                <div class="changeUsernameWrapper">
                    <button type="button" id="changeEmail" class="guac-btn guac-btn-profileDefault">
                        Change Email
                    </button>
                    <div class="changeUsernameHidden deleteInputWrapper" style="display:none;">
                        <input type="text" id="oldUsername" placeholder="Current Username" required>
                        <input type="text" id="oldEmail" placeholder="Current Email" required>
                        <input type="text" id="newEmail" placeholder="New Email" required>
                        <button type="button" id="updateInfo" class="guac-btn guac-btn-profileDefault">
                            Update
                        </button>
                    </div>
                </div>
            </div>
        `,
        'privacy': `
            <h2>Privacy Settings</h2>
            <p>We always store your data safely using hashed passwords and other safety measures.<br><br>NOT WORKING</p>
            <div class="settings-btn-wrapper">
                <button type="button" id="changeCookies" class="guac-btn guac-btn-profileDefault">
                    Change cookies preferences
                </button>
                <button type="button" id="passwordSafetyInfo" class="guac-btn guac-btn-profileDefault">
                    Password Safety Measures
                </button>
                <button type="button" id="dontSavePins" class="guac-btn guac-btn-profileDefault">
                    Don't save my pins
                </button>
            </div>
        `,
        'notifications': `
            <h2>Notification Settings</h2>
            <p>Tired of the notification popups? Change here!<br><br>NOT WORKING</p>
            <div class="settings-btn-wrapper">
                <button type="button" id="changeNotifications" class="guac-btn guac-btn-profileDefault">
                    Disable Notifications
                </button>
            </div>
        `,
        'language': `
            <h2>Language Settings</h2>
            <p>Feel free to change the language - Simply choose below here! <br><br>NOT WORKING</p>
            <div class="deleteInputWrapper">
                <select id="language-select">
                    <option value="">Vælg sprog...</option>
                    <option value="da">Dansk</option>
                    <option value="en">English</option>
                    <option value="es">Español</option>
                    <option value="fr">Français</option>
                </select>
            </div>
            <button type="button" class="guac-btn guac-btn-profileDefault">
                Save Language
            </button>
        `,
        'worldmap': `
            <h2>World Map Settings</h2>
            <p>Initial Zoom, Start Spawn Location, Darkmode, Lightmode, Custom Icons and so on! <br><br>NOT WORKING</p>
            <div class="settings-btn-wrapper">
                <button type="button" id="changeZoom" class="guac-btn guac-btn-profileDefault">
                    Change Initial Zoom
                </button>
                <button type="button" id="changeVisualMode" class="guac-btn guac-btn-profileDefault">
                    Darkmode / Lightmode
                </button>
                <button type="button" id="changeSpawnLocation" class="guac-btn guac-btn-profileDefault">
                    Change Spawn Location
                </button>
                <button type="button" id="changePinIcons" class="guac-btn guac-btn-profileDefault">
                    Change Pin Icons
                </button>
            </div>
        `,
        'delete': `
            <h2>Delete Account</h2>
            <p>Additional confirmations before such an action!</p>
            <div class="deleteInputWrapper">
                <input type="text" id="deleteConfirmUsername" placeholder="Type your username...">
                <input type="email" id="deleteConfirmEmail" placeholder="Type your email...">
            </div>
            <button type="button" class="guac-btn guac-btn-deleteAcc" id="open-delete-overlay" disabled>
                Delete My Account
            </button>
        `
    };

    // ________________________________________________________________________

    // Profile doesnt load without..
    contentArea.innerHTML = contentMap['profile'];
    const defaultMenuItem = document.querySelector('.settings-nav li a[href="#profile"]');
    if (defaultMenuItem) defaultMenuItem.parentElement.classList.add('active');

    // ________________________________________________________________________

    contentArea.addEventListener('click', function(e) {
        if (e.target && (e.target.id === 'changeUsername' || e.target.id === 'changePassword' || e.target.id === 'changeEmail')) {
            const hiddenDiv = e.target.nextElementSibling;
            if (hiddenDiv) {
                if (!hiddenDiv.style.display) hiddenDiv.style.display = 'none';
                hiddenDiv.style.display = hiddenDiv.style.display === 'none' ? 'block' : 'none';
            }
        }
    });

    // ________________________________________________________________________

    menuItems.forEach(item => {
        item.addEventListener('click', (e) => {
            e.preventDefault();
            menuItems.forEach(i => i.classList.remove('active'));
            item.classList.add('active');

            const target = item.querySelector('a').getAttribute('href').substring(1);

            // Difference in bagground color. Delete needs to be red
            // xoxo
            if (target === 'delete') {
                item.style.backgroundColor = 'red';
                item.style.color = 'white';
            } else {
                menuItems.forEach(i => {
                    i.style.backgroundColor = '';
                    i.style.color = '';
                });
            }

            contentArea.innerHTML = contentMap[target] || `<h2>${target}</h2><p>Placeholder</p>`;

            if (target === 'delete') {
                initDeleteAccountValidation();
            } else if (target === 'notifications'){
                changeNotifications()
            }

        });
    });

    // ________________________________________________________________________

    function initDeleteAccountValidation() {
        const usernameInput = document.getElementById('deleteConfirmUsername');
        const emailInput = document.getElementById('deleteConfirmEmail');
        const deleteButton = document.getElementById('open-delete-overlay');

        if (!usernameInput || !emailInput || !deleteButton) return;

        function checkInputs() {
            const isValid = usernameInput.value.trim() !== '' && emailInput.value.trim() !== '';
            deleteButton.disabled = !isValid;
        }

        usernameInput.addEventListener('input', checkInputs);
        emailInput.addEventListener('input', checkInputs);
        deleteButton.addEventListener('click', async () => {

            if (deleteButton.disabled) return;
            deleteButton.disabled = true;

            const params = new URLSearchParams();
            params.append('name', usernameInput.value.trim());
            params.append('email', emailInput.value.trim());

            try {
                const res = await fetch('/gruppeD/settings/delete', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: params.toString()
                });
                if (res.redirected) {
                    window.location.href = res.url;
                    return;
                }
                window.location.reload();
            } catch (e) {
                deleteButton.disabled = false;
            }


        });
    }

});