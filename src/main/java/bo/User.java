package bo;

import db.UserDB;

import java.util.Collection;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private ShoppingCart shoppingCart;
    private UserRole userRole;

    // searches should be for admin only
    static public Collection searchUser(String username){
        return UserDB.searchUser(username);
    }

    static public Collection searchAllUsers(){
        return UserDB.searchAllUsers();
    }

    static public boolean signup(String name, String username, String password){
        return UserDB.insertUser(name, username, password);
    }

    static public boolean login(String username, String password){
        return UserDB.checkUserCredentials(username, password);
    }

    protected User(int id, String name, String username, String password, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.shoppingCart = new ShoppingCart(this.id);
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

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
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

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}