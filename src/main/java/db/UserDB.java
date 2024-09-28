package db;

import bo.User;
import bo.UserRole;

import java.sql.*;
import java.util.Collection;
import java.util.Vector;

public class UserDB extends bo.User {

    public static Collection<UserDB> searchUser(String username) {
        Vector<UserDB> users = new Vector<>();
        try {
            Connection con = DBManager.getConnection();  // Assuming DBManager handles the database connection
            Statement st = con.createStatement();

            String query;
            // Fetch all users if the username is empty or null
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

                users.add(new UserDB(id, name, password, retrievedUsername, userRole));
            }

            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    private UserDB(int id, String name, String password, String username, UserRole userRole) {
        super(id, name, password, username, userRole);
    }
}
