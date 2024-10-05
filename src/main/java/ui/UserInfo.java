package ui;

import bo.UserRole;

/**
 * The Userinfo reveals the infromation of a ser to the ui. Used both by users and admins.
 */

public class UserInfo {
    private int id;
    private String name;
    private String username;
    private UserRole userRole;

    public UserInfo(int userId, String name, String username, UserRole userRole) {
        this.id = userId;
        this.username = username;
        this.name = name;
        this.userRole = userRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public int getId() {
        return this.id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
