package app.controllers;

import java.util.Map;
import java.util.HashMap;
import io.javalin.Javalin;
import io.javalin.http.Context;

import app.entities.gruppeE.*;

public class MineController {
    public static void addRoutes(Javalin app)
    {
        app.get("/gruppeE", MineController::serveStartPage);
        app.get("/gruppeE/game", MineController::serveGamePage);
        app.post("/gruppeE/start", MineController::handleStartPost);
    }

    public static void serveStartPage(Context ctx)
    {
        ctx.render("/gruppeE/start.html");
    }

    public static void serveGamePage(Context ctx){
        Map<String, Object> model = new HashMap<>();
        model.put("mineMap", ctx.sessionAttribute("mineMap"));
        ctx.render("/gruppeE/game.html", model);
    }

    public static void handleStartPost(Context ctx)
    {
        String val = ctx.formParam("difficulty");
        MineMap map;
        int w, h;
        if (val.equals("easy")) {
            w = h = 9;
        } else if (val.equals("medium")) {
            w = h = 16;
        } else if (val.equals("hard")) {
            w = 30;
            h = 16;
        } else {
            ctx.status(500);
            return;
        }
        map = new MineMap(w, h);
        ctx.sessionAttribute("mineMap", map);
        ctx.redirect("/gruppeE/game");
    }
}
