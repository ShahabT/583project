package queryManager;

import java.sql.*;

public class QueryExecutor {
    Connection connection;
    Statement statement;

    public static QueryExecutor getInstance(String connectionStr, String username, String password) throws Exception {
        try {
            return new QueryExecutor(connectionStr, username, password);
        } catch (ClassNotFoundException e) {
            throw new Exception("Could not find MySQL driver class. don't forget to add mysq-connector.jar into the classpath");
        } catch (SQLException e) {
            throw new Exception("Could not connect to MySQL. Make sure you are providing the correct connection string, username and password.");
        }
    }

    private QueryExecutor(String connectionStr, String username, String password) throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connectionStr, username, password);
            statement = connection.createStatement();
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        return statement.executeQuery(sql);
    }

    public int executeUpdate(String sql) throws SQLException {
        return statement.executeUpdate(sql);
    }
}
