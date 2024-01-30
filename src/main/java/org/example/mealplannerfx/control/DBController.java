package org.example.mealplannerfx.control;

import org.example.mealplannerfx.dao.DAODayData;
import org.example.mealplannerfx.dao.DAOIngredient;
import org.example.mealplannerfx.dao.DAORecipe;
import org.example.mealplannerfx.dao.DAOUser;
import org.example.mealplannerfx.entity.DayData;
import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.entity.User;

import java.util.*;

public class DBController {
    private static final DAORecipe daoRecipe = DAORecipe.getDaoRecipeInstance();
    private static final DAOIngredient daoIngredient = DAOIngredient.getDaoIngredientInstance();
    private static final DAOUser daoUser = DAOUser.getDaoUserInstance();
    private static final DAODayData daoDayData = DAODayData.getDaoDayDataInstance();

    private static DBController dBControllerInstance;
    // Singelton GOF
    public DBController(){
        setDBControllerInstance(this);
    }
    private void setDBControllerInstance(DBController dbController){
        dBControllerInstance = dbController;
    }
    public static DBController getDBControllerInstance(){
        return dBControllerInstance;
    }

    // DB Controller functions

    /**
     * Obtain the next available recipe id and increase it
     * @return the next available recipe id
     */
    public long getNextRecipeId(){
        return daoRecipe.getNextRecipeId();
    }

    public void checkUserInDB(String nick, String pass) throws WrongArgumentException{
        User user = getUserInfo(nick);
        if(user != null){
            if(!user.getPassword().equals(pass)){
                throw new WrongArgumentException("Incorrect password");
            }
        } else{
            throw new WrongArgumentException("Nickname doesn't exists");
        }
    }
    public boolean checkUserInDB(String nick){
        return getUserInfo(nick) != null;
    }
    public User getUserInfo(String nick){
        return daoUser.getUser(nick);
    }
    public List<DayData> getDaysData(String nick, long fromDate, long toDate){
        return daoDayData.getDayDataFromUserBetween(nick, fromDate, toDate);
    }
    public void deleteRecipeFromDB(Recipe recipe){
        daoRecipe.deleteRecipe(recipe.getId());
    }
    public void createNewRecipeDB(String name, String description, String owner, List<String> steps, int duration,
                                  List<Ingredient> ingredients, List<Float> ingredientsQuantity,
                                  List<String> ingredientsPortionsNames){
        Recipe recipe = new Recipe(getNextRecipeId(), name, description, owner, steps, duration, ingredients,
                ingredientsQuantity, ingredientsPortionsNames);
        daoRecipe.saveRecipe(recipe, true);
    }
    private String getRegexFromQuery(String query){
        String regexName = "";
        if (query != null && !query.isEmpty()){
            regexName += "(?i)^";
            for (String namePartial : query.split(" ")){
                regexName += "(?=.*" + namePartial + ")";
            }
        }
        return regexName + ".*";
    }
    public List<Recipe> getRecipesSortedBy(String name, Boolean exactSameName, Integer duration,
                                           Boolean toBeGraterEqualDuration, Boolean toBeLowerEqualDuration,
                                           List<Ingredient> ingredients, Boolean allOfThoseIngredients,
                                           Boolean allFieldsInCommon, User thisUser, int numberOfElements) throws WrongArgumentException{
        List<Recipe> correctRecipes = daoRecipe.getAllRecipesAs(getRegexFromQuery(name), duration, toBeGraterEqualDuration, toBeLowerEqualDuration, ingredients,
                allOfThoseIngredients, allFieldsInCommon, thisUser, numberOfElements);
        if (correctRecipes.isEmpty()){
            throw new WrongArgumentException("No recipe matches with that filters.");
        }
        return correctRecipes;
    }
    public void deleteUserFromDB(String nick){
        daoUser.deleteCompleteUser(nick);
    }

    public DayData getSpecificDayData(String nick, long dayNumber){
        return daoDayData.getOrCreateDayDataFromUserIn(nick, dayNumber);
    }

    public Ingredient getIngredientByName(String name){
        return daoIngredient.getIngredient(name);
    }
    public List<String> getIngredientPortionsNames(String name){
        List<String> portionsList = new ArrayList<>(getIngredientByName(name).getFoodPortionsNamesList());
        portionsList.add("g");
        return portionsList;
    }
    public List<Ingredient> getListOfAllIngredientsByName(String name, Integer numberOfElements) {
        return daoIngredient.getAllIngredientsAsRegex(getRegexFromQuery(name), numberOfElements);
    }

    public Recipe getRecipe(Long id){
        return daoRecipe.getRecipe(id);
    }

    public User createUser(String nick, String pas, float height, float weight, String email, long birth) {
        User newUser = new User(nick, height, weight, birth, email, pas);
        daoUser.saveUser(newUser, true);
        return newUser;
    }

    public User modifyUser(String nickname, String correctRepPass, float correctHeight, float correctWeight, String email, long birth) {
        User user = daoUser.getUser(nickname);
        user.setPassword(correctRepPass);
        user.setHeight(correctHeight);
        user.setWeight(correctWeight);
        user.setEmail(email);
        user.setBirth(birth);
        daoUser.saveUser(user, false);
        return user;
    }

    public void saveDayData(DayData thisDayData, boolean newDayData) {
        daoDayData.saveDayData(thisDayData, newDayData);
    }
}
