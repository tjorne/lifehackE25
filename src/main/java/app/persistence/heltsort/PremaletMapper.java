package app.persistence.heltsort;

import app.entities.heltsort.Premalet;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PremaletMapper {

    public static Premalet getPremaletPrice() throws DatabaseException {
        String sql = "SELECT * FROM premalet";

        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int oneCoat = resultSet.getInt(2);
                int twoCoat = resultSet.getInt(3);

                return new Premalet(oneCoat, twoCoat);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("could not get Premalet from database", e.getMessage());
        }
    }
}

