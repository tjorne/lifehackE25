package app.persistence.references;

import app.entities.references.Theorists;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TheoristsMapper {

    public static List<Theorists> getAllTheorists(ConnectionPool connectionPool) throws DatabaseException {
        List<Theorists> theoristsList = new ArrayList<>();

        String sql = "SELECT id, name FROM theorists " +
                "ORDER BY name DESC;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                theoristsList.add(new Theorists(id, name));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Something something", e.getMessage());
        }
        return theoristsList;
    }
}
