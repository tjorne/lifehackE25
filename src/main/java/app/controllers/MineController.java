package app.controllers;

import java.util.Map;
import java.util.HashMap;

import app.entities.User;
import io.javalin.Javalin;
import io.javalin.http.Context;

import app.entities.gruppeE.*;

public class MineController {
    private static long startTime;
    public static void addRoutes(Javalin app)
    {
        app.get("/gruppeE", MineController::serveStartPage);
        app.get("/gruppeE/game", MineController::serveGamePage);
        app.get("/gruppeE/gameover", MineController::serveGameOverPage);
        app.post("/gruppeE/start", MineController::handleStartPost);
        app.post("/gruppeE/mine/{id}", MineController::handleMinePost);
        app.post("/gruppeE/quit", MineController::serveBackStartPage);
        app.post("/gruppeE/restart",MineController::serveBackToGamePage);
    }

    public static void serveStartPage(Context ctx)
    {
        User currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser == null) {
            ctx.redirect("/");
            return;
        }

        ctx.render("/gruppeE/start.html");
    }

    public static void serveGamePage(Context ctx){
        Map<String, Object> model = new HashMap<>();
        model.put("mineMap", ctx.sessionAttribute("mineMap"));
        model.put("w", ctx.sessionAttribute("w"));
        ctx.render("/gruppeE/game.html", model);
    }

    public static void serveGameOverPage(Context ctx){
        ctx.status(404);
    }

    public static void handleStartPost(Context ctx)
    {
        String val = ctx.formParam("difficulty");
        ctx.sessionAttribute("difficulty", val);
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
        ctx.sessionAttribute("w",w);
        ctx.sessionAttribute("h",h);
        map = new MineMap(w, h);
        ctx.sessionAttribute("mineMap", map);
        ctx.redirect("/gruppeE/game");
        startTime = System.currentTimeMillis();
    }

    public static void handleMinePost(Context ctx)
    {
        int id = Integer.decode(ctx.pathParam("id"));
        MineMap map = ctx.sessionAttribute("mineMap");
        if (id < 0 || id >= map.field.length) {
            ctx.status(400);
            return;
        }
        map.field[id].shown = true;
        if (map.field[id].isMine) {
            ctx.redirect("/gruppeE/gameover");
            return;
        }

        serveGamePage(ctx);
    }

    public static void serveBackStartPage(Context ctx)
    {
        ctx.sessionAttribute("mineMap",null);
        ctx.render("/gruppeE/start.html");
    }

    public static void serveBackToGamePage(Context ctx)
    {
        MineMap map;
        int w = ctx.sessionAttribute("w");
        int h = ctx.sessionAttribute("h");
        map = new MineMap(w,h);
        ctx.sessionAttribute("mineMap", map);
        ctx.redirect("/gruppeE/game");
        startTime = System.currentTimeMillis();
    }

    public static long getStartTime() {
        return startTime;
    }


}
