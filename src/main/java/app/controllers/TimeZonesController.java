package app.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class TimeZonesController
{
    public static void addRoutes(Javalin app)
    {
        app.get("/timezones", ctx -> index(ctx));
    }

    private static void index(Context ctx)
    {
        ctx.render("/timezones/index.html");
    }
}
