package db;

import bo.User;
import bo.UserRole;

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

    public static boolean insertUser(String name, String username, String password) {
        boolean success = false;
        try {
            Connection con = DBManager.getConnection();  // Assuming DBManager provides connection
            String query = "INSERT INTO T_USER (name, username, password, userRole) VALUES (?, ?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, UserRole.CUSTOMER.name());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                success = true;
            }

            pstmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }


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

                if (storedPassword.equals(password)) {
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


}
