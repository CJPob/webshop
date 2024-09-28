package bo;

import db.UserDB;

import java.util.Collection;

public class User {
    private int id;
    private String name;
    private String password;
    private String username;
    private UserRole userRole;


    static public Collection searchUser(String username){
        return UserDB.searchUser(username);
    }

    protected User(int id, String name,String password, String username, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}