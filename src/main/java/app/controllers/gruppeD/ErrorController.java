// Package
package app.controllers.gruppeD;

// Imports
import io.javalin.http.Context;
import app.config.gruppeD.ThymeleafSetup;

public class ErrorController {

    // Attributes

    // ______________________________________________________________

    public static void handle400(Context ctx) { // Bad Request (Invalid data or request)
        ctx.status(400);
        ctx.html(ThymeleafSetup.render("/gruppeD/400", null));
    }

    // ______________________________________________________________

    public static void handle401(Context ctx) { // Unauthorized
        ctx.status(401);
        ctx.html(ThymeleafSetup.render("/gruppeD/401", null));
    }

    // ______________________________________________________________

    public static void handle403(Context ctx) { // Forbidden (No Access)
        ctx.status(403);
        ctx.html(ThymeleafSetup.render("/gruppeD/403", null));
    }

    // ______________________________________________________________

    public static void handle404(Context ctx) { // Not Found
        ctx.status(404);
        ctx.html(ThymeleafSetup.render("/gruppeD/404", null));
    }

    // ______________________________________________________________

    public static void handle429(Context ctx) { // Rate Limit
        ctx.status(429);
        ctx.html(ThymeleafSetup.render("/gruppeD/429", null));
    }

    // ______________________________________________________________

    public static void handle500(Context ctx) { // Server Code Error
        ctx.status(500);
        ctx.html(ThymeleafSetup.render("/gruppeD/500", null));
    }

    // ______________________________________________________________

    public static void handle503(Context ctx) { // Server || Database -> Down
        ctx.status(503);
        ctx.html(ThymeleafSetup.render("/gruppeD/503", null));
    }

} // ErrorController end