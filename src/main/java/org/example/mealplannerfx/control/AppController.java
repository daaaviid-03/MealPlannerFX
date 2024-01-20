package org.example.mealplannerfx.control;

import org.example.mealplannerfx.bwScreen.GraphicControllerBW;
import org.example.mealplannerfx.dao.db.DBJDBCController;
import org.example.mealplannerfx.dao.fs.DBFileController;
import org.example.mealplannerfx.coloredScreen.GraphicControllerColored;

public class AppController{
    private static AppController appControllerInstance;
    private DBController dbController;
    private GraphicController graphicController;
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
                dbController = new DBJDBCController();
                break;
            case "File System":
                dbController = new DBFileController();
                break;
        }
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
}
