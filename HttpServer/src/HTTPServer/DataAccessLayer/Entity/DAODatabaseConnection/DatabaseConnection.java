package HTTPServer.DataAccessLayer.Entity.DAODatabaseConnection;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DATABASEDRIVER = "com.mysql.jdbc.Driver";

    private static final String DATABASEURL = "jdbc:mysql://104.224.164.163:3306/chat";//192.168.1.150:3306/chat 104.224.164.163:3306
    private static final String DATABASEUSER = "root";//
    private static final String DATABASEPASSWORD = "Dengkangjun5!";//Dengkangjun5!
    private static Connection connection = null;

    static {
        try {
            Class.forName(DATABASEDRIVER);

            connection = DriverManager.getConnection(DATABASEURL, DATABASEUSER, DATABASEPASSWORD);
        }
        catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
