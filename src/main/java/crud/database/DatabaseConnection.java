package crud.database;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String URL = dotenv.get("PATH_SQL");
    private static final String USER = dotenv.get("USER_SQL");
    private static final String PASSWORD = dotenv.get("PASSWORD_USER_SQL");

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
