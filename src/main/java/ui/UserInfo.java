package ui;

import bo.User;

public class UserInfo {
    private int id;
    private String name;
    private String username;
    private String userRole;

    public UserInfo(int id, String username, String name, User.UserRole userRole) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.userRole = userRole.toString();
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getUsername() { return username; }

    public String getUserRole() { return userRole; }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
