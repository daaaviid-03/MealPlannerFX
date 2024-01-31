package org.example.mealplannerfx.control;

import org.example.mealplannerfx.bwScreen.GraphicControllerBW;
import org.example.mealplannerfx.dao.db.*;
import org.example.mealplannerfx.dao.fs.*;
import org.example.mealplannerfx.coloredScreen.GraphicControllerColored;

public class AppController{
    private static AppController appControllerInstance;
    private String actualDBMS;
    private String actualViewMS;
    private String nextDBMS;
    private String nextViewMS;

    public AppController(){
        appControllerInstance = this;
        GetGlobalSettings.loadGlobalSettings();
        getDB();
        startView();
    }

    public void getDB(){
        switch (actualDBMS){
            case "DBMS (SQL)":
                new DAODayDataDB();
                new DAOIngredientDB();
                new DAORecipeDB();
                new DAORecipeMaxIdDB();
                new DAOUserDB();
                break;
            case "File System":
                new DAODayDataFS();
                new DAOIngredientFS();
                new DAORecipeFS();
                new DAORecipeMaxIdFS();
                new DAOUserFS();
                break;
        }
    }
    public void startView(){
        switch (actualViewMS) {
            case "Colored Screens":
                GraphicController graphicControllerColored = new GraphicControllerColored();
                graphicControllerColored.startView();
                break;
            case "B/W Screens":
                GraphicController graphicControllerBW = new GraphicControllerBW();
                graphicControllerBW.startView();
                break;
        }
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
        this.nextViewMS = viewMS;
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
