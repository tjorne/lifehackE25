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
            Word word = wordMapper.getWord();
            ctx.sessionAttribute("correctWord", word);

            ctx.render("Wordngo/gamepage.html");
        });




    }



    public static void login(Context ctx) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();


        // Hent form parametre
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        // Check om bruger findes i DB med de angivne username + password
        try {
            User user = UserMapper.login(username, password);

            User currentUser = ctx.sessionAttribute("currentUser");
            if (user != null) {
                if (currentUser != null) {
                    ctx.req().getSession().invalidate();
                    ctx.sessionAttribute("currentUser", user);
                    ctx.render("Wordngo/gamepage.html");
                } else {
                    ctx.sessionAttribute("currentUser", user);
                    ctx.render("Wordngo/gamepage.html");
                }
            } else {ctx.render("Wordngo/index.html");}


            WordMapper wordMapper = new WordMapper();
            Word word = wordMapper.getWord();
            ctx.sessionAttribute("correctWord", word);

        } catch (DatabaseException e) {
            // Hvis nej, send tilbage til login side med fejl besked

            ctx.attribute("message", e.getMessage());
            ctx.render("wordngo/index.html");
        }

    }
}
