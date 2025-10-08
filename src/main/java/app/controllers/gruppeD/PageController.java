// Package
package app.controllers.gruppeD;

// Imports
import app.entities.User;
import io.javalin.http.Context;
import app.config.gruppeD.ThymeleafSetup;
import java.util.HashMap;
import java.util.Map;

public class PageController {

    // Attributes

    // ___________________________________________________________________

    public static void loginPage(Context ctx) {
        ctx.html(ThymeleafSetup.render("login", null));
    }

    // ___________________________________________________________________

    public static void registerPage(Context ctx) {
        ctx.html(ThymeleafSetup.render("register", null));
    }

    // ___________________________________________________________________

    public static void worldMapPage(Context ctx) {

        ctx.html(ThymeleafSetup.render("index", null));
    }

    // ___________________________________________________________________

    public static void settingsPage(Context ctx) {
        ctx.html(ThymeleafSetup.render("settings", null));
    }

} // PageController end