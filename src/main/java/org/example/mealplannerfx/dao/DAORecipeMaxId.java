package org.example.mealplannerfx.dao;

import org.example.mealplannerfx.control.WrongArgumentException;
import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.entity.User;

import java.util.List;

public abstract class DAORecipeMaxId {
    /**
     * The object that contains the instance of the singleton class
     */
    private static DAORecipeMaxId daoRecipeMaxIdInstance;

    /**
     * Constructor in witch is assigned the instance of the singleton class
     */
    public DAORecipeMaxId(){
        setDaoRecipeMaxIdInstance(this);
    }
    /**
     * Obtain the recipe max id available
     * @return the recipe max id available
     */
    //public abstract long getRecipeMaxId();
    /**
     * Obtain the next recipe max id available and adds the actual value plus une
     * @return the recipe max id available
     */
    public abstract long getNextAndAddRecipeMaxId();
    public static DAORecipeMaxId getDaoRecipeMaxIdInstance() {
        return daoRecipeMaxIdInstance;
    }

    public static void setDaoRecipeMaxIdInstance(DAORecipeMaxId daoRecipeMaxIdInstance) {
        DAORecipeMaxId.daoRecipeMaxIdInstance = daoRecipeMaxIdInstance;
    }
}
