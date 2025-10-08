package app.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import app.entities.User;

public class WordngoController
{
    public static void addRoutes(Javalin app)
    {

        app.get("/Wordngo", ctx -> {User user = ctx.sessionAttribute("currentUser");
        ctx.attribute("user", user);
        ctx.render("Wordngo/index.html");
        });


    }

    private static void index(Context ctx)
    {
        ctx.render("/projectname/index.html");
    }



//    her kan du lytte til keystrokes
}