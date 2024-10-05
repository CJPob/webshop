package db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The DBManager class manages the database connection pool using HikariCP.
 * It implements a singleton connection to the database
 */

public class DBManager {
    private static DBManager instance = null;
    private HikariDataSource dataSource;

    private DBManager() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/my_webshop");
        config.setUsername("root");
        config.setPassword("password");
        dataSource = new HikariDataSource(config);
    }

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().dataSource.getConnection();
    }
}

