package org.example.mealplannerfx.control;

import org.example.mealplannerfx.bwScreen.GraphicControllerBW;
import org.example.mealplannerfx.dao.*;
import org.example.mealplannerfx.dao.db.*;
import org.example.mealplannerfx.dao.fs.*;
import org.example.mealplannerfx.coloredScreen.GraphicControllerColored;
import org.example.mealplannerfx.entity.DayData;

public class AppController{
    private static AppController appControllerInstance;
    private String actualDBMS;
    private String actualViewMS;
    private String nextDBMS;
    private String nextViewMS;

    private DAODayData daoDayData;
    private DAOIngredient daoIngredient;
    private DAORecipe daoRecipe;
    private DAORecipeMaxId daoRecipeMaxId;
    private DAOUser daoUser;

    private DBController dbController;

    private GraphicController graphicController;

    public AppController(){
        appControllerInstance = this;
        GetGlobalSettings.loadGlobalSettings();
        getDB();
        startView();
    }

    public void getDB(){
        switch (actualDBMS){
            case "DBMS (SQL)":
                daoDayData = new DAODayDataDB();
                daoIngredient = new DAOIngredientDB();
                daoRecipe = new DAORecipeDB();
                daoRecipeMaxId = new DAORecipeMaxIdDB();
                daoUser = new DAOUserDB();
                break;
            case "File System":
                daoDayData = new DAODayDataFS();
                daoIngredient = new DAOIngredientFS();
                daoRecipe = new DAORecipeFS();
                daoRecipeMaxId = new DAORecipeMaxIdFS();
                daoUser = new DAOUserFS();
                break;
        }
        dbController = new DBController();
    }
    public void startView(){
        switch (actualViewMS){
            case "Colored Screens":
                graphicController = new GraphicControllerColored();
                break;
            case "B/W Screens":
                graphicController = new GraphicControllerBW();
                break;
        }
        graphicController.startView();
    }

    public static AppController getAppControllerInstance() {
        return appControllerInstance;
    }

    public void setActualDBMS(String actualDBMS) {
        this.actualDBMS = actualDBMS;
    }

    public void setActualViewMS(String actualViewMS) {
        this.actualViewMS = actualViewMS;
    }

    public void setNextDBMS(String dBMS) {
        this.nextDBMS = dBMS;
        GetGlobalSettings.saveGlobalSettings();
    }

    public void setNextViewMS(String viewMS) {
        this.nextDBMS = viewMS;
        GetGlobalSettings.saveGlobalSettings();
    }

    public String getActualDBMS() {
        return actualDBMS;
    }

    public String getActualViewMS() {
        return actualViewMS;
    }

    public String getNextDBMS() {
        return nextDBMS;
    }

    public String getNextViewMS() {
        return nextViewMS;
    }
}
