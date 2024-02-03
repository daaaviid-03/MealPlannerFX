package org.example.mealplannerfx.dao;

import org.example.mealplannerfx.control.AppController;
import org.example.mealplannerfx.control.WrongArgException;
import org.example.mealplannerfx.dao.db.DAORecipeDB;
import org.example.mealplannerfx.dao.fs.DAORecipeFS;
import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.entity.User;

import java.util.ArrayList;
import java.util.List;

public abstract class DAORecipe {
    /**
     * The object that contains the instance of the singleton class
     */
    private static DAORecipe daoRecipeInstance;

    protected DAORecipe(){}

    /**
     * Obtain the recipe that has the exact id
     * @param id the id of the recipe
     * @return the recipe object of the specific id, if it doesn't exist, returns null
     */
    public abstract Recipe getRecipe(Long id);

    /**
     * Obtain all the recipes that matches the restrictions
     * @param regexName regex to match with the name of the recipe (if NULL then there isn't restriction)
     * @param duration duration of the recipe to search (if NULL then there isn't restriction)
     * @param ingredients list of ingredients to match in the recipe (if NULL then there isn't restriction)
     * @param thisUser the restriction to be only the user's recipes (if NULL then there isn't restriction)
     * @param checkers is a boolean array with {toBeGraterEqualDuration, toBeLowerEqualDuration,
     *                 allOfThoseIngredients, allFieldsInCommon}.
     *                 so that:
     *                 allOfThoseIngredients -> if there should be a total match of the ingredients
     *                 allFieldsInCommon -> if there should be a total match with all restrictions or can be any of them
     *                 toBeGraterEqualDuration -> if the recipe duration should be grater-equal to the duration
     *                 toBeLowerEqualDuration -> if the recipe duration should be lower-equal to the duration
     * @return a list of recipes that matches the restrictions
     */
    public abstract List<Recipe> getAllRecipesAs(String regexName, Integer duration, List<Ingredient> ingredients,
                                                 User thisUser, boolean[] checkers) throws WrongArgException;

    /**
     * Obtain all the recipes in the file
     * @return a list of all recipes
     */
    public List<Recipe> getAllRecipes(){
        try {
            boolean[] checkers = {false, false, false, false};
            return getAllRecipesAs(".*", null, null, null, checkers);
        } catch (WrongArgException wrongArgException){
            return new ArrayList<>();
        }

    }

    /**
     * Deletes all recipes that has a specific user as owner
     * @param nick the nickname of the user to delete the recipes, if NULL then deletes the recipe with id
     * @param recipeId the id of the recipe to delete, if NULL then deletes all recipes from user
     */
    public abstract void deleteAllRecipesFrom(String nick, Long recipeId);

    /**
     * Saves the recipe in the binary file
     * @param recipe the recipe to save
     */
    public abstract void saveRecipe(Recipe recipe);

    /**
     * Deletes the recipe from the DB
     * @param recipeId the recipe object to delete
     */
    public abstract void deleteRecipe(Long recipeId);

    /**
     * Obtains the long for the next id for recipes that can be assigned, it also sums one to the actual value
     * and saves the new value in the DB
     * @return the long value of the next id available
     */
    public long getNextRecipeId() {
        return DAORecipeMaxId.getDaoRecipeMaxIdInstance().getNextAndAddRecipeMaxId();
    }

    public static DAORecipe getDaoRecipeInstance() {
        if (daoRecipeInstance == null){
            if (AppController.getActualDBMS().equals("DBMS (SQL)")) {
                daoRecipeInstance = new DAORecipeDB();
            } else {
                daoRecipeInstance = new DAORecipeFS();
            }
        }
        return daoRecipeInstance;
    }

}
