// Package
package dk.project.server.controllers;

// Imports
import dk.project.Search;
import dk.project.db.Database;
import dk.project.mapper.SearchMapper;
import io.javalin.http.Context;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SearchController {

    // Attributes

    // ___________________________________________________

    public static void handleSearch(Context ctx) {

        String query = ctx.queryParam("query");

        if (query == null || query.isBlank()) {
            ctx.status(400).json("Query missing");
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