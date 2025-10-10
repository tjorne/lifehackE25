// Package
package app.controllers.gruppeD;

// Imports
import app.entities.User;
import io.javalin.http.Context;
import app.config.ThymeleafConfig;
import java.util.HashMap;
import java.util.Map;

public class PageController {

    // Attributes

    // ___________________________________________________________________

    public static void loginPage(Context ctx) {
        ctx.html(ThymeleafConfig.render("gruppeD/login", null));
    }

    // ___________________________________________________________________

    public static void registerPage(Context ctx) {
        ctx.html(ThymeleafConfig.render("gruppeD/register", null));
    }

    // ___________________________________________________________________

    public static void worldMapPage(Context ctx) {
        ctx.html(ThymeleafConfig.render("gruppeD/index", null));
    }

    // ___________________________________________________________________

    public static void settingsPage(Context ctx) {
        ctx.html(ThymeleafConfig.render("gruppeD/settings", null));
    }

} // PageController end