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
            userInfos.add(new UserInfo(u.getUsername()));  // Converting User to UserInfo
        }

        return userInfos;
    }

    public static boolean signupUser(String name, String username, String password) {
        return UserDB.insertUser(name, username, password);
    }

    public static boolean loginUser(String username, String password) {
        boolean loginSuccess = false;
        try {
            loginSuccess = UserDB.checkUserCredentials(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return loginSuccess;  // Return true if login is successful, false otherwise
    }
}