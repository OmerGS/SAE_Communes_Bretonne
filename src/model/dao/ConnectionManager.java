package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Manages database connections using JDBC.
 * Provides methods to retrieve, initialize, and close database connections.
 * This class loads database configuration from a properties file located at "../properties/config.properties".
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * Connection connection = ConnectionManager.getConnection();
 * // Perform database operations using 'connection'
 * ConnectionManager.closeConnection();
 * }
 * </pre>
 * 
 * @author O.Gunes
 */
public class ConnectionManager {
    /**
     * Connection instance.
     */
    private static Connection connection;

    /**
     * Retrieves a connection to the database.
     * Initializes a new connection if none exists or if the existing connection is closed.
     *
     * @return a Connection object representing the database connection
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            initializeConnection();
        }
        return connection;
    }

    /**
     * Initializes the database connection using properties loaded from the configuration file.
     *
     * @throws SQLException if a database access error occurs or the connection URL is invalid
     */
    private static void initializeConnection() throws SQLException {
        try {
            Properties props = loadDatabaseProperties();
            String url = props.getProperty("db.url");
            String userDB = props.getProperty("db.user");
            String motDePasseDB = props.getProperty("db.password");

            connection = DriverManager.getConnection(url, userDB, motDePasseDB);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SQLException("Failed to initialize database connection: " + e.getMessage());
        }
    }

    /**
     * Closes the database connection.
     * If the connection is not null, it is closed; otherwise, no action is taken.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads database properties from the configuration file.
     *
     * @return Properties object containing database configuration
     * @throws IOException if an I/O error occurs while reading the configuration file
     */
    private static Properties loadDatabaseProperties() throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("../properties/config.properties")) {
            props.load(fis);
        }
        return props;
    }
}
