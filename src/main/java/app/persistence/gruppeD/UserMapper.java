// Package
package app.persistence.gruppeD;

// Imports
import app.entities.gruppeD.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    // Attributes
    private final Connection connection;

    // _____________________________________________________________

    public UserMapper(Connection connection) {
        this.connection = connection;
    }

    // _____________________________________________________________

    public User getUserById(int id) throws SQLException {
        String sql = "SELECT id, username, email, password_hash, role_id, created_at, notifications, lang FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getInt("role_id"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getBoolean("notifications"),
                        rs.getString("lang")
                );
            }
        }
        return null;
    }

    // _____________________________________________________________

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT id, username, email, password_hash, role_id, created_at, notifications, lang FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getInt("role_id"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getBoolean("notifications"),
                        rs.getString("lang")
                );
            }
        }
        return null;
    }

    // _____________________________________________________________

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, email, password_hash, role_id, created_at, notifications, lang FROM users";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getInt("role_id"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getBoolean("notifications"),
                        rs.getString("lang")
                ));
            }
        }
        return users;
    }

    // _____________________________________________________________

    public void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, email, password_hash, role_id, created_at, lang) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());
            stmt.setInt(4, user.getRoleId());
            stmt.setTimestamp(5, Timestamp.valueOf(user.getCreatedAt()));
            stmt.setString(6,user.getLanguage());
            stmt.executeUpdate();

            // Opdater bruger ID med generated key
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
        }
    }

    // _____________________________________________________________

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, email = ?, password_hash = ?, role_id = ?, notifications = ?, lang = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());
            stmt.setInt(4, user.getRoleId());
            stmt.setBoolean(5, user.getNotifications());
            stmt.setInt(6, user.getId());
            stmt.setString(7, user.getLanguage());
            stmt.executeUpdate();
        }
    }

    // _____________________________________________________________

    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

} // UserMapper end