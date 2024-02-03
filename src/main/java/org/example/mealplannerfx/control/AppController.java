package org.example.mealplannerfx.control;

import org.example.mealplannerfx.bwscreen.GraphicControllerBW;
import org.example.mealplannerfx.coloredscreen.GraphicControllerColored;
import org.example.mealplannerfx.dao.db.ConnectionManager;

public class AppController{
    private static String actualDBMS;
    private static String actualViewMS;
    private static String nextDBMS;
    private static String nextViewMS;

    public static void startBD(){
        if(actualDBMS.equals("DBMS (SQL)")){
            ConnectionManager.startConnection();
        }
    }

    public static void startView(){
        GraphicController graphicController;
        if (actualViewMS.equals("B/W Screens")) {
            graphicController = new GraphicControllerBW();
        } else {
            graphicController = new GraphicControllerColored();
        }
        graphicController.startView();
    }

    public static void setActualDBMS(String actualDBMS1) {
        actualDBMS = actualDBMS1;
    }

    public static void setActualViewMS(String actualViewMS1) {
        actualViewMS = actualViewMS1;
    }

    public static void setNextDBMS(String dBMS) {
        nextDBMS = dBMS;
        if (!nextDBMS.equals(actualDBMS)) {
            GetGlobalSettings.saveGlobalSettings();
        }
    }

    public static void setNextViewMS(String viewMS) {
        nextViewMS = viewMS;
        if (!nextViewMS.equals(actualViewMS)) {
            GetGlobalSettings.saveGlobalSettings();
        }
    }

    public static String getActualDBMS() {
        return actualDBMS;
    }

    public static String getActualViewMS() {
        return actualViewMS;
    }

    public static String getNextDBMS() {
        return nextDBMS;
    }

    public static String getNextViewMS() {
        return nextViewMS;
    }
}
