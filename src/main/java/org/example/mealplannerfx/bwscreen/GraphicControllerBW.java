package org.example.mealplannerfx.bwscreen;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.mealplannerfx.control.GraphicController;

import java.util.HashMap;

public class GraphicControllerBW extends Application implements GraphicController {
    private HashMap<String, String> screensFXML;

    private static GraphicControllerBW graphicControllerBWInstance;

    public GraphicControllerBW(){
        //this.screensFXML.put("login", "screen-colored-login-view.fxml");
    }

    @Override
    public void startView(){

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
