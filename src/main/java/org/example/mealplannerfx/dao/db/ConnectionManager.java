package org.example.mealplannerfx.dao.db;

import java.sql.*;

/**
 * Class to manage the connection to the JDBC and ensure that there is only one connection at a time
 */
public class ConnectionManager {
    private static final String URL_RUTE = "jdbc:mysql://localhost:3306/mealplannerschema";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = System.getenv("PASSWORD");
    private Connection connection;
    private Statement statement;
    private static ConnectionManager connectionManagerInstance;

    private ConnectionManager(){
        startConnection();
    }

    /**
     * Starts the connection with the JDBC
     */
    public void startConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL_RUTE, USER_NAME, PASSWORD);
            statement = connection.createStatement();
        } catch (Exception e) {
            // No action
        }
    }

    /**
     * Execute a query that has no result set in the DB
     * @param query the query to execute
     */
    public void newQueryNoResult(String query){
        try {
            statement.execute(query);
        } catch (SQLException e) {
            // No action
        }
    }

    /**
     * Execute a query that has a result set in the DB
     * @param query the query to execute
     */
    public ResultSet newQuery(String query) throws SQLException{
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * End the result set of a query from the DB
     * @param resultSet the result set to end
     */
    public void endQuery(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            // No action
        }
    }

    /**
     * End the connection with the JDBC
     */
    public void endConnection(){
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            // No action
        }
    }

    /**
     * Get the instance of the Singleton object and creates it if there isn't one and starts connection
     * @return the instance of the Singleton object
     */
    public static ConnectionManager getConnectionManagerInstance() {
        if (connectionManagerInstance == null){
            connectionManagerInstance = new ConnectionManager();
        }
        return connectionManagerInstance;
    }
}
