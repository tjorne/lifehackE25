package app.persistence.gratitudeJournal;

import app.entities.gratitudeJournal.JournalItem;
import app.persistence.ConnectionPool;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class JournalMapper {
    private static final ConnectionPool cp = ConnectionPool.getInstance();

    public long createLog(LocalDate date) throws SQLException {
        String sql = "INSERT INTO journal_log (log_date) VALUES (?) RETURNING log_id";
        try (Connection c = cp.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getLong(1);
            }
        }
    }

    public void addItems(long logId, List<JournalItem> items) throws SQLException {
        String sql = "INSERT INTO journal_item (log_id,item_type,position,content) VALUES (?,?,?,?)";
        try (Connection c = cp.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            c.setAutoCommit(false);
            try {
                for (JournalItem it : items) {
                    ps.setLong(1, logId);
                    ps.setString(2, it.getItemType().name());
                    ps.setInt(3, it.getPosition());
                    ps.setString(4, it.getContent());
                    ps.addBatch();
                }
                ps.executeBatch();
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    // evt. convenience-metode: opret log + items i én transaktion
    public static long createLogWithItems(LocalDate date, List<JournalItem> items) throws SQLException {
        try (Connection c = cp.getConnection()) {
            c.setAutoCommit(false);
            try {
                long logId;
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO journal_log (log_date) VALUES (?) RETURNING log_id")) {
                    ps.setDate(1, Date.valueOf(date));
                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();
                        logId = rs.getLong(1);
                    }
                }
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO journal_item (log_id,item_type,position,content) VALUES (?,?,?,?)")) {
                    for (JournalItem it : items) {
                        ps.setLong(1, logId);
                        ps.setString(2, it.getItemType().name());
                        ps.setInt(3, it.getPosition());
                        ps.setString(4, it.getContent());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
                c.commit();
                return logId;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }
}