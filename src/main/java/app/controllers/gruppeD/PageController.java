// Package
package dk.project.server.controllers;

// Imports
import dk.project.User;
import io.javalin.http.Context;
import dk.project.server.ThymeleafSetup;
import java.util.HashMap;
import java.util.Map;

public class PageController {

    // Attributes

    // ___________________________________________________________________

    public static void loginPage(Context ctx) {
        ctx.html(ThymeleafSetup.render("login.html", null));
    }

    // ___________________________________________________________________

    public static void registerPage(Context ctx) {
        ctx.html(ThymeleafSetup.render("register.html", null));
    }

    // ___________________________________________________________________

    public static void worldMapPage(Context ctx) {

        ctx.html(ThymeleafSetup.render("index.html", null));
    }

    // ___________________________________________________________________

    public static void settingsPage(Context ctx) {
        ctx.html(ThymeleafSetup.render("settings.html", null));
    }

} // PageController end