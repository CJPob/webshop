package db;

import bo.ShoppingCartHandler;
import bo.UserRole;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Collection;
import java.util.Vector;

/**
 * The UserDB class handles database operations for user accounts, including user search, insertion,
 * authentication, and role updates. It provides methods for managing user data such as creating new users,
 * checking credentials, and retrieving user roles.
 *
 * Key methods:
 * - {@code searchUser()}: Searches for users by username.
 * - {@code insertUser()}: Inserts a new user into the database.
 * - {@code checkUserCredentials()}: Verifies user login credentials.
 * - {@code updateUserRole()}: Updates the role of a user.
 */
public class UserDB extends bo.User {

    private UserDB(int id, String name, String username, String password, UserRole userRole) {
        super(id, name, username, password, userRole);
    }

    public static Collection<UserDB> searchUser(String username) {
        Vector<UserDB> users = new Vector<>();
        try (Connection con = DBManager.getConnection();
             Statement st = con.createStatement()) {
            String query = username == null || username.isEmpty() ?
                    "SELECT id, name, username, password, userRole FROM T_USER" :
                    "SELECT id, name, username, password, userRole FROM T_USER WHERE username = '" + username + "'";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                users.add(new UserDB(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        UserRole.valueOf(rs.getString("userRole"))
                ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static Collection<UserDB> searchAllUsers() {
        Vector<UserDB> users = new Vector<>();
        try (Connection con = DBManager.getConnection();
             Statement st = con.createStatement()) {
            String query = "SELECT id, name, username, password, userRole FROM T_USER";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                users.add(new UserDB(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        UserRole.valueOf(rs.getString("userRole"))
                ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static boolean insertUser(String name, String username, String password) {
        String encryptedPassword = hashPassword(password);
        UserRole role = UserRole.CUSTOMER;  // Default role
        String query = "INSERT INTO T_USER (name, username, password, userRole) VALUES (?, ?, ?, ?)";
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, encryptedPassword);
            ps.setString(4, role.name());
            int result = ps.executeUpdate();
            if (result > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    ShoppingCartHandler.createCartForUser(userId);
                    return true;
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getUserIdByUsername(String username) {
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT id FROM T_USER WHERE username = ?")) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean checkUserCredentials(String username, String password) {
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT password FROM T_USER WHERE username = ?")) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String hashedPassword = hashPassword(password);
                if (hashedPassword.equals(rs.getString("password"))) {
                    return true;
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to hash password", e);
        }
    }

    public static String getUserRoleByUserId(int userId) {
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT userRole FROM T_USER WHERE id = ?")) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getString("userRole");
                }
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateUserRole(String username, UserRole newRole) {
        String query = "UPDATE T_USER SET userRole = ? WHERE username = ?";
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, newRole.name());
            ps.setString(2, username);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
