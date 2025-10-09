package app.controllers.filmRouletten;

import app.entities.filmRouletten.Provider;
import app.exceptions.DatabaseException;
import app.persistence.filmRouletten.ProviderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class ProviderController {

    public static void addRoutes(Javalin app) {
        //TODO: sæt til knap for "Se filmen" app.get("/providers/:movieId", ctx -> showProviders(ctx));

        app.get("/providers/{movieId}", ProviderController::showProviders);
    }

    private static void showProviders(Context ctx) {
        int movieId = Integer.parseInt(ctx.pathParam("movieId"));

        try {
            List<Provider> providers = ProviderMapper.getProvidersByMovieId(movieId);
            ctx.attribute("providers", providers);
            ctx.render("partials/providers.html");
        } catch (DatabaseException e) {
            ctx.result("<p>Kunne ikke hente streamingtjenester: " + e.getMessage() + "</p>");
        }
    }
}
