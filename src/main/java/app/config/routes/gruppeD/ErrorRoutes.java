// Package
package app.config.routes.gruppeD;

// Imports
import io.javalin.Javalin;
import app.controllers.gruppeD.ErrorController;

public class ErrorRoutes {

    // Attributes

    // ___________________________________________________

    public ErrorRoutes(Javalin app) {
        app.error(400, ErrorController::handle400);
        app.error(401, ErrorController::handle401);
        app.error(403, ErrorController::handle403);
        app.error(404, ErrorController::handle404);
        app.error(409, ErrorController::handle429);
        app.error(500, ErrorController::handle500);
        app.error(503, ErrorController::handle503);
    }

} // ErrorRoutes end