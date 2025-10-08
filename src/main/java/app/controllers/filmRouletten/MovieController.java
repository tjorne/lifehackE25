package app.controllers.filmRouletten;

import app.entities.filmRouletten.Movie;
import app.persistence.filmRouletten.MovieMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;
import java.util.Random;

public class MovieController {

    public static void addRoutes(Javalin app) {


        app.get("/getMovie", ctx -> {
            String genre = ctx.queryParam("genre");
            if (genre == null || genre.isEmpty()) {
                ctx.redirect("/");
                return;
            }

            Movie movie = getRandomMovieByGenre(genre);

            ctx.attribute("selectedGenre", genre);
            ctx.attribute("movie", movie); // kan være null hvis ingen film fundet
            ctx.render("filmRouletten/getMovie.html");
        });

        app.get("/movies", ctx -> {
            List<Movie> allMovies = MovieMapper.getAllMovies();
            ctx.attribute("allMovies", allMovies);
            ctx.render("filmRouletten/allMovies.html"); // lav template hvis ønsket
        });
    }

    private static Movie getRandomMovieByGenre(String genre) {
        if (genre == null) return null;

        List<Movie> movies = MovieMapper.getAllMoviesByGenre(genre);

        if (movies == null || movies.isEmpty()) {
            return null;
        }

        Random random = new Random();
        return movies.get(random.nextInt(movies.size()));
    }
}
