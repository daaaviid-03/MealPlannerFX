package org.example.mealplannerfx.dao;

import org.example.mealplannerfx.control.AppController;
import org.example.mealplannerfx.dao.db.DAOIngredientDB;
import org.example.mealplannerfx.dao.fs.DAOIngredientFS;
import org.example.mealplannerfx.entity.Ingredient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public abstract class DAOIngredient {
    private static final String INGREDIENTS_ORIGINAL_DB_TXT = "fileData/originalDataToDB/ingredientsOriginalDB.txt";
    /**
     * The object that contains the instance of the singleton class
     */
    private static DAOIngredient daoIngredientInstance;

    protected DAOIngredient(){}
    /**
     * Obtain the ingredient that has the exact name
     * @param name the name of the ingredient
     * @return the ingredient object of the specific name, if it doesn't exist, returns null
     */
    public Ingredient getIngredient(String name){
        List<Ingredient> ingredient = getAllIngredientsAsRegex("^" + name + "$", 1);
        if (!ingredient.isEmpty()){
            return ingredient.getFirst();
        } else {
            return null;
        }
    }

    /**
     * Obtain all the ingredients that matches with the regex string
     * @param regex the regex string to match with the name of the ingredient
     * @return all ingredients that matches the regex string
     */
    public abstract List<Ingredient> getAllIngredientsAsRegex(String regex, Integer numberOfElements);

    /**
     * Saves the ingredient in the binary file
     * @param ingredient the ingredient to save
     */
    public abstract void saveIngredient(Ingredient ingredient);

    /**
     * Saves the list of ingredients in the binary file
     * @param ingredientsToSave the list of ingredients to save
     */
    public abstract void saveIngredients(List<Ingredient> ingredientsToSave);

    /**
     * Load all the ingredients from the original DB (txt) into de binary file
     */
    public void loadIngredientsFromOriginalDB(){
        try (BufferedReader in = new BufferedReader(new FileReader(INGREDIENTS_ORIGINAL_DB_TXT))){
            List<Ingredient> ingredients = new ArrayList<>();
            String line;
            while((line = in.readLine()) != null){
                Map<String, Float> portions = new HashMap<>();
                String[] s = line.split("\t");
                for (int i = 6; i < s.length; i += 2) {
                    portions.put(s[i], Float.valueOf(s[i + 1]));
                }
                Ingredient thisIngredient = new Ingredient(s[0], Float.parseFloat(s[1]), Float.parseFloat(s[2]), Float.parseFloat(s[3]), Float.parseFloat(s[4]), s[5], portions);
                ingredients.add(thisIngredient);
            }
            saveIngredients(ingredients);
        } catch (Exception e) {
            // No action
        }
    }

    public static DAOIngredient getDaoIngredientInstance() {
        if (daoIngredientInstance == null){
            if (AppController.getActualDBMS().equals("DBMS (SQL)")) {
                daoIngredientInstance = new DAOIngredientDB();
            } else {
                daoIngredientInstance = new DAOIngredientFS();
            }
        }
        return daoIngredientInstance;
    }

}
