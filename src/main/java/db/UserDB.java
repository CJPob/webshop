package db;
import bo.UserRole;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Collection;
import java.util.Vector;

public class UserDB extends bo.User {

    private UserDB(int id, String name, String password, String username) {
        super(id, name, password, username, UserRole.CUSTOMER);
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

                users.add(new UserDB(id, name, password, retrievedUsername));
            }

            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Method to insert a user into the database
    public static boolean insertUser(String name, String username, String password) {
        // Hash the password using the private helper method
        String encryptedPassword = hashPassword(password);
        UserRole role = UserRole.CUSTOMER;  // Always assign the 'CUSTOMER' role for new users

        String query = "INSERT INTO T_USER (name, username, password, userRole) VALUES (?, ?, ?, ?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            // Set the query parameters
            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, encryptedPassword);
            ps.setString(4, role.name());  // Assign 'CUSTOMER' role to the user

            // Execute the query
            int result = ps.executeUpdate();
            return result > 0;  // Return true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

}
