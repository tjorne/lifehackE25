package app.persistence.filmRouletten;

import app.entities.filmRouletten.Provider;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProviderMapper {

    public static List<Provider> getProvidersByMovieId(int movieId) throws DatabaseException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        String sql = """
            SELECT p.provider_id, pn.provider_name, p.provider_url
            FROM provider_roulette p
            JOIN provider_name pn ON p.provider_name_id = pn.provider_name_id
            WHERE p.movie_id = ?
            ORDER BY pn.provider_name
        """;

        List<Provider> providers = new ArrayList<>();

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, movieId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int providerId = rs.getInt("provider_id");
                String providerName = rs.getString("provider_name");
                String providerUrl = rs.getString("provider_url");

                providers.add(new Provider(providerId, providerName, providerUrl));
            }

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl ved hentning af providers for movie_id: " + movieId, e.getMessage());
        }

        return providers;
    }
}
