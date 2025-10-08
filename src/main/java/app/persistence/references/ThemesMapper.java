package app.persistence.references;

import app.entities.references.Themes;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThemesMapper {

    public static List<Themes> getAllThemes(ConnectionPool connectionPool) {
        List<Themes> themesList = new ArrayList<>();

        String sql = "SELECT id, name FROM themes " +
                "ORDER BY name DESC;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                themesList.add(new Themes(id, name));
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return themesList;

    }
}
