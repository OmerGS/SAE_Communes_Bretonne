package test;
import java.sql.*;

public class DatabaseConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://omergs:57800/communeBretonne";
        String username = "omer";
        String password = "omerSAE27";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
