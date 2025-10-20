package app.controllers;

import app.entities.User;
import app.entities.VineTimeSession;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.VineTimeSessionDAO;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class VineTimeController {

    public static void addRoutes(Javalin app) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        VineTimeSessionDAO sessionDAO = new VineTimeSessionDAO(connectionPool);

        System.out.println("Adding vinetime route");

        app.get("/vinetime", VineTimeController::index);
        app.post("/api/vinetime/session", ctx -> createSession(ctx, sessionDAO));
        app.get("/api/vinetime/sessions", ctx -> getSessions(ctx, sessionDAO));
        app.get("/api/vinetime/sessions/today", ctx -> getTodaySessions(ctx, sessionDAO));
        app.delete("/api/vinetime/session/{id}", ctx -> deleteSession(ctx, sessionDAO));
    }

    private static void index(Context ctx) {
        ctx.render("/vinetime/index.html");
    }

    private static void createSession(Context ctx, VineTimeSessionDAO sessionDAO) {
        User user = ctx.sessionAttribute("currentUser");
        if (user == null) {
            ctx.status(401).json("Unauthorized");
            return;
        } try {

            VineTimeSession session = ctx.bodyAsClass(VineTimeSession.class);
            session.setUserId(user.getUserId());
            int sessionId = sessionDAO.create(session);
            session.setSessionId(sessionId);
            ctx.status(201).json(session);
        } catch (DatabaseException e) {
            ctx.status(500).json(e.getMessage());
        }
    }

    private static void getSessions(Context ctx, VineTimeSessionDAO sessionDAO) {
        User user = ctx.sessionAttribute("currentUser");
        if (user == null) {
            ctx.status(401).json("Unauthorized");
            return;

        }
        try {
            List<VineTimeSession> sessions = sessionDAO.getSessionsByUserId(user.getUserId());
            ctx.json(sessions);
        } catch (DatabaseException e) {
            ctx.status(500).json(e.getMessage());
        }
    }

    private static void getTodaySessions(Context ctx, VineTimeSessionDAO sessionDAO) {
        User user = ctx.sessionAttribute("currentUser");
        if (user == null) {
            ctx.status(401).json("Unauthorized");
            return;

        }
        try {
            List<VineTimeSession> sessions = sessionDAO.getTodaySessionsByUserId(user.getUserId());
            ctx.json(sessions);
        } catch (DatabaseException e) {
            ctx.status(500).json(e.getMessage());
        }
    }

    private static void deleteSession(Context ctx, VineTimeSessionDAO sessionDAO) {
        User user = ctx.sessionAttribute("currentUser");
        if (user == null) {
            ctx.status(401).json("Unauthorized");
            return;

        } try {
            int sessionId = Integer.parseInt(ctx.pathParam("id"));
            boolean deleted = sessionDAO.deleteSession(sessionId);

            if (deleted) {
                ctx.status(204);

            } else {

                ctx.status(404).json("Session not found");
            }
        } catch (DatabaseException e) {
                ctx.status(500).json(e.getMessage());
        }
    }
}