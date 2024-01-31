package org.example.mealplannerfx.dao.db;

import org.example.mealplannerfx.dao.DAOIngredient;
import org.example.mealplannerfx.entity.Ingredient;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAOIngredientDB extends DAOIngredient {
    /**
     * The connection to the server of the db
     */
    private final ConnectionManager connectionManager = ConnectionManager.getConnectionManagerInstance();

    @Override
    public List<Ingredient> getAllIngredientsAsRegex(String regex, Integer numberOfElements) {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT * FROM Ingredient WHERE ingredientName RLIKE '" + regex + "' ORDER BY ingredientName;";
        try (ResultSet resultSet = connectionManager.newQuery(query)){
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
            connectionManager.endQuery(resultSet);
        } catch (Exception e){
            return ingredients;
        }
        return ingredients;
    }

    @Override
    public void saveIngredient(Ingredient ingredient) {
        // Save the tuple into the table
        connectionManager.newQueryNoResult("INSERT INTO Ingredient (ingredientName, calories, carb, prot, fats, " +
                "category) VALUES ('" + ingredient.getName() + "', " + ingredient.getCalories() + ", " +
                ingredient.getCarbohydrates() + ", " + ingredient.getProteins() + ", " + ingredient.getFats() +
                ", '" + ingredient.getCategory() + "') ON DUPLICATE KEY UPDATE calories = " +
                ingredient.getCalories() + ", carb = " + ingredient.getCarbohydrates() + ", prot = " +
                ingredient.getProteins() + ", fats = " + ingredient.getFats() + ", category = '" +
                ingredient.getCategory() + "';");
        // Save all the food portions into the table
        for (String portionName : ingredient.getFoodPortionsNamesList()){
            float portionInGrams = ingredient.getFoodPortionInGrams(portionName);
            connectionManager.newQueryNoResult("INSERT INTO FoodPortions (ingredientName, portionName, quantity) " +
                    "VALUES ('" + ingredient.getName() + "', '" + portionName + "', " + portionInGrams +
                    ") ON DUPLICATE KEY UPDATE quantity = " + portionInGrams + ";");
        }
    }

    @Override
    public void saveIngredients(List<Ingredient> ingredientsToSave) {
        for (Ingredient ingredient : ingredientsToSave){
            saveIngredient(ingredient);
        }
    }

    private Map<String, Float> getPortionsFromIngredient(String name){
        Map<String, Float> portions = new HashMap<>();
        String query = "SELECT * FROM FoodPortions WHERE (ingredientName = '" + name + "');";
        try (ResultSet resultSet = connectionManager.newQuery(query)){
            while (resultSet.next()){
                String portionName = resultSet.getString("portionName");
                float quantity = resultSet.getFloat("quantity");
                portions.put(portionName, quantity);
            }
            connectionManager.endQuery(resultSet);
        } catch (Exception e){
            return portions;
        }
        return portions;
    }
}
