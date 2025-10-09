package app;

import app.config.ThymeleafConfig;
import app.controllers.filmRouletten.FilmRoulettenController;
import app.controllers.filmRouletten.MovieController;
import app.controllers.filmRouletten.UserController;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main 
{
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/FilmRouletten?currentSchema=public";
    private static final String DB = "FilmRouletten";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args)
    {
        // Initializing Javalin and Jetty webserver
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
            config.staticFiles.add("/templates");
        }).start(7070);

        // Routing
        //UserController.addRoutes(app);
        //TimeZonesController.addRoutes(app);

        app.get("/", ctx -> ctx.render("filmRouletten/index.html"));
        UserController.addRoutes(app);
        FilmRoulettenController.addRoutes(app);
        MovieController.addRoutes(app);

    }
}