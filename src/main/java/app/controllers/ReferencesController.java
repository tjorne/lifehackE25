package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class ReferencesController
{
    public static void addRoutes(Javalin app)
    {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        app.get("/references", ctx -> index(ctx));
    }

    private static void index(Context ctx)
    {
        ctx.render("/references/index.html");
    }
}