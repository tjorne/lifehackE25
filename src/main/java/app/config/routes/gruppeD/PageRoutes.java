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

    // HANDLES GET REQUESTS

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

        // Temp for Development
        /*
        app.get("/gruppeD/400", ctx -> ctx.status(400).html(ThymeleafSetup.render("400.html", null)));
        app.get("/gruppeD/401", ctx -> ctx.status(401).html(ThymeleafSetup.render("401.html", null)));
        app.get("/gruppeD/403", ctx -> ctx.status(403).html(ThymeleafSetup.render("403.html", null)));
        app.get("/gruppeD/404", ctx -> ctx.status(404).html(ThymeleafSetup.render("404.html", null)));
        app.get("/gruppeD/429", ctx -> ctx.status(429).html(ThymeleafSetup.render("429.html", null)));
        app.get("/gruppeD/500", ctx -> ctx.status(500).html(ThymeleafSetup.render("500.html", null)));
        app.get("/gruppeD/503", ctx -> ctx.status(503).html(ThymeleafSetup.render("503.html", null)));
        */

    }

} // PageRoutes end