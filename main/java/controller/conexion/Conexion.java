package controller.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String CREDENTIALS_FILE = "src/main/java/resources/db_credentials.json";
    private static DBCredentials dbCredentials = DBCredentials.fromJson(CREDENTIALS_FILE);

    private static final String URL = dbCredentials.getUrl();
    private static final String USER = dbCredentials.getUser();
    private static final String PASSWORD = dbCredentials.getPassword();

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}
