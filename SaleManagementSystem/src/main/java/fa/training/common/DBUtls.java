package fa.training.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtls {
    public static final String USER = "sa";
    public static final String PASSWORD = "1234";
    public static final String CONNECTION_URL =
            "jdbc:sqlserver://localhost:1433;databaseName=SMS;encrypt=true;trustServerCertificate=true;";

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
