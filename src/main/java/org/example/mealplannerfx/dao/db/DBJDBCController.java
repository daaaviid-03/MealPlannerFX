package org.example.mealplannerfx.dao.db;

import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.entity.DayData;
import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBJDBCController extends DBController {
    private final ConnectionMySQL connectionMySQL;
    private final String ingredientsFileNameDBOriginalDB = "fileData/originalDataToDB/ingredientsOriginalDB.txt";
    
    public DBJDBCController(){
        super();
        connectionMySQL = new ConnectionMySQL();
        connectionMySQL.startConnection();
        loadDataFromDB();
        loadRelationsFromDB();
        //super.loadIngredientsFromOriginalDB(true);
    }

    private void loadRelationsFromDB() {
        loadIngredientInRecipe();
    }
    @Override
    public void loadDayDataFromUserFromDB(String nick) {
        try {
            String query = "SELECT * FROM DayData WHERE (userNickname = '" + nick + "');";
            ResultSet resultDayData = connectionMySQL.newQuery(query);
            while (resultDayData.next()){
                long dayNumber = resultDayData.getLong("dayNumber");
                Long breakfastRecipeId = resultDayData.getLong("breakfastRecipe");
                Recipe breakfastRecipe = null;
                if (!resultDayData.wasNull()){
                    breakfastRecipe = super.getRecipe(breakfastRecipeId);
                }
                Long lunchRecipeId = resultDayData.getLong("lunchRecipe");
                Recipe lunchRecipe = null;
                if (!resultDayData.wasNull()){
                    lunchRecipe = super.getRecipe(lunchRecipeId);
                }
                Long dinnerRecipeId = resultDayData.getLong("dinnerRecipe");
                Recipe dinnerRecipe = null;
                if (!resultDayData.wasNull()){
                    dinnerRecipe = super.getRecipe(dinnerRecipeId);
                }
                addUserDayData(new DayData(nick, dayNumber, breakfastRecipe, lunchRecipe, dinnerRecipe));
            }
            connectionMySQL.endQuery(resultDayData);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }

    }

    @Override
    public void deleteAllDayDataFromUserFromTheDB(String nick) {
        try {
            connectionMySQL.newQueryNoResult("DELETE FROM DayData WHERE (userNickname = '" + getThisUser().getNickname() + "');");
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private void loadIngredientInRecipe() {
        for (Recipe recipe : super.getRecipesValues()){
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
                    ingredientList.add(super.getIngredientByName(ingredientName));
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
    }

    @Override
    public void saveUsersInDB() {
        try {
            for (User user : super.getUsersValues()){
                connectionMySQL.newQueryNoResult("INSERT INTO UserT (nickname, pass, email, heightVal, weightVal, birth) VALUES ('" +
                        user.getNickname() + "', '" + user.getPassword() + "', '" + user.getEmail() + "', " +
                        user.getHeight() + ", " + user.getWeight() + ", " + user.getBirth() + ") ON DUPLICATE KEY UPDATE pass = '" +
                        user.getPassword() + "', email = '" + user.getEmail() + "', heightVal = " + user.getHeight() +
                        ", weightVal = " + user.getWeight() + ", birth = " + user.getBirth() + ";");
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveRecipesInDB() {
        try {
            for (Recipe recipe : super.getRecipesValues()){
                connectionMySQL.newQueryNoResult("INSERT INTO Recipe (id, recipeName, descr, ownerNickname, duration) VALUES (" +
                        recipe.getId() + ", '" + recipe.getName() + "', '" + recipe.getDescription() + "', '" +
                        recipe.getOwner() + "', " + recipe.getDuration() + ");");
                for (int i = 0; i < recipe.getIngredients().size(); i++) {
                    connectionMySQL.newQueryNoResult("INSERT INTO IngredientInRecipe (recipeId, ingredientName, portionName, quantity) VALUES (" +
                            recipe.getId() + ", '" + recipe.getIngredientInPos(i) + "', '" + recipe.getIngredientPortionNameInPos(i) + "', " +
                            recipe.getIngredientQuantityInPos(i) + ");");
                }
                for (int i = 0; i < recipe.getSteps().size(); i++) {
                    connectionMySQL.newQueryNoResult("INSERT INTO StepsInRecipe (recipeId, stepPosition, stepDescription) VALUES (" +
                            recipe.getId() + ", " + i + ", '" + recipe.getStepInPos(i) + "');");
                }
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveIngredientsInDB() {
        try {
            for (Ingredient ingredient : super.getIngredientsValues()){
                connectionMySQL.newQueryNoResult("INSERT INTO Ingredient (ingredientName, calories, carb, prot, fats, category) VALUES ('" +
                        ingredient.getName() + "', " + ingredient.getCalories() + ", " + ingredient.getCarbohydrates() + ", " +
                        ingredient.getProteins() + ", " + ingredient.getFats() + ", '" + ingredient.getCategory() + "');");
                for (String portionName : ingredient.getFoodPortionsNamesList()){
                    connectionMySQL.newQueryNoResult("INSERT INTO FoodPortions (ingredientName, portionName, quantity) VALUES ('" +
                            ingredient.getName() + "', '" + portionName + "', " +
                            ingredient.getFoodPortionInGrams(portionName) + ");");
                }
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveDayDataFromUserInDB() {
        try {
            for (DayData dayData : getUserDayDataValues()){
                connectionMySQL.newQueryNoResult("INSERT INTO DayData (userNickname, dayNumber, breakfastRecipe, lunchRecipe, dinnerRecipe) VALUES ('" +
                        dayData.getUserNickname() + "', " + dayData.getDayNumber() + ", " + dayData.getBreakfast().getId() + ", " +
                        dayData.getLunch().getId() + ", " + dayData.getDinner().getId() + ");");
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private Map<String, Float> getFoodPortionsForIngredient(String name) throws Exception{
        try {
            String query = "SELECT * FROM FoodPortions WHERE (ingredientName = '" + name + "');";
            ResultSet resultFoodPort = connectionMySQL.newQuery(query);
            Map<String, Float> foodPortions = new HashMap<>();
            while (resultFoodPort.next()){
                String portionName = resultFoodPort.getString("portionName");
                float quantity = resultFoodPort.getFloat("quantity");
                foodPortions.put(portionName, quantity);
            }
            connectionMySQL.endQuery(resultFoodPort);
            return foodPortions;
        } catch (Exception e){
            throw e;
        }
    }
    private List<String> getStepsFromRecipe(long id) throws Exception{
        try {
            String query = "SELECT stepDescription FROM StepsInRecipe WHERE (recipeId = " + id + ") ORDER BY stepPosition ASC;";
            ResultSet resultSteps = connectionMySQL.newQuery(query);
            List<String> stepsList = new ArrayList<>();
            while (resultSteps.next()){
                String step = resultSteps.getString("stepDescription");
                stepsList.add(step);
            }
            connectionMySQL.endQuery(resultSteps);
            return stepsList;
        } catch (Exception e){
            throw e;
        }
    }

    private void loadUserFromResultSet(ResultSet result) throws SQLException{
        String nick = result.getString("nickname");
        String email = result.getString("email");
        String password = result.getString("pass");
        long birth = result.getLong("birth");
        float height = result.getFloat("heightVal");
        float weight = result.getFloat("weightVal");
        User user = new User(nick,height,weight,birth,email,password);
        super.addUser(user);
    }

    private void loadRecipeFromResultSet(ResultSet result) throws Exception{
        long id = result.getLong("id");
        String name = result.getString("recipeName");
        String description = result.getString("descr");
        String owner = result.getString("ownerNickname");
        int duration = result.getInt("duration");
        List<String> steps = getStepsFromRecipe(id);
        Recipe recipe = new Recipe(id, name, description, owner, steps, duration);
        super.addRecipe(recipe);
    }

    private void loadIngredientFromResultSet(ResultSet result) throws Exception{
        String name = result.getString("ingredientName");
        float calories = result.getFloat("calories");
        float carbohydrates = result.getFloat("carb");
        float proteins = result.getFloat("prot");
        float fats = result.getFloat("fats");
        String category = result.getString("category");
        Map<String, Float> foodPortions = getFoodPortionsForIngredient(name);
        Ingredient ingredient = new Ingredient(name,calories,carbohydrates,proteins,fats,category,foodPortions);
        super.addIngredient(ingredient);
    }

    @Override
    public void loadUsersFromDB() {
        try {
            String query = "SELECT * FROM UserT;";
            ResultSet resultUsers = connectionMySQL.newQuery(query);
            while (resultUsers.next()){
                loadUserFromResultSet(resultUsers);
            }
            connectionMySQL.endQuery(resultUsers);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void loadRecipesFromDB() {
        try {
            String query = "SELECT * FROM Recipe;";
            ResultSet resultRecipes = connectionMySQL.newQuery(query);
            while (resultRecipes.next()){
                loadRecipeFromResultSet(resultRecipes);
            }
            connectionMySQL.endQuery(resultRecipes);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void loadIngredientsFromDB() {
        try {
            String query = "SELECT * FROM Ingredient;";
            ResultSet resultIngredients = connectionMySQL.newQuery(query);
            while (resultIngredients.next()){
                loadIngredientFromResultSet(resultIngredients);
            }
            connectionMySQL.endQuery(resultIngredients);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}