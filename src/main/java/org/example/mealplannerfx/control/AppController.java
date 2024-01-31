package org.example.mealplannerfx.control;

import org.example.mealplannerfx.bwscreen.GraphicControllerBW;
import org.example.mealplannerfx.coloredscreen.GraphicControllerColored;

public class AppController{
    private static AppController appControllerInstance;
    private String actualDBMS;
    private String actualViewMS;
    private String nextDBMS;
    private String nextViewMS;

    private AppController(){}

    public void startView(){
        if (actualViewMS.equals("B/W Screens")) {
            GraphicController graphicControllerBW = new GraphicControllerBW();
            graphicControllerBW.startView();
        } else {
            GraphicController graphicControllerColored = GraphicControllerColored.getGCCInstance();
            graphicControllerColored.startView();
        }
    }

    public static AppController getAppControllerInstance() {
        if (appControllerInstance == null){
            appControllerInstance = new AppController();
            GetGlobalSettings.loadGlobalSettings();
        }
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
