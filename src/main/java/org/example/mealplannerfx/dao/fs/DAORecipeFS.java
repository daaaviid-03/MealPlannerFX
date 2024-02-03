package org.example.mealplannerfx.dao.fs;

import org.example.mealplannerfx.control.WrongArgException;
import org.example.mealplannerfx.dao.DAORecipe;
import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.entity.User;

import java.util.*;

public class DAORecipeFS extends DAORecipe {
    private static final String RECIPES_FILE_NAME_DB = "fileData/fileDataBase/recipesInfo_DB.recipesInfo";
    private final FileRW<Recipe> fileRW = new FileRW<>(RECIPES_FILE_NAME_DB);
    @Override
    public Recipe getRecipe(Long id) {
        if (id == null){
            return null;
        }
        return fileRW.getObjectAs(recipe -> (recipe.getId() == id));
    }

    @Override
    public List<Recipe> getAllRecipesAs(String regexName, Integer duration, List<Ingredient> ingredients, User thisUser, boolean[] checkers) throws WrongArgException {

        List<Recipe> correctRecipes = fileRW.getAllObjectsAs(recipe -> matchRecipe(regexName, duration, ingredients, thisUser, checkers, recipe));
        if (correctRecipes.isEmpty()){
            throw new WrongArgException("No recipe matches with that filters.");
        }
        correctRecipes.sort(Comparator.comparing(Recipe::getName));
        return correctRecipes;
    }

    private static boolean matchRecipe(String regexName, Integer duration, List<Ingredient> ingredients, User thisUser,
                                       boolean[] checkers, Recipe recipe) {
        // Obtain this recipe info
        String thisName = recipe.getName();
        int thisDuration = recipe.getDuration();
        List<Ingredient> thisIngredients = recipe.getIngredients();
        // User
        boolean isCandidateForUser = true;
        if (thisUser != null) {
            isCandidateForUser = recipe.getOwner().equals(thisUser.getNickname());
        }
        // Name
        boolean isCandidateForName = thisName.matches(regexName);
        // Duration
        boolean isCandidateForDuration = (duration == null) ||
                ((checkers[2] && !checkers[3] && thisDuration >= duration) ||
                (!checkers[2] && checkers[3] && thisDuration <= duration) ||
                (checkers[2] && checkers[3] && thisDuration == duration) ||
                (!checkers[2] && !checkers[3] && thisDuration != duration));

        boolean isCandidateForIngredients = checkers[0];
        if (ingredients != null) {
            for (Ingredient ingredient : ingredients) {
                if (checkers[0]) {
                    isCandidateForIngredients &= thisIngredients.contains(ingredient);
                } else {
                    isCandidateForIngredients |= thisIngredients.contains(ingredient);
                }
            }
        } else {
            isCandidateForIngredients = true;
        }
        // Last filter of fields
        return (isCandidateForUser && ((checkers[1] && isCandidateForName && isCandidateForDuration && isCandidateForIngredients) ||
                (!checkers[1] && (isCandidateForName || isCandidateForDuration || isCandidateForIngredients))));
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
