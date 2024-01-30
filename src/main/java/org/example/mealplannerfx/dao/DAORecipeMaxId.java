package org.example.mealplannerfx.dao;

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
