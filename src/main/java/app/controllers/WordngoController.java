package app.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class WordngoController
{
    public static void addRoutes(Javalin app)
    {
        app.get("/projectname", ctx -> index(ctx));

    }

    private static void index(Context ctx)
    {
        ctx.render("/projectname/index.html");
    }



//    her kan du lytte til keystrokes
}