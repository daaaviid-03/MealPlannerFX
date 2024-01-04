package org.example.mealplannerfx;

import java.sql.*;

public class ConnectionMySQL {
    private String url = "jdbc:mysql://localhost:3306/db_name"; // --------------
    private String user = "root"; // --------------
    private String pas = "password"; // --------------
    private Connection connection;
    private Statement statement;

    public void startConnection(){
        try{
            // Load JDBC controller
            Class.forName("com.mysql.cj.jdbc.Driver"); // --------------
            connection = DriverManager.getConnection(url, user, pas);
            if (connection != null) {
                //System.out.println("Correct connection with DB");
                // Create statement obj
                statement = connection.createStatement();
            }
        } catch (ClassNotFoundException | SQLException e) {
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


}
