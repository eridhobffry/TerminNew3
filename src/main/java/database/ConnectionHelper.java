package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

        private String url;
        private static ConnectionHelper instance;

        public ConnectionHelper()
        {
            String driver = null;
            try {
                Class.forName("org.sqlite.JDBC");
                url = "jdbc:sqlite:db/TerminKalender.sqlite";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static Connection getConnection() throws SQLException {
            if (instance == null) {
                instance = new ConnectionHelper();
            }
            try {
                return DriverManager.getConnection(instance.url);
            } catch (SQLException e) {
                throw e;
            }
        }

        public static void close(Connection connection)
        {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}




