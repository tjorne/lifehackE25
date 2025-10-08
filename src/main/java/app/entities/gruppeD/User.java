// Package
package app.entities.gruppeD;

// Imports
import org.mindrot.jbcrypt.BCrypt;
import java.time.LocalDateTime;

public class User {

    // Attributes
    private int id;
    private String username;
    private String email;
    private String passwordHash;
    private int roleId;
    private LocalDateTime createdAt;
    private boolean notifications;
    private String lang;

    // ______________________________________________________________

    public User(int id, String username, String email, String passwordHash, int roleId, LocalDateTime createdAt, boolean notifications, String language) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
        this.createdAt = createdAt;
        this.notifications = notifications;
        this.lang = language;
    }

    // __________________________________________________________

    public int getId() {
        return id;
    }

    // __________________________________________________________

    public void setId(int id) {
        this.id = id;
    }

    // __________________________________________________________

    public String getUsername() {
        return username;
    }

    // __________________________________________________________

    public void setUsername(String username) {
        this.username = username;
    }

    // __________________________________________________________

    public String getEmail() {
        return email;
    }

    // __________________________________________________________

    public void setEmail(String email) {
        this.email = email;
    }

    // __________________________________________________________

    public String getPasswordHash() {
        return passwordHash;
    }

    // __________________________________________________________

    public void setPasswordHash(String passwordHash) {
        String newpasswordHash = BCrypt.hashpw(passwordHash, BCrypt.gensalt());
        this.passwordHash = newpasswordHash;
    }

    // __________________________________________________________

    public int getRoleId() {
        return roleId;
    }

    // __________________________________________________________

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    // __________________________________________________________

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // __________________________________________________________

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // __________________________________________________________

    public boolean getNotifications() {
        return notifications;
    }

    // __________________________________________________________

    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }

    // __________________________________________________________

    public String getLanguage() {
        return lang;
    }

    // __________________________________________________________

    public void setLanguage(String language) {
        this.lang = language;
    }

    // __________________________________________________________

} // User end