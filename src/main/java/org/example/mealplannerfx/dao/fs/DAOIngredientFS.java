package org.example.mealplannerfx.dao.fs;

import org.example.mealplannerfx.dao.DAOIngredient;
import org.example.mealplannerfx.entity.Ingredient;

import java.io.*;
import java.util.*;

public class DAOIngredientFS extends DAOIngredient {
    private final static String INGREDIENTS_FILE_NAME_DB = "fileData/fileDataBase/ingredientsInfo_DB.ingredientsInfo";
    @Override
    public List<Ingredient> getAllIngredientsAsRegex(String regex) {
        List<Ingredient> allIngredients = new ArrayList<>();
        try {
            FileInputStream thisFile = new FileInputStream(INGREDIENTS_FILE_NAME_DB);
            ObjectInputStream fileStream = new ObjectInputStream(thisFile);
            Ingredient ingred;
            while((ingred = (Ingredient)fileStream.readObject()) != null){
                if(ingred.getName().matches(regex)){
                    allIngredients.add(ingred);
                }
            }
            fileStream.close();
        } catch (Exception e) {
            loadIngredientsFromOriginalDB();
        }
        return allIngredients;
    }
    @Override
    public void saveIngredient(Ingredient ingredient, boolean newIngredient){
        try {
            FileOutputStream thisFile = new FileOutputStream(INGREDIENTS_FILE_NAME_DB, newIngredient);
            ObjectOutputStream fileStream = new ObjectOutputStream(new BufferedOutputStream(thisFile));
            if(!newIngredient){
                List<Ingredient> allIngredients = getAllIngredientsAsRegex("^(?!" + ingredient.getName() + "$).*");
                // Save the objects in the binary file
                for (Ingredient ingredient1 : allIngredients){
                    fileStream.writeObject(ingredient1);
                }
            } else {
                // Save the object in the binary file
                fileStream.writeObject(ingredient);
            }
            fileStream.close();
        } catch (Exception e) {
            System.err.println("Ingredient's DB file not found.");
        }
    }
}
