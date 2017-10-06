package chat.Connection;

import chat.Connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bella
 */
public class SqlConnection implements ConnectionFactory {

    private final static String dbDriver = "com.mysql.jdbc.Driver";
    private final static String dbURL = "jdbc:mysql://localhost:3306/chat";
    private final static String user = "root";
    private final static String pass = "";

    public SqlConnection() {
    }

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        try {
            Class.forName(dbDriver);
            return DriverManager.getConnection(dbURL, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            ConnectionFactory cf = new SqlConnection();
            cf.getConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
