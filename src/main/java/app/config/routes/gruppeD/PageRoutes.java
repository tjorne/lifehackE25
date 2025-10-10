// Package
package app.config.routes.gruppeD;

// Import
import app.config.gruppeD.ThymeleafSetup;
import app.controllers.gruppeD.SearchController;
import io.javalin.Javalin;
import app.controllers.gruppeD.PageController;
import app.controllers.gruppeD.PinController;

public class PageRoutes {

    // Attributes

    // _________________________________________________

    // GET

    public PageRoutes(Javalin app) {

        // Pathing
        app.get("/gruppeD/", PageController::loginPage);
        app.get("/gruppeD/register", PageController::registerPage);
        app.get("/gruppeD/settings",PageController::settingsPage);
        app.get("/gruppeD/worldmap", PageController::worldMapPage);
        app.get("/gruppeD/api/search", SearchController::handleSearch);
        app.post("/gruppeD/pins", PinController::createPin);
        app.delete("/gruppeD/pins/{id}", PinController::deletePin);
        app.get("/gruppeD/pins", PinController::getPins);

    }

} // PageRoutes end