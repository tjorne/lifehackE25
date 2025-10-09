package app.controllers.filmRouletten;

import app.entities.User;
import app.entities.filmRouletten.Movie;
import app.exceptions.DatabaseException;
import app.persistence.filmRouletten.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class UserController {

    public static void addRoutes(Javalin app) {

        app.get("/login", ctx -> ctx.render("filmRouletten/login.html"));
        app.post("/login", ctx -> login(ctx));

        app.get("/logout", ctx -> logout(ctx));

        app.get("/createuser", ctx -> ctx.render("filmRouletten/createuser.html"));
        app.post("/createuser", ctx -> createUser(ctx));

        app.get("/watchlist", ctx -> showWatchlist(ctx));
        // /index kan være overflødig når / renderer index; du kan fjerne eller behold:
        app.get("/index", ctx -> ctx.render("filmRouletten/index.html"));
    }


    private static void createUser(Context ctx) {
        String username = ctx.formParam("username");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");

        if (username == null || username.isEmpty() || password1 == null || password1.isEmpty()) {
            ctx.attribute("message", "Udfyld alle felter.");
            ctx.render("createuser.html");
            return;
        }

        if (!password1.equals(password2)) {
            ctx.attribute("message", "Passwords matcher ikke. Prøv igen.");
            ctx.render("createuser.html");
            return;
        }

        try {
            UserMapper.createUser(username, password1);
            ctx.attribute("message", "Du er hermed oprettet som: " + username + ". Log nu ind.");
            ctx.render("login.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("createuser.html");
        }
    }

    private static void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }

    public static void login(Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            ctx.attribute("message", "Udfyld både brugernavn og password.");
            ctx.render("filmRouletten/login.html");
            return;
        }

        try {
            User user = UserMapper.login(username, password);
            ctx.sessionAttribute("currentUser", user);
            ctx.attribute("message", "Du er nu logget ind som " + username);
            ctx.render("filmRouletten/index.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", "Forkert brugernavn eller adgangskode. Prøv igen.");
            ctx.render("filmRouletten/login.html");
        }
    }

    private static void showWatchlist(Context ctx) {
        User currentUser = ctx.sessionAttribute("currentUser");

        try {
            List<Movie> watchedMovies = UserMapper.getWatchedMoviesByUser(currentUser.getUserId());
            ctx.attribute("movies", watchedMovies);
            ctx.render("filmRouletten/watchlist.html");
        } catch (DatabaseException e) {
            ctx.render("filmRouletten/watchlist.html");
        }
    }
}
