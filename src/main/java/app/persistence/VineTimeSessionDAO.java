package app.persistence;

import app.entities.VineTimeSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                INSERT INTO vt_session (user_id, session_type)
                VALUES (?, ?)
                RETURNING session_id
                """;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, session.
                    getUserId());
            ps.setString(2, session.getSessionType());
            ps.setInt(3, session.getDuration());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }
}