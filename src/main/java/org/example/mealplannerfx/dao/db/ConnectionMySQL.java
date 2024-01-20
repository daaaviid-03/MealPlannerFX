package org.example.mealplannerfx.dao.db;

import java.sql.*;

public class ConnectionMySQL {
    private String url = "jdbc:mysql://localhost:3306/mealplannerschema";
    private String user = "root";
    private String pas = "Password1234";
    private Connection connection;
    private Statement statement;
    private static ConnectionMySQL connectionMySQLInstance;
    public ConnectionMySQL(){
        connectionMySQLInstance = this;
    }

    public void startConnection(){
        try{
            // Load JDBC controller
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pas);
            if (connection != null) {
                statement = connection.createStatement();
            }
        } catch (Exception e) {
            System.err.println("Connection not found.");
        }
    }

    public void newQueryNoResult(String query){
        try {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet newQuery(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void endQuery(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void endConnection(){
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConnectionMySQL getConnectionMySQLInstance() {
        return connectionMySQLInstance;
    }
}
