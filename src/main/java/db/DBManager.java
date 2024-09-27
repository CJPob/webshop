package db;

import java.sql.Connection;
import java.sql.DriverManager;

//THIS is singleton
public class DBManager {
    private static DBManager instance = null;
    private Connection connection;

    private static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_webshop", "root", "password");
        } catch (Exception e) {e.printStackTrace();}
    }

    public static Connection getConnection() {
        return getInstance().connection;
    }
}
