package org.example.mealplannerfx.dao.fs;

import org.example.mealplannerfx.dao.DAOIngredient;
import org.example.mealplannerfx.entity.Ingredient;

import java.util.*;

public class DAOIngredientFS extends DAOIngredient {
    private static final String INGREDIENTS_FILE_NAME_DB = "fileData/fileDataBase/ingredientsInfo_DB.ingredientsInfo";
    private final FileRW<Ingredient> fileRW = new FileRW<>(INGREDIENTS_FILE_NAME_DB);
    @Override
    public List<Ingredient> getAllIngredientsAsRegex(String regex, Integer numberOfElements) {
        List<Ingredient> ingredients = fileRW.getAllObjectsAs(ingredient ->
                (ingredient.getName().matches(regex)), numberOfElements);
        ingredients.sort(Comparator.comparing(Ingredient::getName));
        return ingredients;
    }
    @Override
    public void saveIngredient(Ingredient ingredientToSave){
        fileRW.appendObjectsWithout(ingredientToSave, ingredient ->
                (ingredient.getName().equals(ingredientToSave.getName())));
    }

    @Override
    public void saveIngredients(List<Ingredient> ingredientsToSave){
        fileRW.appendObjectsWithout(ingredientsToSave, ingredient ->
                (ingredientsToSave.stream().anyMatch(ingredient1 ->
                        (ingredient.getName().equals(ingredient1.getName())))));
    }
}
