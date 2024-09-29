package db;

import java.sql.Connection;
import java.sql.SQLException;

// Simply check if the connection to the DB works, note that ive forgot if mysql is limited to local or is it remote?
public class TestConnection {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBManager.getConnection();

        if (connection != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Failed to make connection.");
        }
    }
}