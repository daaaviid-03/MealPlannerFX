package org.example.mealplannerfx;

import java.util.List;

public abstract class DBController {
    private static DBController dBControllerInstance;

    public abstract int checkUserInDB(String nick, String pass);

    public abstract boolean checkUserInDB(String nick);

    public abstract QueryReply createsUserInDB(String nick, String pass, float height, float weight, String email, long birth, Boolean isAdmin);
    public abstract User getUserInfo(String nick);
    public abstract List<User> getListOfAllUsers();
    public abstract QueryReply deleteUserFromDB(String nick);
    public abstract List<DayData> getUserCalendarInfo(String nick, int fromDate, int toDate);
    public abstract QueryReply createNewRecipeDB(String name, String description, User owner, List<String> steps, int duration, List<Ingredient> ingredients, List<Integer> ingredientsQuantity);
    public abstract List<Recipe> getRecipesSortedBy(String name, Boolean exactSameName, int duration, Boolean exactNotGrater, Boolean exactNotLower, List<Ingredient> ingredients, Boolean exactThoseIngredients);
    public abstract QueryReply deleteRecipeFromDB(Recipe recipe);
    public abstract void loadDataFromDB();
    public abstract void saveDataToDB();
    public void setDBControllerInstance(DBController dbController){
        dBControllerInstance = dbController;
    }
    public static DBController getDBControllerInstance(){
        return dBControllerInstance;
    }

    public abstract List<String> getListOfIngredientsNamesSorted();
    public abstract Ingredient getIngredientByName(String name);
}
