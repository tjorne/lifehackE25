// Package
package dk.project.mapper;

// Imports
import dk.project.Pin;
import dk.project.User;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class PinMapper {

    // Attributes
    private final Connection connection;

    // ________________________________________

    public PinMapper(Connection connection) {
        this.connection = connection;
    }

    // ________________________________________

    public void deletePin(int id) throws SQLException {

        String sql = "DELETE FROM pins WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        }
    }

    // ________________________________________

    public void addPin(Pin pin) throws SQLException {

        String sql = "INSERT INTO pins (user_id, category_id, latitude, longitude, created_at, title, rating) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, pin.getUser_id());
            stmt.setInt(2, pin.getCategory_id());
            stmt.setDouble(3, pin.getLatitude());
            stmt.setDouble(4, pin.getLongitude());
            stmt.setTimestamp(5, pin.getCreated_at());
            stmt.setString(6, pin.getTitle());
            stmt.setInt(7, pin.getRating());
            stmt.executeUpdate();

        }
    }

    // ________________________________________

    public void updatePin(Pin pin) throws SQLException {
        String sql = "UPDATE pins SET user_id = ?, category_id = ?, latitude = ?, longitude = ?, created_at = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pin.getUser_id());
            stmt.setInt(2, pin.getCategory_id());
            stmt.setDouble(3, pin.getLatitude());
            stmt.setDouble(4, pin.getLongitude());
            stmt.setTimestamp(5, pin.getCreated_at());
            stmt.setInt(6, pin.getId());
            stmt.executeUpdate();
        }
    }

    // ________________________________________

    public ArrayList<Pin> getPinsByUserId(int userId) throws SQLException {

        ArrayList<Pin> pins = new ArrayList<>();
        String sql = "SELECT * FROM pins WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Pin pin = new Pin(

                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("category_id"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getTimestamp("created_at"),
                        rs.getString("title"),
                        rs.getInt("rating")

                );

                pins.add(pin);
            }
        }
        return pins;
    }


} // PinMapper end