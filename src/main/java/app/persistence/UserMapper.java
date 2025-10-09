package app.persistence;

import app.entities.User;
import app.entities.filmRouletten.Movie;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class UserMapper {

    public static User login(String userName, String password) throws DatabaseException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, userName);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("user_id");
                return new User(id, userName, password);
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen");
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public static void createUser(String userName, String password) throws DatabaseException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, userName);
            ps.setString(2, password);

            ps.executeUpdate();

        } catch (SQLException e) {

            if (e.getSQLState().equals("23505")) {
                throw new DatabaseException("Brugernavnet '" + userName + "' findes allerede. Vælg et andet.");
            } else {
                throw new DatabaseException("Fejl ved oprettelse af bruger: " + e.getMessage());
            }
        }
    }


    public static void addMovieToWatched(int currentUserID, int selectedMovieID) throws DatabaseException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        if (isMovieInWatchlist(currentUserID, selectedMovieID)) {
            User user = getUserFromID(currentUserID);
            String username = user.getUserName();
            throw new DatabaseException("Filmen er allerede på watchlist for bruger " + username);
        }

        String sql = "INSERT INTO watchlist (current_user, selected_movie) VALUES (?, ?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, currentUserID);
            ps.setInt(2, selectedMovieID);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected != 1) {
                User user = getUserFromID(currentUserID);
                String username = user.getUserName();
                throw new DatabaseException("Fejl ved indsætning af film på watchlist for bruger " + username);
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl ved indsætning i watchlist", e.getMessage());
        }
    }



    public static boolean isMovieInWatchlist(int userID, int movieID) throws DatabaseException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        String sql = "SELECT COUNT(*) AS count FROM watchlist_roulette WHERE current_user = ? AND selected_movie = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, userID);
            ps.setInt(2, movieID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl ved tjek af watchlist", e.getMessage());
        }
    }

    public static User getUserFromID(int userID) throws DatabaseException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        String sql = "SELECT user_id, username, password, role FROM user_roulette WHERE user_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, userID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("user_id");
                String username = rs.getString("username");
                String password = rs.getString("password");

                return new User(id, username, password);
            } else {
                throw new DatabaseException("Ingen bruger fundet med ID: " + userID);
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl ved hentning af bruger", e.getMessage());
        }
    }

    public static List<Movie> getWatchedMoviesByUser(int userID) throws DatabaseException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        String sql = """
        SELECT m.movie_title, m.movie_description, m.movie_aired, m.movie_length, m.movie_rating
        FROM movie_roulette m
        JOIN watchlist w ON m.movie_id = w.movie_id
        WHERE w.user_id = ?
        ORDER BY m.movie_title
        """;

        List<Movie> watchedMovies = new ArrayList<>();

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, userID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("movie_id");
                String title = rs.getString("movie_title");
                String description = rs.getString("movie_description");
                Date aired = rs.getDate("movie_aired");
                int length = rs.getInt("movie_length");
                int rating = rs.getInt("movie_rating");

                Movie movie = new Movie(id, title, description, aired, rating, length);
                watchedMovies.add(movie);
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl ved hentning af watchlist", e.getMessage());
        }

        return watchedMovies;
    }



}
