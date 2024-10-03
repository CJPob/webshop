package bo;

import db.UserDB;
import ui.UserInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class UserHandler {

    public static ArrayList<UserInfo> getUser(String username) {
        Collection<User> users = User.searchUser(username);
        ArrayList<UserInfo> userInfos = new ArrayList<>();
        for (User u : users) {
            userInfos.add(new UserInfo(u.getId(), u.getName(), u.getUsername(), u.getUserRole()));  // Converting User to UserInfo
        }
        return userInfos;
    }

    public static ArrayList<UserInfo> getAllUsers() {
        Collection<User> users = User.searchAllUsers();
        ArrayList<UserInfo> userInfos = new ArrayList<>();

        for (User user : users) {
            userInfos.add(new UserInfo(user.getId(), user.getUsername(), user.getName(), user.getUserRole()));
        }

        return userInfos;
    }

    public static boolean signupUser(String name, String username, String password) {
        return User.signup(name, username, password);
    }

    public static boolean loginUser(String username, String password) {
        return User.login(username, password);
    }

    // TODO create a convert method here

}