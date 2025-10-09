package app.persistence.filmRouletten;

import app.entities.filmRouletten.Movie;
import app.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieMapper {

    public static List<Movie> getAllMovies() {

        List<Movie> movies = new ArrayList<>();

        String sql = "SELECT * FROM movie_roulette";

        try(Connection connection = ConnectionPool.getInstance().getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("movie_id");
                String title = rs.getString("movie_title");
                String description = rs.getString("movie_description");
                Date timeStamp = rs.getDate("movie_aired");
                int rating = rs.getInt("movie_rating");
                int length = rs.getInt("movie_length");

                movies.add(new Movie(id, title, description, timeStamp, rating, length));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movies;
    }

    public static List<Movie> getAllMoviesByGenre(String genre) {

        List<Movie> movies = new ArrayList<>();

        String sql = "SELECT * FROM movie_roulette JOIN genre_roulette ON movie_roulette.movie_id = genre_roulette.movie_id WHERE genre_roulette.genre_name = ? ";

        try(Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, genre);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("movie_id");
                String title = rs.getString("movie_title");
                String description = rs.getString("movie_description");
                Date timeStamp = rs.getDate("movie_aired");
                int rating = rs.getInt("movie_rating");
                int length = rs.getInt("movie_length");

                movies.add(new Movie(id, title, description, timeStamp, rating, length));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movies;
    }

    public static Movie getMovieById(int id) {

        String sql = "SELECT * FROM movie_roulette WHERE movie_id = ?";

        try(Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Movie(
                        rs.getInt("movie_id"),
                        rs.getString("movie_title"),
                        rs.getString("movie_description"),
                        rs.getDate("movie_aired"),
                        rs.getInt("movie_rating"),
                        rs.getInt("movie_length")
                );
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
