package app.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class FilmRoulettenController
{
    public static void addRoutes(Javalin app)
    {
        app.get("/FilmRouletten", ctx -> index(ctx));
    }

    private static void index(Context ctx)
    {
        ctx.render("/FilmRouletten/index.html");
    }
}