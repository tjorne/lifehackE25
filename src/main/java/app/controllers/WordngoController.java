package app.controllers;

import app.entities.Word;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import app.persistence.WordMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import app.entities.User;

public class WordngoController {
    public static void addRoutes(Javalin app) {
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
        app.post("login-wordngo", ctx -> login(ctx));

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

        app.get("/changeLanguage", ctx -> {changeLanguage(ctx);
        });

    }


public static void changeLanguage(Context ctx) throws DatabaseException {
        WordMapper wordMapper = new WordMapper();
        String language = ctx.sessionAttribute("language");
        if ("English".equals(language)) {
            ctx.sessionAttribute("language", "French");
            ctx.sessionAttribute("correctWord", wordMapper.getWord(ctx.sessionAttribute("language")));
        } else if ("French".equals(language)) {
            ctx.sessionAttribute("language", "English");
            ctx.sessionAttribute("correctWord", wordMapper.getWord(ctx.sessionAttribute("language")));
        } else {
            // Optionally set a default or handle unknown/missing case
            ctx.sessionAttribute("language", "English");
            ctx.sessionAttribute("correctWord", wordMapper.getWord(ctx.sessionAttribute("language")));

        }
        ctx.render("Wordngo/gamepage.html");
    }

    public static void login(Context ctx) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();


        // Hent form parametre
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        String language = ctx.formParam("language");


        // Check om bruger findes i DB med de angivne username + password
        try {
            User user = UserMapper.login(username, password);

            User currentUser = ctx.sessionAttribute("currentUser");
            if (user != null) {
                if (currentUser != null) {
                    ctx.req().getSession().invalidate();
                    ctx.sessionAttribute("language", language);
                    ctx.sessionAttribute("currentUser", user);
                    ctx.render("Wordngo/gamepage.html");
                } else {
                    ctx.sessionAttribute("currentUser", user);
                    ctx.render("Wordngo/gamepage.html");
                }
            } else {ctx.render("Wordngo/index.html");}


            WordMapper wordMapper = new WordMapper();
            Word word = wordMapper.getWord(language);
            ctx.sessionAttribute("correctWord", word);

        } catch (DatabaseException e) {


            ctx.attribute("message", e.getMessage());
            ctx.render("wordngo/index.html");
        }

    }
}
