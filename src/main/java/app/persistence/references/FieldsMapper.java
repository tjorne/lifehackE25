package app.persistence.references;

import app.entities.references.Fields;
import app.entities.references.Theorists;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FieldsMapper {
    public static List<Fields> getAllFields(ConnectionPool connectionPool) {
        List<Fields> fieldsList = new ArrayList<>();

        String sql = "SELECT id, name FROM fields " +
                "ORDER BY name DESC;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                fieldsList.add(new Fields(id, name));
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return fieldsList;
    }
}