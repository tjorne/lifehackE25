package app.controllers;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.MineMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import app.entities.gruppeE.*;

public class MineController {
    public static void addRoutes(Javalin app)
    {
        try {
            Connection conn = ConnectionPool.getInstance().getConnection();
            String sql = """
                        CREATE TABLE IF NOT EXISTS public.scores (
                        id SERIAL NOT NULL,
                        user_id INTEGER NOT NULL,
                        score_value INTEGER NOT NULL,
                        date DATE NOT NULL,                        
                        difficulty VARCHAR NOT NULL,
                        CONSTRAINT scores_pkey PRIMARY KEY (id)
                        
                                                                );

                        ALTER TABLE IF EXISTS public.scores
                        ADD CONSTRAINT fk_user FOREIGN KEY (user_id)
                        REFERENCES public.users (user_id) MATCH SIMPLE
                        ON UPDATE NO ACTION
                        ON DELETE CASCADE;
                        """;
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

        } catch (Exception e) {
            System.out.println(e);
        }
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
        if (ctx.sessionAttribute("startTime") == null) {
            // game not started
            ctx.redirect("/gruppeE");
            return;
        }
        Map<String, Object> model = new HashMap<>();
        model.put("mineMap", ctx.sessionAttribute("mineMap"));
        model.put("w", ctx.sessionAttribute("w"));
        ctx.render("/gruppeE/game.html", model);
    }

    public static void serveGameOverPage(Context ctx){
        Map<String, Object> model = new HashMap<>();
        List<MineScore> scores;
        try {
            scores = MineMapper.getScores();
        } catch (Exception e) {
            System.out.println(e);
            ctx.status(500);
            return;
        }
        model.put("scores", scores);
        ctx.render("/gruppeE/gameover.html", model);
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
        ctx.sessionAttribute("startTime", System.currentTimeMillis());
        ctx.redirect("/gruppeE/game");
    }

    public static void handleMinePost(Context ctx)
    {
        if (ctx.sessionAttribute("startTime") == null) {
            ctx.redirect("/gruppeE");
            return;
        }
        int id = Integer.decode(ctx.pathParam("id"));
        MineMap map = ctx.sessionAttribute("mineMap");
        if (map == null) {
            ctx.redirect("/");
            return;
        }
        map.reveal(id);
        if (map.shownFields >= map.field.length-map.numMines) {
            // only mines are left
            long endTime = System.currentTimeMillis();
            long deltaTime = endTime-(long)ctx.sessionAttribute("startTime");
            try {
                User user = ctx.sessionAttribute("currentUser");
                MineMapper.addScore(user.getUserName(), deltaTime, ctx.sessionAttribute("difficulty"));
            } catch (Exception e) {
                // internal server error
                ctx.status(500);
            }
            ctx.redirect("/gruppeE/gameover");
            return;
        }
        if (map.gameover) {
            ctx.redirect("/gruppeE");
            return;
        }

        ctx.redirect("/gruppeE/game");
    }

    public static void serveBackStartPage(Context ctx)
    {
        ctx.sessionAttribute("mineMap",null);
        ctx.redirect("/gruppeE");
    }

    public static void serveBackToGamePage(Context ctx)
    {
        MineMap map;
        int w = ctx.sessionAttribute("w");
        int h = ctx.sessionAttribute("h");
        map = new MineMap(w,h);
        ctx.sessionAttribute("mineMap", map);
        ctx.sessionAttribute("startTime", System.currentTimeMillis());
        ctx.redirect("/gruppeE/game");
    }


}
