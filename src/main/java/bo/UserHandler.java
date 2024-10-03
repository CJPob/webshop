package bo;

import db.UserDB;
import ui.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserHandler {


    public static ArrayList<UserInfo> getUser(String username) {
        List<User> users = UserDB.searchUser(username);
        ArrayList<UserInfo> userInfos = new ArrayList<>();

        for (User user : users) {
            userInfos.add(new UserInfo(user.getId(), user.getUsername(), user.getName(), user.getUserRole()));
        }

        return userInfos;
    }

    public static boolean signupUser(String name, String username, String password) {
        return UserDB.insertUser(name, username, password);
    }

    public static UserInfo loginUser(String username, String password) {
        boolean isValidUser = UserDB.checkUserCredentials(username, password);
        if (isValidUser) {
            List<User> users = UserDB.searchUser(username);
            if (!users.isEmpty()) {
                User user = users.get(0);
                return new UserInfo(user.getId(), user.getUsername(), user.getName(), user.getUserRole());
            }
        }
        return null;
    }

    public static ArrayList<UserInfo> getAllUsers() {
        List<User> users = UserDB.getAllUsers();
        ArrayList<UserInfo> userInfos = new ArrayList<>();

        for (User user : users) {
            userInfos.add(new UserInfo(user.getId(), user.getUsername(), user.getName(), user.getUserRole()));
        }

        return userInfos;
    }

    public static boolean setUserRole(String username, String roleName) {
        User.UserRole userRole;
        try {
            userRole = User.UserRole.valueOf(roleName.toUpperCase());
            return UserDB.updateUserRole(username, userRole);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid role specified: " + roleName);
            return false;
        }
    }
}
