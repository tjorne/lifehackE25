package app.persistence.heltsort;

import app.entities.heltsort.Beton;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BetonMapper {

    public static Beton getBetonPrice() throws DatabaseException {
        String sql = "SELECT * FROM beton";

        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int mFilt = resultSet.getInt(2);
                int uFilt = resultSet.getInt(3);

                return new Beton(mFilt, uFilt);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("could not get Beton from database", e.getMessage());
        }
    }
}

