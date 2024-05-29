package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class MyConnection {
    
    private static Properties loadDatabaseProperties() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("../properties/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public static void setConnection(){
        Properties props = loadDatabaseProperties();
        String url = props.getProperty("db.url");
        String userDB = props.getProperty("db.user");
        String motDePasseDB = props.getProperty("db.password");

        
    }

}