// Package
package dk.project.server.routes;

// Imports
import dk.project.server.controllers.LoginController;
import dk.project.server.controllers.RegisterController;
import dk.project.server.controllers.SettingsController;
import io.javalin.Javalin;

public class ValidRoutes {

    // Attributes

    // __________________________________________________________

    // HANDLES POST REQUESTS

    public ValidRoutes(Javalin app) {
        app.post("/login", LoginController::handleLogin);
        app.post("/register", RegisterController::handleRegister);
        app.post("/settings/delete", SettingsController::handleDeleteAccount);
        app.post("/settings/changeUsername", SettingsController::handleChangeUsername);
        app.post("/settings/changePassword", SettingsController::handleChangePassword);
        app.post("/settings/changeEmail", SettingsController::handleChangeEmail);
        app.post("/user/notifications", SettingsController::changeNotifications);
        app.post("/user/language", SettingsController::changeLanguage);
    }

} // ValidRoutes end