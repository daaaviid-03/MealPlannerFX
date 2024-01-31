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

    private DBController(){}

    /**
     * Obtain the next available recipe id and increase it
     * @return the next available recipe id
     */
    public static long getNextRecipeId(){
        return daoRecipe.getNextRecipeId();
    }

    public static void checkUserInDB(String nick, String pass) throws WrongArgException {
        User user = getUserInfo(nick);
        if(user != null){
            if(!user.getPassword().equals(pass)){
                throw new WrongArgException("Incorrect password");
            }
        } else{
            throw new WrongArgException("Nickname doesn't exists");
        }
    }
    public static boolean checkUserInDB(String nick){
        return getUserInfo(nick) != null;
    }
    public static User getUserInfo(String nick){
        return daoUser.getUser(nick);
    }
    public static List<DayData> getDaysData(String nick, long fromDate, long toDate){
        return daoDayData.getDayDataFromUserBetween(nick, fromDate, toDate);
    }

    public static void createNewRecipeDB(String name, String description, String owner, List<String> steps, int duration,
                                  List<Ingredient> ingredients, List<Float> ingredientsQuantity,
                                  List<String> ingredientsPortionsNames){
        Recipe recipe = new Recipe(getNextRecipeId(), name, description, owner, steps, duration, ingredients,
                ingredientsQuantity, ingredientsPortionsNames);
        daoRecipe.saveRecipe(recipe);
    }
    private static String getRegexFromQuery(String query){
        StringBuilder regexName = new StringBuilder();
        if (query != null && !query.isEmpty()){
            regexName.append("(?i)^");
            for (String namePartial : query.split(" ")){
                regexName.append("(?=.*").append(namePartial).append(")");
            }
        }
        return regexName + ".*";
    }
    public static List<Recipe> getRecipesSortedBy(String name, boolean exactSameName, Integer duration,
                                           boolean toBeGraterEqualDuration, boolean toBeLowerEqualDuration,
                                           List<Ingredient> ingredients, boolean allOfThoseIngredients,
                                                  boolean allFieldsInCommon, User thisUser, int numberOfElements) throws WrongArgException {
        String nameRegex = "^(?i)" + name + "$";
        if (!exactSameName){
            nameRegex = getRegexFromQuery(name);
        }
        List<Recipe> correctRecipes = daoRecipe.getAllRecipesAs(nameRegex, duration, toBeGraterEqualDuration, toBeLowerEqualDuration, ingredients,
                allOfThoseIngredients, allFieldsInCommon, thisUser, numberOfElements);
        if (correctRecipes.isEmpty()){
            throw new WrongArgException("No recipe matches with that filters.");
        }
        return correctRecipes;
    }
    public static void deleteUserFromDB(String nick){
        daoUser.deleteCompleteUser(nick);
    }

    public static DayData getSpecificDayData(String nick, long dayNumber){
        return daoDayData.getOrCreateDayDataFromUserIn(nick, dayNumber);
    }

    public static Ingredient getIngredientByName(String name){
        return daoIngredient.getIngredient(name);
    }
    public static List<String> getIngredientPortionsNames(String name){
        List<String> portionsList = new ArrayList<>(getIngredientByName(name).getFoodPortionsNamesList());
        portionsList.add("g");
        return portionsList;
    }
    public static List<Ingredient> getListOfAllIngredientsByName(String name, Integer numberOfElements) {
        return daoIngredient.getAllIngredientsAsRegex(getRegexFromQuery(name), numberOfElements);
    }

    public static Recipe getRecipe(Long id){
        return daoRecipe.getRecipe(id);
    }

    public static User createUser(String nick, String pas, float height, float weight, String email, long birth) {
        User newUser = new User(nick, height, weight, birth, email, pas);
        daoUser.saveUser(newUser);
        return newUser;
    }

    public static User modifyUser(String nickname, String correctRepPass, float correctHeight, float correctWeight, String email, long birth) {
        User user = daoUser.getUser(nickname);
        user.setPassword(correctRepPass);
        user.setHeight(correctHeight);
        user.setWeight(correctWeight);
        user.setEmail(email);
        user.setBirth(birth);
        daoUser.saveUser(user);
        return user;
    }

    public static void saveDayData(DayData thisDayData) {
        daoDayData.saveDayData(thisDayData);
    }
}
