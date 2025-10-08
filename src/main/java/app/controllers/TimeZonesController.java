// Package
package app.controllers;

// Imports
import io.javalin.Javalin;
import io.javalin.http.Context;

public class TimeZonesController {

    // Attributes

    // ______________________________________________________________

    public static void addRoutes(Javalin app) {
        app.get("/timezones", ctx -> index(ctx));
    }

    // ______________________________________________________________

    private static void index(Context ctx) {
        ctx.render("/timezones/index.html");
    }

} // TimeZoneController end