package org.example.mealplannerfx;

import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DBJDBCController extends DBController{

    private HashMap<String, User> users;
    private List<Recipe> recipes;
    private List<Ingredient> ingredients;
    @Override
    public int checkUserInDB(String nick, String pass){
        return 0;
    }

    @Override
    public boolean checkUserInDB(String nick) {
        return false;
    }

    @Override
    public QueryReply createsUserInDB(String nick, String pass, float height, float weight, String email, long birth, Boolean isAdmin){
        return QueryReply.ErrorInExecution;
    }
    @Override
    public User getUserInfo(String nick){
        return null;
    }
    @Override
    public List<User> getListOfAllUsers(){
        return null;
    }
    @Override
    public QueryReply deleteUserFromDB(String nick){
        return QueryReply.ErrorInExecution;
    }
    @Override
    public List<DayData> getUserCalendarInfo(String nick, long fromDate, long toDate){
        return null;
    }
    @Override
    public QueryReply createNewRecipeDB(String name, String description, User owner, List<String> steps, int duration, List<Ingredient> ingredients, List<Integer> ingredientsQuantity, List<String> ingredientsPortionsNames){
        return QueryReply.ErrorInExecution;
    }
    @Override
    public List<Recipe> getRecipesSortedBy(String name, Boolean exactSameName, int duration, Boolean exactNotGrater, Boolean exactNotLower, List<Ingredient> ingredients, Boolean exactThoseIngredients){
        return null;
    }
    @Override
    public QueryReply deleteRecipeFromDB(Recipe recipe){
        return QueryReply.ErrorInExecution;
    }
    @Override
    public void loadDataFromDB(){

    }
    @Override
    public void saveDataToDB(){

    }

    @Override
    public ObservableList getListOfIngredientsNamesSorted() {
        return null;
    }

    @Override
    public Ingredient getIngredientByName(String name) {
        return null;
    }

    @Override
    public List<String> getIngredientPortionsNames(String name) {
        return null;
    }

    @Override
    public List<String> getListOfIngredientsNamesSortedBy(String string, int quantity) {
        return null;
    }

}
