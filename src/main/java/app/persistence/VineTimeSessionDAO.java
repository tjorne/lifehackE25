package app.persistence;

import app.entities.VineTimeSession;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VineTimeSessionDAO {

    private final ConnectionPool connectionPool;

    public VineTimeSessionDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public int create(VineTimeSession session) throws DatabaseException {
        if (session == null) {
            throw new IllegalArgumentException("Session cannot be null");
        }

        String sql = """
                INSERT INTO vt_session (user_id, session_type, duration_seconds)
                VALUES (?, ?, ?) 
                RETURNING session_id
                """;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, session.getUserId());
            ps.setString(2, session.getSessionType());
            ps.setInt(3, session.getDurationSeconds());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new DatabaseException("Failed to create session.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error creating session", e.getMessage());
        }
    }

    public List<VineTimeSession> getSessionsByUserId(int userId) throws DatabaseException {
        String sql = """
                SELECT * FROM vt_session WHERE user_id = ?
                ORDER BY completed_at DESC
                """;
        List<VineTimeSession> sessions = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sessions.add(VineTimeSessionMapper.map(rs));
                }
            }
            return sessions;
        } catch (SQLException e) {
            throw new DatabaseException("Error getting sessions by user id", e.getMessage());
        }
    }

    public List<VineTimeSession> getTodaySessionsByUserId(int userId) throws DatabaseException {
        String sql = """
                SELECT * FROM vt_session
                WHERE user_id = ?
                AND DATE (completed_at) = CURRENT_DATE
                ORDER BY completed_at DESC
                """;
        List<VineTimeSession> sessions = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    sessions.add(VineTimeSessionMapper.map(rs));
                }
            }
            return sessions;

        } catch (SQLException e) {
            throw new DatabaseException("Error getting sessions by user id", e.getMessage());
        }
    }

    public int getSessionCountByType(int userId, String sessionType) throws DatabaseException {
        String sql = """
                SELECT COUNT (*) FROM vt_session
                WHERE user_id = ?
                AND session_type = ?
                """;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, sessionType);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error counting sessions", e.getMessage());
        }
    }

    public int getTotalFocusTime(int userId) throws DatabaseException {
        String sql = """
                SELECT COALESCE(SUM(duration_seconds), 0) FROM vt_session
                WHERE user_id = ?
                AND session_type = 'pomodoro'
                """;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error calculating focus sessions", e.getMessage());
        }
    }

    public boolean deleteSession(int sessionId) throws DatabaseException {
        String sql = "DELETE FROM vt_session WHERE session_id = ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, sessionId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting session", e.getMessage());
        }
    }
}