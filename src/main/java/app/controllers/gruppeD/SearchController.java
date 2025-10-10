// Package
package app.controllers.gruppeD;

// Imports
import app.entities.gruppeD.Search;
import app.db.gruppeD.Database;
import app.persistence.gruppeD.SearchMapper;
import io.javalin.http.Context;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SearchController {

    // Attributes

    // ___________________________________________________

    public static void handleSearch(Context ctx) {

        String query = ctx.queryParam("query");

        if (query == null || query.isBlank()) {
            ctx.status(400).json(Map.of("message", "Query missing"));
            return;
        }

        try (Connection connection = Database.getConnection()) {
            SearchMapper searchMapper = new SearchMapper(connection);
            List<Search> results = searchMapper.searchCities(query);

            ctx.json(results);

        } catch (SQLException e) {
            e.printStackTrace();
            ctx.status(500).json("Database error: " + e.getMessage());
        }

    }

} // SearchController end