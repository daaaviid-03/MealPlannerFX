package org.example.mealplannerfx.dao.fs;

import org.example.mealplannerfx.control.WrongArgException;
import org.example.mealplannerfx.dao.DAORecipe;
import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.entity.User;

import java.util.*;

public class DAORecipeFS extends DAORecipe {
    private final static String RECIPES_FILE_NAME_DB = "fileData/fileDataBase/recipesInfo_DB.recipesInfo";
    private final FileRW<Recipe> fileRW = new FileRW<>(RECIPES_FILE_NAME_DB);
    @Override
    public Recipe getRecipe(Long id) {
        if (id == null){
            return null;
        }
        return fileRW.getObjectAs(recipe -> (recipe.getId() == id));
    }

    @Override
    public List<Recipe> getAllRecipesAs(String regexName, Integer duration, Boolean toBeGraterEqualDuration,
                                        Boolean toBeLowerEqualDuration, List<Ingredient> ingredients,
                                        Boolean allOfThoseIngredients, Boolean allFieldsInCommon, User thisUser) throws WrongArgException {

        List<Recipe> correctRecipes = fileRW.getAllObjectsAs(recipe -> {
            boolean isCandidateForIngredients = allFieldsInCommon;
            boolean isCandidateForUser = true;
            // Obtain this recipe info
            String thisName = recipe.getName();
            int thisDuration = recipe.getDuration();
            List<Ingredient> thisIngredients = recipe.getIngredients();
            // Compare recipe with filter
            boolean isCandidateForName = thisName.matches(regexName);
            boolean isCandidateForDuration = (duration == null) || ((toBeGraterEqualDuration && !toBeLowerEqualDuration && thisDuration >= duration) ||
                    (!toBeGraterEqualDuration && toBeLowerEqualDuration && thisDuration <= duration) ||
                    (toBeGraterEqualDuration && toBeLowerEqualDuration && thisDuration == duration));
            if (ingredients != null) {
                for (Ingredient ingredient : ingredients) {
                    if (allOfThoseIngredients) {
                        isCandidateForIngredients &= thisIngredients.contains(ingredient);
                    } else {
                        isCandidateForIngredients |= thisIngredients.contains(ingredient);
                    }
                }
            } else {
                isCandidateForIngredients = true;
            }
            if (thisUser != null) {
                isCandidateForUser = recipe.getOwner().equals(thisUser.getNickname());
            }
            // Last filter of fields
            return (isCandidateForUser && ((allFieldsInCommon && isCandidateForName && isCandidateForDuration && isCandidateForIngredients) ||
                        (!allFieldsInCommon && (isCandidateForName || isCandidateForDuration || isCandidateForIngredients))));

        });

        if (correctRecipes.isEmpty()){
            throw new WrongArgException("No recipe matches with that filters.");
        }
        correctRecipes.sort(Comparator.comparing(Recipe::getName));
        return correctRecipes;
    }

    @Override
    public void deleteAllRecipesFrom(String nick, Long recipeId) {
        fileRW.deleteObjects(recipe -> (!recipe.getOwner().equals(nick) || recipeId != recipe.getId()));
    }

    @Override
    public void saveRecipe(Recipe recipeToSave) {
        fileRW.appendObjectsWithout(recipeToSave, recipe -> (recipe.getId() == recipeToSave.getId()));
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        deleteAllRecipesFrom(null, recipeId);
    }
}
