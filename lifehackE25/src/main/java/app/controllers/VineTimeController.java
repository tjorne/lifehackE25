package app.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class VineTimeController {

    public static void addRoutes (Javalin app) {
        System.out.println("[VineTime] Adding /vinetime route");
        app.get("/vinetime", VineTimeController::index);
    }
    private static void index(Context ctx) {
        ctx.redirect("/vinetime/index.html");
    }
}
