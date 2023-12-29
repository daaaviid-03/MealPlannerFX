package org.example.mealplannerfx;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GraphicControllerColored extends Application implements GraphicController{
    private DBController dBController = DBController.getDBControllerInstance();
    private final HashMap<String, String> screensFXML;
    private Stage thisStage;
    private static GraphicControllerColored graphicControllerColoredInstance;
    private User thisUser;
    private long dayToExplore;
    private Map<String, Object> namespace;

    public GraphicControllerColored(){
        setGraphicControllerColoredInstance(this);
        this.screensFXML = new HashMap<String, String>();
        this.screensFXML.put("login", "screen-colored-login-view.fxml");
        this.screensFXML.put("register", "screen-colored-register-view.fxml");
        this.screensFXML.put("mainMenu", "screen-colored-mainMenu-view.fxml");
        this.screensFXML.put("newRecipe", "screen-colored-newRecipe-view.fxml");
        this.screensFXML.put("oneDay", "screen-colored-oneDay-view.fxml");
        this.screensFXML.put("searchNewFood", "screen-colored-searchNewFood-view.fxml");
        this.screensFXML.put("shoppingList", "screen-colored-shoppingList-view.fxml");
        this.screensFXML.put("stats", "screen-colored-stats-view.fxml");
        this.screensFXML.put("userInfo", "screen-colored-userInfo-view.fxml");
    }
    private void setGraphicControllerColoredInstance(GraphicControllerColored gcc){
        graphicControllerColoredInstance = gcc;
    }
    public static void main(String[] args){
        launch();
    }

    @Override
    public void startView(){
        GraphicControllerColored.main(null);
    }
    @Override
    public void endView(){

    }


    public void startScreenColored(String screenName){
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicControllerColored.class.getResource(this.screensFXML.get(screenName)));
        namespace = fxmlLoader.getNamespace();
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
            this.thisStage.setScene(scene);
        } catch (Exception e){
            System.err.println("Error loading " + screenName + " screen due to: " + e.getCause() + "\nFrom: " + e);
        }
    }
    public void updateNamespace(FXMLLoader fxmlLoader){
        System.out.println(namespace);
        namespace.putAll(fxmlLoader.getNamespace());
        System.out.println(namespace);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.thisStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicControllerColored.class.getResource("screen-colored-login-view.fxml"));
        namespace = fxmlLoader.getNamespace();
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        System.out.println("Mostrar screen");
        stage.setTitle("MealPlanner");
        stage.setScene(scene);
        stage.show();
    }
    public static GraphicControllerColored getGCCInstance(){
        return graphicControllerColoredInstance;
    }

    public User getThisUser() {
        return thisUser;
    }

    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
    }

    public long getDayToExplore() {
        return dayToExplore;
    }

    public void setDayToExplore(long dayToExplore) {
        this.dayToExplore = dayToExplore;
    }
    public Object searchForObjInScene(String name){
        return namespace.get(name);
    }
}
