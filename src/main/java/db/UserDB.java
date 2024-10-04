package db;
import bo.ShoppingCart;
import bo.ShoppingCartHandler;
import bo.UserRole;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Collection;
import java.util.Vector;

public class UserDB extends bo.User {

    private UserDB(int id, String name, String username, String password, UserRole userRole) {
        super(id, name, username, password, UserRole.CUSTOMER);
    }

    public static Collection<UserDB> searchUser(String username) {
        Vector<UserDB> users = new Vector<>();
        try {
            Connection con = DBManager.getConnection();
            Statement st = con.createStatement();

            String query;
            if (username == null || username.isEmpty()) {
                query = "SELECT id, name, password, username, userRole FROM T_USER";
            } else {
                query = "SELECT id, name, password, username, userRole FROM T_USER WHERE username = '" + username + "'";
            }

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String retrievedUsername = rs.getString("username");
                UserRole userRole = UserRole.valueOf(rs.getString("userRole"));

                users.add(new UserDB(id, name, retrievedUsername, password, userRole));
            }

            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static Collection<UserDB> searchAllUsers() {
        Vector<UserDB> users = new Vector<>();
        try {
            Connection con = DBManager.getConnection();
            Statement st = con.createStatement();

            // Query to fetch all users
            String query = "SELECT id, name, password, username, userRole FROM T_USER";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String retrievedUsername = rs.getString("username");
                UserRole userRole = UserRole.valueOf(rs.getString("userRole"));

                // Add each user to the collection
                users.add(new UserDB(id, name, retrievedUsername, password, userRole));
            }

            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static boolean insertUser(String name, String username, String password) {
        String encryptedPassword = hashPassword(password);
        UserRole role = UserRole.CUSTOMER;
        String query = "INSERT INTO T_USER (name, username, password, userRole) VALUES (?, ?, ?, ?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, encryptedPassword);
            ps.setString(4, role.name());

            int result = ps.executeUpdate();  // Execute the query

            if (result > 0) {
                // Fetch the newly created user's ID
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);  // Get the user ID

                    // Now create a shopping cart for the user using the ShoppingCartHandler
                    return ShoppingCartHandler.createCartForUser(userId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Return false if user insertion failed
    }

    public static int getUserIdByUsername(String username) {
        int userId = -1;  // Default value if no user is found
        try {
            Connection con = DBManager.getConnection();
            String query = "SELECT id FROM T_USER WHERE username = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("id");  // Get userId from result set
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;  // Return the userId or -1 if not found
    }



    // Method to check user credentials during login
    public static boolean checkUserCredentials(String username, String password) {
        boolean loginSuccess = false;
        try {
            Connection con = DBManager.getConnection();
            String query = "SELECT password FROM T_USER WHERE username = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");

                // Hash the provided password using the private helper method
                String hashedPassword = hashPassword(password);

                // Compare the stored hashed password with the hashed input password
                if (storedPassword.equals(hashedPassword)) {
                    loginSuccess = true;  // Successful login
                }
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loginSuccess;
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = messageDigest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();

            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));  // Convert each byte to a hex string
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static String getUserRoleByUserId(int userId) {
        String role = null;
        String query = "SELECT userRole FROM T_USER WHERE id = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                role = rs.getString("userRole");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return role;
    }
}
