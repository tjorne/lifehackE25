package app.entities;

import java.time.LocalDateTime;

public class VineTimeSession {

    private int sessionId;
    private int userId;
    private String sessionType;
    private int duration;
    private LocalDateTime timestamp;

    public VineTimeSession() {
    }

    public VineTimeSession(String sessionType, int sessionId, int userId, int duration, LocalDateTime timestamp) {
        this.sessionType = sessionType;
        this.sessionId = sessionId;
        this.userId = userId;
        this.duration = duration;
        this.timestamp = timestamp;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "VineTimeSession{" + "sessionId=" + sessionId + ", userId=" + userId + ", sessionType='" + sessionType + '\'' + ", duration=" + duration + ", timestamp=" + timestamp + '}';
    }
}