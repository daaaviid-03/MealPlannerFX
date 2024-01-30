package org.example.mealplannerfx.dao.db;

import org.example.mealplannerfx.control.WrongArgumentException;
import org.example.mealplannerfx.dao.DAOIngredient;
import org.example.mealplannerfx.dao.DAORecipe;
import org.example.mealplannerfx.dao.DAORecipeMaxId;
import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAORecipeMaxIdDB extends DAORecipeMaxId {
    /**
     * The connection to the server of the db
     */
    private final ConnectionMySQL connectionMySQL = ConnectionMySQL.getConnectionMySQLInstance();

    @Override
    public long getNextAndAddRecipeMaxId() {
        long nextRedipeId = -1L;
        try {
            String query = "SELECT * FROM MaxRecipeId;";
            ResultSet resultSet = connectionMySQL.newQuery(query);
            if (resultSet.next()){
                nextRedipeId = resultSet.getLong("maxRecipeIdLong") + 1;
                connectionMySQL.endQuery(resultSet);
                connectionMySQL.newQueryNoResult("UPDATE MaxRecipeId SET maxRecipeIdLong = " + nextRedipeId +
                        " WHERE maxRecipeIdLong = " + (nextRedipeId - 1) + ";");
            } else {
                connectionMySQL.endQuery(resultSet);
                connectionMySQL.newQueryNoResult("INSERT INTO MaxRecipeId (maxRecipeIdLong) VALUES (-1);");
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        return nextRedipeId;
    }
}
