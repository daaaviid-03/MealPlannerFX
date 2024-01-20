package org.example.mealplannerfx.dao;

import org.example.mealplannerfx.entity.Ingredient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DAOIngredient {
    private final static String INGREDIENTS_ORIGINAL_DB_TXT = "fileData/originalDataToDB/ingredientsOriginalDB.txt";
    /**
     * The object that contains the instance of the singelton class
     */
    private static DAOIngredient daoIngredientInstance;

    /**
     * Constructor in witch is assigned the instance of the singelton class
     */
    public DAOIngredient(){
        setDaoIngredientInstance(this);
    }
    /**
     * Obtain the ingredient that has the exact name
     * @param name the name of the ingredient
     * @return the ingredient object of the especific name, if it doesn't exist, returns null
     */
    public Ingredient getIngredient(String name){
        List<Ingredient> ingredient = getAllIngredientsAsRegex("^" + name + "$");
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
    public abstract List<Ingredient> getAllIngredientsAsRegex(String regex);

    /**
     * Saves the ingredient in the binary file
     * @param ingredient the ingredient to save
     * @param newIngredient whether is a new ingredient or have to override an existing one
     */
    public abstract void saveIngredient(Ingredient ingredient, boolean newIngredient);

    /**
     * Load all the ingredients from the original DB (txt) into de binary file
     */
    public void loadIngredientsFromOriginalDB(){
        try {
            BufferedReader in = new BufferedReader(new FileReader(INGREDIENTS_ORIGINAL_DB_TXT));
            String line;
            while((line = in.readLine()) != null){
                Map<String, Float> portions = new HashMap<>();
                String[] s = line.split("\t");
                for (int i = 6; i < s.length; i += 2) {
                    portions.put(s[i], Float.valueOf(s[i + 1]));
                }
                Ingredient thisIngredient = new Ingredient(s[0], Float.parseFloat(s[1]), Float.parseFloat(s[2]), Float.parseFloat(s[3]), Float.parseFloat(s[4]), s[5], portions);
                saveIngredient(thisIngredient, true);
            }
            in.close();
        } catch (Exception e) {
            System.err.println("Original Ingredient's DB file not found.");
        }
    }

    public static DAOIngredient getDaoIngredientInstance() {
        return daoIngredientInstance;
    }

    public static void setDaoIngredientInstance(DAOIngredient daoIngredientInstance) {
        DAOIngredient.daoIngredientInstance = daoIngredientInstance;
    }
}
