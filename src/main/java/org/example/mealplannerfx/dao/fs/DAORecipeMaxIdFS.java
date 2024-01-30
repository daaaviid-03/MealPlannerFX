package org.example.mealplannerfx.dao.fs;

import org.example.mealplannerfx.dao.DAORecipe;
import org.example.mealplannerfx.dao.DAORecipeMaxId;
import org.example.mealplannerfx.entity.Recipe;

import java.util.List;

public class DAORecipeMaxIdFS extends DAORecipeMaxId {
    private final static String RECIPES_MAX_ID_FILE_NAME_DB = "fileData/fileDataBase/recipesMaxId_DB.recipesMaxId";
    private final FileRW<Long> fileRW = new FileRW<>(RECIPES_MAX_ID_FILE_NAME_DB);

    private long getRecipeMaxId() {
        try {
            return fileRW.getAllObjects().getFirst();
        } catch (Exception e){
            // If there is no max id file
            List<Recipe> allRecipes = DAORecipe.getDaoRecipeInstance().getAllRecipes();
            long maxActualId = -1L;
            for (Recipe recipe : allRecipes){
                if (recipe.getId() > maxActualId){
                    maxActualId = recipe.getId();
                }
            }
            fileRW.setAllObjects(maxActualId);
            return maxActualId;
        }
    }

    @Override
    public long getNextAndAddRecipeMaxId() {
        long nextRecipeId = getRecipeMaxId();
        fileRW.setAllObjects(++nextRecipeId);
        return nextRecipeId;
    }
}
