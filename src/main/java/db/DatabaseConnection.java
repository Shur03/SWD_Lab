package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:postgresql://dpg-ctfh3f1u0jms739mui7g-a.oregon-postgres.render.com:5432/banktrans";
    private static final String DB_USER = "banktrans_user";
    private static final String DB_PASSWORD = "JfoJw7x7jGABwuiHmqZAEn9KzTvj5ZFc";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
