package app.entities;

import java.time.LocalDateTime;

public class VineTimeSession {
    private int sessionId;
    private int userId;
    private String sessionType;
    private int durationSeconds;
    private LocalDateTime completedAt;

    public VineTimeSession() {}

    public VineTimeSession(int userId, String sessionType, int durationSeconds, LocalDateTime completedAt) {
        this.userId = userId;
        this.sessionType = sessionType;
        this.durationSeconds = durationSeconds;
        this.completedAt = LocalDateTime.now();
    }

    public VineTimeSession(int sessionId, int userId, String sessionType, int durationSeconds, LocalDateTime completedAt) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.sessionType = sessionType;
        this.durationSeconds = durationSeconds;
        this.completedAt = completedAt;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public String toString() {
        return "VineTimeSession{" +
                "sessionId=" + sessionId +
                ", userId=" + userId +
                ", sessionType='" + sessionType + '\'' +
                ", duration=" + durationSeconds +
                ", timestamp=" + completedAt +
                '}';
    }
}