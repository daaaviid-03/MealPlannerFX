package org.example.mealplannerfx.dao.db;

import org.example.mealplannerfx.dao.DAORecipeMaxId;

import java.sql.ResultSet;

public class DAORecipeMaxIdDB extends DAORecipeMaxId {
    /**
     * The connection to the server of the db
     */
    private final ConnectionManager connectionManager = ConnectionManager.getConnectionManagerInstance();

    @Override
    public long getNextAndAddRecipeMaxId() {
        long nextRecipeId = -1L;
        String query = "SELECT * FROM MaxRecipeId;";
        try (ResultSet resultSet = connectionManager.newQuery(query)){
            if (resultSet.next()){
                nextRecipeId = resultSet.getLong("maxRecipeIdLong") + 1;
                connectionManager.endQuery(resultSet);
                connectionManager.newQueryNoResult("UPDATE MaxRecipeId SET maxRecipeIdLong = " + nextRecipeId +
                        " WHERE maxRecipeIdLong = " + (nextRecipeId - 1) + ";");
            } else {
                connectionManager.endQuery(resultSet);
                connectionManager.newQueryNoResult("INSERT INTO MaxRecipeId (maxRecipeIdLong) VALUES (-1);");
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        return nextRecipeId;
    }
}
