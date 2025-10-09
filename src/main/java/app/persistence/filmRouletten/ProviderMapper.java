package app.persistence.filmRouletten;

import app.entities.filmRouletten.Provider;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProviderMapper {

    public static List<Provider> getProvidersByMovieId(int movieId) throws DatabaseException {
        String sql = """
        SELECT provider_roulette.provider_id,
               provider_name_roulette.provider_name,
               provider_roulette.provider_url
        FROM provider_roulette
        JOIN provider_name_roulette
          ON provider_roulette.provider_name_id = provider_name_roulette.provider_name_id
        WHERE provider_roulette.movie_id = ?
        ORDER BY provider_name_roulette.provider_name
        """;

        List<Provider> providers = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, movieId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                providers.add(new Provider(
                        rs.getInt("provider_id"),
                        rs.getString("provider_name"),
                        rs.getString("provider_url")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // få fuld stacktrace i konsollen
            throw new DatabaseException("DB fejl ved hentning af providers for movie_id: " + movieId, e.getMessage());
        }

        return providers;
    }

}
