package app.controllers.filmRouletten;

import app.entities.filmRouletten.Movie;
import app.persistence.filmRouletten.MovieMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;
import java.util.Random;

public class MovieController {

    public static void addRoutes(Javalin app) {

        app.get("", ctx -> {

            List<Movie> allMovies = MovieMapper.getAllMovies();
            ctx.attribute("allMovies", allMovies);
            ctx.render("");
        });

        app.get("", ctx -> {

            Movie aMovieByGenre = getAMoviesByGenre(ctx);

            if (aMovieByGenre != null) {

                ctx.attribute("aMovieByGenre", aMovieByGenre);
                ctx.render("");
            }
        });
    }

    public static Movie getAMoviesByGenre(Context ctx) {

        String genre = ctx.formParam("genreId");

        if (genre != null) {

            List<Movie> movies = MovieMapper.getAllMoviesByGenre(genre);

            if (!movies.isEmpty()) {

                Random random = new Random();

                int size = random.nextInt(movies.size());

                return movies.get(size);
            }
        }

        return null;
    }
}
