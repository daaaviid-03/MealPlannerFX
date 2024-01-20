package org.example.mealplannerfx.dao;

import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.entity.User;

import java.util.List;

public abstract class DAORecipe {
    private final static String RECIPE_ORIGINAL_DB_TXT = "fileData/originalDataToDB/recipesOriginalDB.txt";
    /**
     * The object that contains the instance of the singleton class
     */
    private static DAORecipe daoRecipeInstance;

    /**
     * Constructor in witch is assigned the instance of the singleton class
     */
    public DAORecipe(){
        setDaoRecipeInstance(this);
    }
    /**
     * Obtain the recipe that has the exact id
     * @param id the id of the recipe
     * @return the recipe object of the specific id, if it doesn't exist, returns null
     */
    public abstract Recipe getRecipe(long id);

    /**
     * Obtain all the recipes that matches the restrictions
     * @param regexName regex to match with the name of the recipe (if NULL then there isn't restriction)
     * @param duration duration of the recipe to search (if NULL then there isn't restriction)
     * @param toBeGraterEqualDuration if the recipe duration should be grater-equal to the duration
     * @param toBeLowerEqualDuration if the recipe duration should be lower-equal to the duration
     * @param ingredients list of ingredients to match in the recipe (if NULL then there isn't restriction)
     * @param allOfThoseIngredients if there should be a total match of the ingredients
     * @param allFieldsInCommon if there should be a total match with all restrictions or can be any of them
     * @param thisUser the restriction to be only the user's recipes (if NULL then there isn't restriction)
     * @return a list of recipes that matches the restrictions
     */
    public abstract List<Recipe> getAllRecipesAs(String regexName, Integer duration,
                                                 Boolean toBeGraterEqualDuration, Boolean toBeLowerEqualDuration,
                                                 List<Ingredient> ingredients, Boolean allOfThoseIngredients,
                                                 Boolean allFieldsInCommon, User thisUser);

    /**
     * Deletes all recipes that has a specific user as owner
     * @param nick the nickname of the user to delete the recipes
     */
    public abstract void deleteAllRecipesFromUser(String nick);

    /**
     * Saves the recipe in the binary file
     * @param recipe the recipe to save
     * @param newRecipe whether is a new recipe or have to override an existing one
     */
    public abstract void saveRecipe(Recipe recipe, boolean newRecipe);

    /**
     * Deletes the recipe from the DB
     * @param recipe the recipe object to delete
     */
    public abstract void deleteRecipe(Recipe recipe);

    /**
     * Obtains the long for the next id for recipes that can be assigned, it also sums one to the actual value
     * and saves the new value in the DB
     * @return the long value of the next id available
     */
    public abstract long getNextRecipeId();

    /**
     * Load all the recipes from the original DB (txt) into de binary file
     */
//    public void loadRecipesFromOriginalDB(){
//        try {
//            BufferedReader in = new BufferedReader(new FileReader(RECIPE_ORIGINAL_DB_TXT));
//            String line;
//            while((line = in.readLine()) != null){
//                String[] s = line.split("\t");
//                Recipe thisRecipe = new Recipe(s[0], Float.parseFloat(s[1]), Float.parseFloat(s[2]), Float.parseFloat(s[3]), Float.parseFloat(s[4]), s[5], portions);
//                saveRecipe(thisRecipe, true);
//            }
//            in.close();
//        } catch (Exception e) {
//            System.err.println("Original Recipe's DB file not found.");
//        }
//    }

    public static DAORecipe getDaoRecipeInstance() {
        return daoRecipeInstance;
    }

    public static void setDaoRecipeInstance(DAORecipe daoRecipeInstance) {
        DAORecipe.daoRecipeInstance = daoRecipeInstance;
    }
}
