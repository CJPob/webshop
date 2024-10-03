package db;

import bo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDB extends bo.User {

    // Private constructor to ensure control over object creation
    private UserDB(int id, String name, String password, String username, UserRole role) {
        super(id, name, password, username, role, null, new ArrayList<>());
    }

    // Factory method to create UserDB from ResultSet
    private static UserDB createUserDB(ResultSet rs) throws SQLException {
        return new UserDB(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getString("username"),
                UserRole.valueOf(rs.getString("userRole"))
        );
    }

    public static List<User> searchUser(String username) {
        List<User> users = new ArrayList<>();
        String query = username == null || username.isEmpty()
                ? "SELECT id, name, password, username, userRole FROM T_USER"
                : "SELECT id, name, password, username, userRole FROM T_USER WHERE username = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            if (username != null && !username.isEmpty()) {
                stmt.setString(1, username);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(createUserDB(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static List<User> getAllUsers() {
        return searchUser(null);  // Fetch all users
    }

    public static boolean insertUser(String name, String username, String password) {
        String query = "INSERT INTO T_USER (name, username, password, userRole) VALUES (?, ?, ?, ?)";
        try (Connection con = DBManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, UserRole.CUSTOMER.name());
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkUserCredentials(String username, String password) {
        String query = "SELECT id, name, password, username, userRole FROM T_USER WHERE username = ? AND password = ?";
        try (Connection con = DBManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;  // Successful login, basic check
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateUserRole(String username, UserRole userRole) {
        String query = "UPDATE T_USER SET userRole = ? WHERE username = ?";
        try (Connection con = DBManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, userRole.name());
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
