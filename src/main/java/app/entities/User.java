// Package
package app.entities;

public class User {

    // Attributes
    private int userId;
    private String userName;
    private String password;
    private String role;

    // ______________________________________________________________

    public User(int userId, String userName, String password, String role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    // ______________________________________________________________

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    // ______________________________________________________________

    public String getPassword() {
        return password;
    }

    // ______________________________________________________________

    public String getRole() {
        return role;
    }

    // ______________________________________________________________

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

} // User end