package Connection;

import java.sql.*;
import java.util.logging.Logger;

public class DatabaseConnection {

    private static final Logger LOGGER=Logger.getLogger(DatabaseConnection.class.getName());
    private static final String DRIVER="com.mysql.cj.jdbc.Driver";
    private static final String DB_URL="jdbc:mysql://localhost:3306/warehouse";
    private static final String USER="root";
    private static final String PASS="root";

    private static DatabaseConnection singleInstance=new DatabaseConnection();

    public DatabaseConnection() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection()
    {
        Connection con=null;
        try {
             con=DriverManager.getConnection(
                    DB_URL,USER,PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return con;
    }
    public static Connection getConnection()
    {
        return singleInstance.createConnection();
    }

    public static void closeConnection(Connection c)
    {
        try {
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeStatement(PreparedStatement s)
    {
        try {
            s.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet r)
    {
        try {
            r.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

