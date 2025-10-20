package app.persistence;

import app.entities.VineTimeSession;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class VineTimeSessionMapper {

    private VineTimeSessionMapper() {

    }

    public static VineTimeSession map(ResultSet rs) throws SQLException {
        int sessionId = rs.getInt("session_id");
        int userId = rs.getInt("user_id");
        String sessionType = rs.getString("session_type");
        int duration = rs.getInt("duration_minutes");
        Timestamp ts = rs.getTimestamp("created_at");
        LocalDateTime timestamp = ts != null ? ts.toLocalDateTime() : null;

        return new VineTimeSession(sessionType, sessionId, userId, duration, timestamp);
    }
}