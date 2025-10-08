// Package
package app;

// Imports
import app.config.ThymeleafConfig;
import app.controllers.*;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {

    // Attributes
    private static final String USER = "postgres";
    private static final String PASSWORD = "dinmor";
    private static final String URL = "jdbc:postgresql://localhost:5433/%s?currentSchema=public";
    private static final String DB = "lifehack";
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    // _______________________________________________________________

    public static void main(String[] args) {

        // Initializing Javalin and Jetty webserver
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
            config.staticFiles.add("/templates");
        }).start(7070);

        // Routing
        app.get("/", ctx -> ctx.render("index.html"));
        UserController.addRoutes(app);
        TimeZonesController.addRoutes(app);

        // Gruppe D Routing
        new app.config.routes.gruppeD.PageRoutes(app);
        new app.config.routes.gruppeD.ValidRoutes(app);
        new app.config.routes.gruppeD.ErrorRoutes(app);

    }

} // Main End