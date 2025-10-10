// Package
package app.persistence.gruppeD;

// Imports
import app.entities.gruppeD.Search;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchMapper {

    // Attributes
    private final Connection connection;

    // ____________________________________________________

    public SearchMapper(Connection connection) {
        this.connection = connection;
    }

    // ____________________________________________________

    public List<Search> searchCities(String query) throws SQLException {

        List<Search> results = new ArrayList<>();

        String sql = "SELECT * FROM cities WHERE city_name ILIKE ? OR country ILIKE ? LIMIT 10";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, query + "%"); /* % prevents case sensitive content */
        stmt.setString(2, query + "%");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String cityName = rs.getString("city_name");
            String country = rs.getString("country");

            boolean isCountry = query.equalsIgnoreCase(country);

            Search city = new Search(
                    rs.getInt("id"),
                    cityName,
                    country,
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude"),
                    rs.getTimestamp("created_at"),
                    isCountry
            );
            results.add(city);
        }

        rs.close();
        stmt.close();
        return results;
    }

} // SearchMapper end