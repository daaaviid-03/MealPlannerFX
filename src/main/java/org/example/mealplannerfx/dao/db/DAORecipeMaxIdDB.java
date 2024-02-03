package org.example.mealplannerfx.dao.db;

import org.example.mealplannerfx.dao.DAORecipeMaxId;

import java.sql.ResultSet;

public class DAORecipeMaxIdDB extends DAORecipeMaxId {

    @Override
    public long getNextAndAddRecipeMaxId() {
        long nextRecipeId = -1L;
        String query = "SELECT * FROM MaxRecipeId;";
        try (ResultSet resultSet = ConnectionManager.newQuery(query)){
            if (resultSet.next()){
                nextRecipeId = resultSet.getLong("maxRecipeIdLong") + 1;
                ConnectionManager.endQuery(resultSet);
                ConnectionManager.newQueryNoResult("UPDATE MaxRecipeId SET maxRecipeIdLong = " + nextRecipeId +
                        " WHERE maxRecipeIdLong = " + (nextRecipeId - 1) + ";");
            } else {
                ConnectionManager.endQuery(resultSet);
                ConnectionManager.newQueryNoResult("INSERT INTO MaxRecipeId (maxRecipeIdLong) VALUES (-1);");
            }
        } catch (Exception e){
            return nextRecipeId;
        }
        return nextRecipeId;
    }
}
