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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAORecipeDB extends DAORecipe {
    /**
     * The connection to the server of the db
     */
    private final ConnectionMySQL connectionMySQL = ConnectionMySQL.getConnectionMySQLInstance();

    @Override
    public Recipe getRecipe(Long id) {
        if (id == null){
            return null;
        }
        Recipe thisRecipe = null;
        try {
            String query = "SELECT * FROM Recipe WHERE recipeId = " + id + ";";
            ResultSet resultSet = connectionMySQL.newQuery(query);
            if (resultSet.next()){
                thisRecipe = getRecipeFromResultSet(resultSet);
            }
            connectionMySQL.endQuery(resultSet);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        return thisRecipe;
    }

    @Override
    public List<Recipe> getAllRecipesAs(String regexName, Integer duration, Boolean toBeGraterEqualDuration,
                                        Boolean toBeLowerEqualDuration, List<Ingredient> ingredients,
                                        Boolean allOfThoseIngredients, Boolean allFieldsInCommon, User thisUser,
                                        Integer numberOfElements) throws WrongArgumentException {
        String query = "SELECT recipeName FROM Recipe WHERE ((";
        if (thisUser != null){
            query += "ownerNickname = '" + thisUser.getNickname() + "') AND (";
        }
        query += "recipeName RLIKE '" + regexName + "'";
        String conjuntionAll = " OR ";
        if (allFieldsInCommon){
            conjuntionAll = " AND ";
        }
        String conjuntionIngredients = " OR ";
        if (allOfThoseIngredients){
            conjuntionIngredients = " AND ";
        }
        if (duration != null){
            if (toBeGraterEqualDuration){
                query += conjuntionAll + "duration >= " + duration;
            } else {
                query += conjuntionAll + "duration < " + duration;
            }
            if (toBeLowerEqualDuration){
                query += conjuntionAll + "duration <= " + duration;
            } else {
                query += conjuntionAll + "duration > " + duration;
            }
        }
        for (int i = 0; i < ingredients.size(); i++) {
            if (i == 0){
                query += conjuntionAll + "(";
            }
            query += "recipeId IN (SELECT recipeId FROM IngredientInRecipe WHERE ingredientName = '" +
                    ingredients.get(i).getName() + "')";
            if (i < ingredients.size() - 1){
                query += conjuntionIngredients;
            } else {
                query += ")";
            }
        }
        query += ")) ORDER BY recipeName;";
        List<Recipe> recipes = new ArrayList<>();
        try {
            ResultSet resultSet = connectionMySQL.newQuery(query);
            while (resultSet.next() && (numberOfElements == null || --numberOfElements >= 0)){
                recipes.add(getRecipeFromResultSet(resultSet));
            }
            connectionMySQL.endQuery(resultSet);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        if (recipes.isEmpty()){
            throw new WrongArgumentException("No recipe matches with that filters.");
        }
        return recipes;
    }

    @Override
    public void deleteAllRecipesFrom(String nick, Long recipeId) {
        if (recipeId != null){
            deleteRecipe(recipeId);
        } else {
            connectionMySQL.newQueryNoResult("DELETE FROM Recipe WHERE (ownerNickname = " + nick + ");");
        }
    }

    private Recipe getRecipeFromResultSet(ResultSet resultSet) throws SQLException {
        long recipeId = resultSet.getLong("recipeId");
        String name = resultSet.getString("recipeName");
        String description = resultSet.getString("descr");
        String owner = resultSet.getString("ownerNickname");
        int durationVal = resultSet.getInt("duration");
        List<String> steps = getStepsFromRecipe(recipeId);
        Recipe thisRecipe = new Recipe(recipeId, name, description, owner, steps, durationVal);
        loadIngredientsInRecipe(thisRecipe);
        return thisRecipe;
    }
    private void loadIngredientsInRecipe(Recipe recipe) {
        try {
            String query = "SELECT * FROM IngredientInRecipe WHERE (recipeId = " + recipe.getId() + ");";
            ResultSet resultIngredientInRecipe = connectionMySQL.newQuery(query);
            List<Ingredient> ingredientList = new ArrayList<>();
            List<Float> ingredientsQuantityList = new ArrayList<>();
            List<String> ingredientsPortionsNamesList = new ArrayList<>();
            while (resultIngredientInRecipe.next()){
                String ingredientName = resultIngredientInRecipe.getString("ingredientName");
                String portionName = resultIngredientInRecipe.getString("portionName");
                Float quantity = resultIngredientInRecipe.getFloat("quantity");
                ingredientList.add(DAOIngredient.getDaoIngredientInstance().getIngredient(ingredientName));
                ingredientsQuantityList.add(quantity);
                ingredientsPortionsNamesList.add(portionName);
            }
            connectionMySQL.endQuery(resultIngredientInRecipe);
            recipe.setIngredients(ingredientList);
            recipe.setIngredientsQuantity(ingredientsQuantityList);
            recipe.setIngredientsPortionsNames(ingredientsPortionsNamesList);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private List<String> getStepsFromRecipe(long recipeId) {
        List<String> stepsList = new ArrayList<>();
        try {
            String query = "SELECT stepDescription FROM StepsInRecipe WHERE (recipeId = " + recipeId + ") ORDER BY stepPosition ASC;";
            ResultSet resultSteps = connectionMySQL.newQuery(query);
            while (resultSteps.next()){
                String step = resultSteps.getString("stepDescription");
                stepsList.add(step);
            }
            connectionMySQL.endQuery(resultSteps);
            return stepsList;
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        return stepsList;
    }

    @Override
    public void saveRecipe(Recipe recipe, boolean newRecipe) {
        connectionMySQL.newQueryNoResult("INSERT INTO Recipe (recipeId, recipeName, descr, ownerNickname, duration) VALUES (" +
                recipe.getId() + ", '" + recipe.getName() + "', '" + recipe.getDescription() + "', '" +
                recipe.getOwner() + "', " + recipe.getDuration() + ") ON DUPLICATE KEY UPDATE recipeName = '" +
                recipe.getName() + "', descr = '" + recipe.getDescription() + "', duration = " + recipe.getDuration() + ";");
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            connectionMySQL.newQueryNoResult("INSERT INTO IngredientInRecipe (recipeId, ingredientName, portionName, quantity) VALUES (" +
                    recipe.getId() + ", '" + recipe.getIngredientInPos(i) + "', '" + recipe.getIngredientPortionNameInPos(i) + "', " +
                    recipe.getIngredientQuantityInPos(i) + ") ON DUPLICATE KEY UPDATE ingredientName = '" +
                    recipe.getIngredientInPos(i) + "', portionName = '" + recipe.getIngredientPortionNameInPos(i) +
                    "', quantity = " + recipe.getIngredientQuantityInPos(i) + ";");
        }
        for (int i = 0; i < recipe.getSteps().size(); i++) {
            connectionMySQL.newQueryNoResult("INSERT INTO StepsInRecipe (recipeId, stepPosition, stepDescription) VALUES (" +
                    recipe.getId() + ", " + i + ", '" + recipe.getStepInPos(i) + "') ON DUPLICATE KEY UPDATE stepDescription = '" +
                    recipe.getStepInPos(i) + "';");
        }
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        connectionMySQL.newQueryNoResult("DELETE FROM Recipe WHERE (recipeId = " + recipeId + ");");
    }
}
