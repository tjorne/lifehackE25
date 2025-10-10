    private int userId;
    private String userName;
    private String password;
    private String role;

    public User(int userId, String userName, String password, String role)
    public User(int userId, String userName, String password)
    {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public int getUserId()
@@ -30,19 +28,13 @@ public String getPassword()
        return password;
    }

    public String getRole()
    {
        return role;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
