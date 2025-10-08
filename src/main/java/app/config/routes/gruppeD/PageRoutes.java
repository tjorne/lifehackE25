// Package
package dk.project.server.routes;

// Import
import dk.project.server.ThymeleafSetup;
import dk.project.server.controllers.SearchController;
import io.javalin.Javalin;
import dk.project.server.controllers.PageController;
import dk.project.server.controllers.PinController;

public class PageRoutes {

    // Attributes

    // _________________________________________________

    // HANDLES GET REQUESTS

    public PageRoutes(Javalin app) {

        // Pathing
        app.get("/", PageController::loginPage);
        app.get("/register", PageController::registerPage);
        app.get("/settings",PageController::settingsPage);
        app.get("/worldmap", PageController::worldMapPage);
        app.get("/api/search", SearchController::handleSearch);
        app.post("/pins", PinController::createPin);
        app.delete("/pins/{id}", PinController::deletePin);
        app.get("/pins", PinController::getPins);



        // Temp for Development
        /*
        app.get("/400", ctx -> ctx.status(400).html(ThymeleafSetup.render("400.html", null)));
        app.get("/401", ctx -> ctx.status(401).html(ThymeleafSetup.render("401.html", null)));
        app.get("/403", ctx -> ctx.status(403).html(ThymeleafSetup.render("403.html", null)));
        app.get("/404", ctx -> ctx.status(404).html(ThymeleafSetup.render("404.html", null)));
        app.get("/429", ctx -> ctx.status(429).html(ThymeleafSetup.render("429.html", null)));
        app.get("/500", ctx -> ctx.status(500).html(ThymeleafSetup.render("500.html", null)));
        app.get("/503", ctx -> ctx.status(503).html(ThymeleafSetup.render("503.html", null)));
        */

    }

} // PageRoutes end