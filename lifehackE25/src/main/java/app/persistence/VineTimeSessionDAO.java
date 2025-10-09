package app.persistence;

import app.entities.VineTimeSession;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VineTimeSessionDAO {

    private final ConnectionPool connectionPool;

    public VineTimeSessionDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public int create(VineTimeSession session) throws SQLException {
        if (session == null) {
            throw new IllegalArgumentException("Session cannot be null");
        }

        String sql = """
            INSERT INTO vt_sessions (user_id, session_type, duration_minutes, created_at)
            VALUES (?, ?, ?,?)
            RETURNING session_id
            """;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
                ps.setInt(1, session.
            getUserId());
                ps.setString(2
            , session.getSessionType());
                ps.setInt(3, session.getDuration());
                ps.setTimestamp(4, Timestamp

            .valueOf(session.getTimestamp()));
             
             try (ResultSet rs = ps.executeQuery()){
                return rs.next() ? rs.getInt(1) : 0;
             }
        }
    }
}