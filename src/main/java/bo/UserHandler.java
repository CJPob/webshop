package bo;

import ui.UserInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The UserHandler class manages business logic related to users, providing methods for user retrieval, signup, login,
 * and role updates. It converts User objects to UserInfo for UI use.
 */

public class UserHandler {

    public static ArrayList<UserInfo> getUser(String username) {
        Collection<User> users = User.searchUser(username);
        ArrayList<UserInfo> userInfos = new ArrayList<>();
        for (User u : users) {
            userInfos.add(new UserInfo(u.getId(), u.getName(), u.getUsername(), u.getUserRole()));
        }
        return userInfos;
    }

    public static ArrayList<UserInfo> getAllUsers() {
        Collection<User> users = User.searchAllUsers();
        ArrayList<UserInfo> userInfos = new ArrayList<>();
        for (User user : users) {
            userInfos.add(new UserInfo(user.getId(), user.getName(), user.getUsername(), user.getUserRole()));
        }
        return userInfos;
    }

    public static boolean signupUser(String name, String username, String password) {
        return User.signup(name, username, password);
    }

    public static boolean loginUser(String username, String password) {
        return User.login(username, password);
    }

    public static boolean setNewUserRole(String username, UserRole newRole) {
        return User.updateUserRole(username, newRole);
    }
}