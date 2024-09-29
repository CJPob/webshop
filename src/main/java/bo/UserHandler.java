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
        boolean isUserInserted = UserDB.insertUser(name, username, password);

        return isUserInserted;
    }
}
