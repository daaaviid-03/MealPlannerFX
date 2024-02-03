package org.example.mealplannerfx.dao.db;

import org.example.mealplannerfx.control.WrongArgException;
import org.example.mealplannerfx.dao.DAOIngredient;
import org.example.mealplannerfx.dao.DAORecipe;
import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAORecipeDB extends DAORecipe {
    /**
     * The connection to the server of the db
     */
    private final ConnectionManager connectionManager = ConnectionManager.getConnectionManagerInstance();

    @Override
    public Recipe getRecipe(Long id) {
        if (id == null){
            return null;
        }
        Recipe thisRecipe = null;
        String query = "SELECT * FROM Recipe WHERE recipeId = " + id + ";";
        try (ResultSet resultSet = connectionManager.newQuery(query)){
            if (resultSet.next()){
                thisRecipe = getRecipeFromResultSet(resultSet);
            }
            connectionManager.endQuery(resultSet);
        } catch (Exception e){
            return null;
        }
        return thisRecipe;
    }

    @Override
    public List<Recipe> getAllRecipesAs(String regexName, Integer duration, List<Ingredient> ingredients, User thisUser, boolean[] checkers) throws WrongArgException {
        String query = getQuery(regexName, duration, ingredients, thisUser, checkers);
        List<Recipe> recipes = new ArrayList<>();
        try (ResultSet resultSet = connectionManager.newQuery(query)){
            while (resultSet.next()){
                recipes.add(getRecipeFromResultSet(resultSet));
            }
            connectionManager.endQuery(resultSet);
        } catch (Exception e){
            return recipes;
        }
        if (recipes.isEmpty()){
            throw new WrongArgException("No recipe matches with that filters.");
        }
        return recipes;
    }

    private static String getQuery(String regexName, Integer duration, List<Ingredient> ingredients, User thisUser,
                                   boolean[] checkers) {
        StringBuilder query = new StringBuilder("SELECT recipeName FROM Recipe WHERE ((");
        if (thisUser != null){
            query.append("ownerNickname = '").append(thisUser.getNickname()).append("') AND (");
        }
        query.append("recipeName RLIKE '").append(regexName).append("'");
        String conjunctionAll = " OR ";
        if (checkers[0]){
            conjunctionAll = " AND ";
        }
        String conjunctionIngredients = " OR ";
        if (checkers[1]){
            conjunctionIngredients = " AND ";
        }
        if (duration != null){
            if (checkers[2]){
                query.append(conjunctionAll).append("duration >= ").append(duration);
            } else {
                query.append(conjunctionAll).append("duration < ").append(duration);
            }
            if (checkers[3]){
                query.append(conjunctionAll).append("duration <= ").append(duration);
            } else {
                query.append(conjunctionAll).append("duration > ").append(duration);
            }
        }
        for (int i = 0; i < ingredients.size(); i++) {
            if (i == 0){
                query.append(conjunctionAll).append("(");
            }
            query.append("recipeId IN (SELECT recipeId FROM IngredientInRecipe WHERE ingredientName = '").append(ingredients.get(i).getName()).append("')");
            if (i < ingredients.size() - 1){
                query.append(conjunctionIngredients);
            } else {
                query.append(")");
            }
        }
        query.append(")) ORDER BY recipeName;");
        return query.toString();
    }

    @Override
    public void deleteAllRecipesFrom(String nick, Long recipeId) {
        if (recipeId != null){
            deleteRecipe(recipeId);
        } else {
            connectionManager.newQueryNoResult("DELETE FROM Recipe WHERE (ownerNickname = " + nick + ");");
        }
    }

    private Recipe getRecipeFromResultSet(ResultSet resultSet) throws SQLException {
        long recipeId = resultSet.getLong("recipeId");
        String name = resultSet.getString("recipeName");
        String description = resultSet.getString("descr");
        String owner = resultSet.getString("ownerNickname");
        int durationVal = resultSet.getInt("duration");
        List<String> steps = getStepsFromRecipe(recipeId);
        String[] nameDescOwn = {name, description, owner};
        Recipe thisRecipe = new Recipe(recipeId, nameDescOwn, steps, durationVal);
        loadIngredientsInRecipe(thisRecipe);
        return thisRecipe;
    }
    private void loadIngredientsInRecipe(Recipe recipe) {
        String query = "SELECT * FROM IngredientInRecipe WHERE (recipeId = " + recipe.getId() + ");";
        try (ResultSet resultIngredientInRecipe = connectionManager.newQuery(query)){
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
            connectionManager.endQuery(resultIngredientInRecipe);
            recipe.setIngredients(ingredientList);
            recipe.setIngredientsQuantity(ingredientsQuantityList);
            recipe.setIngredientsPortionsNames(ingredientsPortionsNamesList);
        } catch (Exception e){
            // No action
        }
    }

    private List<String> getStepsFromRecipe(long recipeId) {
        List<String> stepsList = new ArrayList<>();
        String query = "SELECT stepDescription FROM StepsInRecipe WHERE (recipeId = " + recipeId + ") ORDER BY stepPosition ASC;";
        try (ResultSet resultSteps = connectionManager.newQuery(query)){
            while (resultSteps.next()){
                String step = resultSteps.getString("stepDescription");
                stepsList.add(step);
            }
            connectionManager.endQuery(resultSteps);
            return stepsList;
        } catch (Exception e){
            return stepsList;
        }
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        connectionManager.newQueryNoResult("INSERT INTO Recipe (recipeId, recipeName, descr, ownerNickname, duration) VALUES (" +
                recipe.getId() + ", '" + recipe.getName() + "', '" + recipe.getDescription() + "', '" +
                recipe.getOwner() + "', " + recipe.getDuration() + ") ON DUPLICATE KEY UPDATE recipeName = '" +
                recipe.getName() + "', descr = '" + recipe.getDescription() + "', duration = " + recipe.getDuration() + ";");
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            connectionManager.newQueryNoResult("INSERT INTO IngredientInRecipe (recipeId, ingredientName, portionName, quantity) VALUES (" +
                    recipe.getId() + ", '" + recipe.getIngredientInPos(i) + "', '" + recipe.getIngredientPortionNameInPos(i) + "', " +
                    recipe.getIngredientQuantityInPos(i) + ") ON DUPLICATE KEY UPDATE ingredientName = '" +
                    recipe.getIngredientInPos(i) + "', portionName = '" + recipe.getIngredientPortionNameInPos(i) +
                    "', quantity = " + recipe.getIngredientQuantityInPos(i) + ";");
        }
        for (int i = 0; i < recipe.getSteps().size(); i++) {
            connectionManager.newQueryNoResult("INSERT INTO StepsInRecipe (recipeId, stepPosition, stepDescription) VALUES (" +
                    recipe.getId() + ", " + i + ", '" + recipe.getStepInPos(i) + "') ON DUPLICATE KEY UPDATE stepDescription = '" +
                    recipe.getStepInPos(i) + "';");
        }
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        connectionManager.newQueryNoResult("DELETE FROM Recipe WHERE (recipeId = " + recipeId + ");");
    }
}
