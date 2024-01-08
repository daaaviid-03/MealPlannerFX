package org.example.mealplannerfx;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GraphicControllerColored extends Application implements GraphicController{
    private final HashMap<String, String> screensFXML;
    private Stage thisStage;
    private static GraphicControllerColored graphicControllerColoredInstance;
    private User thisUser;
    private long dayToExplore;
    private Map<String, Object> namespace;
    private FXMLLoader thisFxmlLoader;
    private Recipe lastRecipeSelected;
    private String mealNameOfLastSelected;
    private Recipe dayToShow;

    public GraphicControllerColored(){
        setGraphicControllerColoredInstance(this);
        this.screensFXML = new HashMap<String, String>();
        this.screensFXML.put("login", "screen-colored-login-view.fxml"); // User
        this.screensFXML.put("register", "screen-colored-register-view.fxml"); // User
        this.screensFXML.put("mainMenu", "screen-colored-mainMenu-view.fxml"); // dayToExplore
        this.screensFXML.put("newRecipe", "screen-colored-newRecipe-view.fxml"); // lastRecipeSelected
        this.screensFXML.put("oneDay", "screen-colored-oneDay-view.fxml"); //
        this.screensFXML.put("searchNewFood", "screen-colored-searchNewFood-view.fxml"); // lastRecipeSelected
        this.screensFXML.put("shoppingList", "screen-colored-shoppingList-view.fxml"); //
        this.screensFXML.put("stats", "screen-colored-stats-view.fxml"); //
        this.screensFXML.put("userInfo", "screen-colored-userInfo-view.fxml"); //
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
        thisFxmlLoader = new FXMLLoader(GraphicControllerColored.class.getResource(this.screensFXML.get(screenName)));
        namespace = thisFxmlLoader.getNamespace();
        try {
            Parent parent = thisFxmlLoader.load();
            Scene scene = new Scene(parent, 1920, 1080);
            this.thisStage.setScene(scene);
        } catch (Exception e){
            System.err.println("Error loading " + screenName + " screen due to: " + e.getCause() + "\nFrom: " + e.getMessage());
        }
    }
    public void startScreenColored(String screenName, String previousScreen){
        startScreenColored(screenName);
        try {
            if (previousScreen != null){
                ((ScreenColoredDefaultModel) (thisFxmlLoader.getController())).setPreviousScreen(previousScreen);
            }
        } catch (Exception ignore){}
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
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent, 1920, 1080);
        stage.setTitle("MealPlanner");
        stage.setScene(scene);

        // Make it responsive
//        stage.widthProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth) {
//                double ratio = newWidth.doubleValue() / thisStage.getWidth();
//                System.out.println(newWidth.floatValue());
//                group.setMinWidth(newWidth.doubleValue());
//                //group.getTransforms().add(new Scale(newWidth.floatValue() / 1920, 1080));
//                //stage.setWidth(stage.getWidth() * ratio);
//            }
//        });
//        stage.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
//                double ratio = newHeight.doubleValue() / thisStage.getHeight();
//                group.setMaxHeight(ratio);
//                //stage.setHeight(stage.getHeight() * ratio);
//            }
//        });

        // Show stage
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

    public Recipe getLastRecipeSelected() {
        return lastRecipeSelected;
    }

    public void setLastRecipeSelected(Recipe lastRecipeSelected) {
        this.lastRecipeSelected = lastRecipeSelected;
    }

    public void setDayToShow(Recipe dayToShow) {
        this.dayToShow = dayToShow;
    }

    public Recipe getDayToShow() {
        return dayToShow;
    }

    public String getMealNameOfLastSelected() {
        return mealNameOfLastSelected;
    }

    public void setMealNameOfLastSelected(String mealNameOfLastSelected) {
        this.mealNameOfLastSelected = mealNameOfLastSelected;
    }
}
