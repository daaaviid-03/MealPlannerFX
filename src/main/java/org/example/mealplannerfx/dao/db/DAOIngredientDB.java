package org.example.mealplannerfx.dao.db;

import org.example.mealplannerfx.dao.DAOIngredient;
import org.example.mealplannerfx.entity.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAOIngredientDB extends DAOIngredient {
    /**
     * The connection to the server of the db
     */
    private final ConnectionMySQL connectionMySQL = ConnectionMySQL.getConnectionMySQLInstance();

    @Override
    public List<Ingredient> getAllIngredientsAsRegex(String regex, Integer numberOfElements) {
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            String query = "SELECT * FROM Ingredient WHERE ingredientName RLIKE '" + regex + "' ORDER BY ingredientName;";
            ResultSet resultSet = connectionMySQL.newQuery(query);
            while (resultSet.next() && (numberOfElements == null || --numberOfElements >= 0)){
                String name = resultSet.getString("ingredientName");
                float calories = resultSet.getFloat("calories");
                float carbohydrates = resultSet.getFloat("carb");
                float proteins = resultSet.getFloat("prot");
                float fats = resultSet.getFloat("fats");
                String category = resultSet.getString("category");
                Map<String, Float> portions = getPortionsFromIngredient(name);
                Ingredient ingredient = new Ingredient(name,calories,carbohydrates,proteins,fats,category,portions);
                ingredients.add(ingredient);
            }
            connectionMySQL.endQuery(resultSet);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        return ingredients;
    }

    @Override
    public void saveIngredient(Ingredient ingredient, boolean newIngredient) {
        // Save the tuple into the table
        connectionMySQL.newQueryNoResult("INSERT INTO Ingredient (ingredientName, calories, carb, prot, fats, " +
                "category) VALUES ('" + ingredient.getName() + "', " + ingredient.getCalories() + ", " +
                ingredient.getCarbohydrates() + ", " + ingredient.getProteins() + ", " + ingredient.getFats() +
                ", '" + ingredient.getCategory() + "') ON DUPLICATE KEY UPDATE calories = " +
                ingredient.getCalories() + ", carb = " + ingredient.getCarbohydrates() + ", prot = " +
                ingredient.getProteins() + ", fats = " + ingredient.getFats() + ", category = '" +
                ingredient.getCategory() + "';");
        // Save all the food portions into the table
        for (String portionName : ingredient.getFoodPortionsNamesList()){
            float portionInGrams = ingredient.getFoodPortionInGrams(portionName);
            connectionMySQL.newQueryNoResult("INSERT INTO FoodPortions (ingredientName, portionName, quantity) " +
                    "VALUES ('" + ingredient.getName() + "', '" + portionName + "', " + portionInGrams +
                    ") ON DUPLICATE KEY UPDATE quantity = " + portionInGrams + ";");
        }
    }

    private Map<String, Float> getPortionsFromIngredient(String name){
        Map<String, Float> portions = new HashMap<>();
        try {
            String query = "SELECT * FROM FoodPortions WHERE (ingredientName = '" + name + "');";
            ResultSet resultSet = connectionMySQL.newQuery(query);
            while (resultSet.next()){
                String portionName = resultSet.getString("portionName");
                float quantity = resultSet.getFloat("quantity");
                portions.put(portionName, quantity);
            }
            connectionMySQL.endQuery(resultSet);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        return portions;
    }
}
