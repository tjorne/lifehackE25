package app;

import app.config.ThymeleafConfig;
import app.controllers.*;
import app.entities.User;
import app.entities.Word;
import app.persistence.ConnectionPool;
import app.persistence.WordMapper;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import static app.controllers.TimeZonesController.index;
import static app.controllers.UserController.*;
import static app.controllers.WordngoController.changeLanguage;

public class Main 
{
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "lifehack";

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
        app.get("/", ctx -> ctx.render("index.html"));

        app.get("/timezones", ctx -> index(ctx));

        app.post("login", UserController::login);
        app.get("logout", UserController::logout);
        app.get("createuser", ctx -> ctx.render("createuser.html"));
        app.post("createuser", UserController::createUser);

        app.get("/Wordngo", ctx -> {
            User user = ctx.sessionAttribute("currentUser");
            ctx.attribute("user", user);
            Word word = ctx.sessionAttribute("correctWord");
            if (word != null) {
                ctx.result(word.getWord()); // Return the word as plain text
            } else {
                ctx.status(404).result("No word found");
            }

            ctx.render("Wordngo/index.html");


        });
        app.post("login-wordngo", WordngoController::login);

        app.get("/api/correct-word", ctx -> {
            Word word = ctx.sessionAttribute("correctWord");
            if (word != null) {
                ctx.result(word.getWord()); // Return the word as plain text
            } else {
                ctx.status(404).result("No word found");
            }
        });

        app.get("/Wordngo/gamepage", ctx -> {
            User user = ctx.sessionAttribute("currentUser");
            ctx.attribute("user", user);
            WordMapper wordMapper = new WordMapper();
            Word word = wordMapper.getWord(ctx.sessionAttribute("language"));

            ctx.sessionAttribute("correctWord", word);
            ctx.render("Wordngo/gamepage.html");
        });
        app.get("/changeLanguage", WordngoController::changeLanguage);
        }
    }
