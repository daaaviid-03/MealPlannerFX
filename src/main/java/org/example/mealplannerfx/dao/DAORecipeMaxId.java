package org.example.mealplannerfx.dao;

import org.example.mealplannerfx.control.AppController;
import org.example.mealplannerfx.dao.db.DAORecipeMaxIdDB;
import org.example.mealplannerfx.dao.fs.DAORecipeMaxIdFS;

public abstract class DAORecipeMaxId {
    /**
     * The object that contains the instance of the singleton class
     */
    private static DAORecipeMaxId daoRecipeMaxIdInstance;

    /**
     * Constructor in witch is assigned the instance of the singleton class
     */
    protected DAORecipeMaxId(){}

    /**
     * Obtain the next recipe max id available and adds the actual value plus une
     * @return the recipe max id available
     */
    public abstract long getNextAndAddRecipeMaxId();
    public static DAORecipeMaxId getDaoRecipeMaxIdInstance() {
        if (daoRecipeMaxIdInstance == null){
            if (AppController.getAppControllerInstance().getActualDBMS().equals("DBMS (SQL)")) {
                daoRecipeMaxIdInstance = new DAORecipeMaxIdDB();
            } else {
                daoRecipeMaxIdInstance = new DAORecipeMaxIdFS();
            }
        }
        return daoRecipeMaxIdInstance;
    }
}
