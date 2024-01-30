package org.example.mealplannerfx.dao.fs;

import org.example.mealplannerfx.dao.DAOIngredient;
import org.example.mealplannerfx.entity.DayData;
import org.example.mealplannerfx.entity.Ingredient;

import java.io.*;
import java.util.*;

public class DAOIngredientFS extends DAOIngredient {
    private final static String INGREDIENTS_FILE_NAME_DB = "fileData/fileDataBase/ingredientsInfo_DB.ingredientsInfo";
    private final FileRW<Ingredient> fileRW = new FileRW<>(INGREDIENTS_FILE_NAME_DB);
    private final static Comparator<Ingredient> INGREDIENT_COMPARATOR = new Comparator<Ingredient>() {
        @Override
        public int compare(Ingredient ingredient1, Ingredient ingredient2) {
            return ingredient1.getName().compareTo(ingredient2.getName());
        }
    };
    @Override
    public List<Ingredient> getAllIngredientsAsRegex(String regex, Integer numberOfElements) {
        List<Ingredient> ingredients = fileRW.getAllObjectsAs(ingredient ->
                (ingredient.getName().matches(regex)), numberOfElements);
        ingredients.sort(INGREDIENT_COMPARATOR);
        return ingredients;
    }
    @Override
    public void saveIngredient(Ingredient ingredientToSave, boolean newIngredient){
        fileRW.appendObjectsWithout(ingredientToSave, ingredient ->
                (ingredient.getName().equals(ingredientToSave.getName())));
    }
}
