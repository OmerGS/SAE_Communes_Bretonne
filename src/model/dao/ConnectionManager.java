package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static Connection connection;

    // Méthode pour récupérer une connexion à la base de données
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            initializeConnection();
        }
        return connection;
    }

    // Méthode pour initialiser la connexion à la base de données
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

    // Méthode pour fermer la connexion à la base de données
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Méthode pour charger les propriétés de la base de données à partir du fichier de configuration
    private static Properties loadDatabaseProperties() throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("../properties/config.properties")) {
            props.load(fis);
        }
        return props;
    }
}
