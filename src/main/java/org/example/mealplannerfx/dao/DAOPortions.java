package org.example.mealplannerfx.dao;

import java.util.Map;

public abstract class DAOPortions {
    /**
     * The object that contains the instance of the singelton class
     */
    private static DAOPortions daoPortionInstance;

    /**
     * Constructor in witch is assigned the instance of the singelton class
     */
    public DAOPortions(){
        setDaoPortionInstance(this);
    }
    /**
     * Obtain the portions that are in the ingredient
     * @return the portions map of the specific ingredient, if it doesn't exist, returns null
     */
    public abstract Map<String, Float> getPortionsFromIngredient(String ingredientName);

    /**
     * Saves the portion in the binary file
     * @param portionName the portion name to save
     * @param portionInGrams the portion value to save
     * @param ingredientName the ingredient that correspond this portion
     * @param newPortion whether is a new portion or have to override an existing one
     */
    public abstract void savePortion(String portionName, float portionInGrams, String ingredientName, boolean newPortion);

    public static DAOPortions getDaoPortionInstance() {
        return daoPortionInstance;
    }

    public static void setDaoPortionInstance(DAOPortions daoPortionInstance) {
        DAOPortions.daoPortionInstance = daoPortionInstance;
    }
}
