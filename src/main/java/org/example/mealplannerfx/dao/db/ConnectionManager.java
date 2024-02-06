package org.example.mealplannerfx.dao.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage the connection to the JDBC and ensure that there is only one connection at a time
 */
public class ConnectionManager {
    private static final String URL_RUTE = "jdbc:mysql://localhost:3306/mealplannerschema";
    private static final String USER_NAME = "root";
    private static final String PASSWORD =  "Password1234"; //System.getenv("PASSWORD");
    private static Connection connection;
    private static final List<Statement> statementList = new ArrayList<>();

    private ConnectionManager(){}

    /**
     * Starts the connection with the JDBC
     */
    public static void startConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL_RUTE, USER_NAME, PASSWORD);
        } catch (Exception e) {
            // No action
        }
    }

    /**
     * Execute a query that has no result set in the DB
     * @param query the query to execute
     */
    public static void newQueryNoResult(String query){
        try (Statement statement = connection.createStatement()){
            statement.execute(query);
        } catch (SQLException e) {
            // No action
        }
    }

    /**
     * Execute a query that has a result set in the DB
     * @param query the query to execute
     */
    public static ResultSet newQuery(String query) throws SQLException{
        try {
            statementList.add(connection.createStatement());
            return statementList.getLast().executeQuery(query);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /**
     * End the result set of a query from the DB
     * @param resultSet the result set to end
     */
    public static void endQuery(ResultSet resultSet) {
        try {
            resultSet.close();
            statementList.removeLast().close();
        } catch (SQLException e) {
            // No action
        }
    }

    /**
     * End the connection with the JDBC
     */
    public static void endConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            // No action
        }
    }
}
