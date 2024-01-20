package org.example.mealplannerfx.bwScreen;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.control.GraphicController;

import java.util.HashMap;

public class GraphicControllerBW extends Application implements GraphicController {
    private DBController dBController;
    private HashMap<String, String> screensFXML;

    private static GraphicControllerBW graphicControllerBWInstance;

    public GraphicControllerBW(){
        this.dBController = DBController.getDBControllerInstance();
        //this.screensFXML.put("login", "screen-colored-login-view.fxml");
    }

    @Override
    public void startView(){

    }
    @Override
    public void endView(){

    }
    private void StartScreenColored(String screenName){

    }
    @Override
    public void start(Stage stage) throws Exception {

    }
    public static GraphicControllerBW getGCBWInstance(){
        return graphicControllerBWInstance;
    }
}