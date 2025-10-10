// Package
package app.config.routes.gruppeD;

// Imports
import app.controllers.gruppeD.LoginController;
import app.controllers.gruppeD.RegisterController;
import app.controllers.gruppeD.SettingsController;
import io.javalin.Javalin;

public class ValidRoutes {

    // Attributes

    // __________________________________________________________

    // POST

    public ValidRoutes(Javalin app) {
        app.post("/gruppeD/login", LoginController::handleLogin);
        app.post("/gruppeD/register", RegisterController::handleRegister);
        app.post("/gruppeD/settings/delete", SettingsController::handleDeleteAccount);
        app.post("/gruppeD/settings/changeUsername", SettingsController::handleChangeUsername);
        app.post("/gruppeD/settings/changePassword", SettingsController::handleChangePassword);
        app.post("/gruppeD/settings/changeEmail", SettingsController::handleChangeEmail);
        app.post("/gruppeD/user/notifications", SettingsController::changeNotifications);
        app.post("/gruppeD/user/language", SettingsController::changeLanguage);
    }

} // ValidRoutes end