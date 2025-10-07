package app.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class SplitItController {
    public static void addRoutes(Javalin app)
    {
        app.get("/splitit", ctx -> index(ctx));
    }

    private static void index(Context ctx)
    {
        ctx.render("/splitit/index.html");
    }
}
