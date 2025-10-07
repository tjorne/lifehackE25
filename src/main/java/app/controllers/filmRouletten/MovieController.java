package app.controllers.filmRouletten;

import app.entities.filmRouletten.Movie;
import app.persistence.filmRouletten.MovieMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class MovieController {

    public static void addRoutes(Javalin app) {

        app.get("", ctx -> {

            List<Movie> allMovies = MovieMapper.getAllMovies();
            ctx.attribute("allMovies", allMovies);
            ctx.render("");
        });

        app.get("", ctx -> {

            List<Movie> allMoviesByGenre = getAllMoviesByGenre(ctx);

            if (allMoviesByGenre != null) {

                ctx.attribute("allMoviesByGenre", allMoviesByGenre);
                ctx.render("");
            }
        });
    }

    public static List<Movie> getAllMoviesByGenre(Context ctx) {

        String genre = ctx.formParam("genreId");
        if (genre != null && !genre.isEmpty()) {
            return MovieMapper.getAllMoviesByGenre(genre);
        }

        return List.of();
    }
}
