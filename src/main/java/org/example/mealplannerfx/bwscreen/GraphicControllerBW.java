package org.example.mealplannerfx.bwscreen;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.mealplannerfx.control.GraphicController;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.entity.User;

import java.util.HashMap;
import java.util.Map;

public class GraphicControllerBW extends Application implements GraphicController {
    private static final HashMap<String, String> screensFXML = new HashMap<>();
    private static final double WINDOW_HEIGHT_DEFAULT_BW = 1080;
    private static final double WINDOW_WIDTH_DEFAULT_BW = 1920;
    private static final double WINDOW_HEIGHT = 720;
    private static final double WINDOW_WIDTH = 1280;
    private static final String FIRST_SCREEN_TO_SHOW_NAME = "init";
    // Objects of window
    /**
     * The anchorPane that surrounds the scene to be able to resize the screen
     */
    private static final AnchorPane anchorPaneResizeBW = new AnchorPane();
    private static Map<String, Object> namespace;
    private static FXMLLoader thisFxmlLoader;
    // Objects of saved messages
    /**
     * Whether is breakfast, launch or dinner.
     */
    private static String mealNameOfLastSelected;
    /**
     * Active user in the system
     */
    private static User thisUser;
    /**
     * Active day to explore in the system
     */
    private static long dayToExplore;
    /**
     * Last selected recipe from the screen of searching recipes to the one-day screen.
     */
    private static Recipe lastRecipeSelected;
    /**
     * The recipe to show, if there is any
     */
    private static Recipe recipeToShow;

    @Override
    public void init(){
        screensFXML.put(FIRST_SCREEN_TO_SHOW_NAME, "screen-bw-init-view.fxml");
        screensFXML.put("login", "screen-bw-login-view.fxml");
        screensFXML.put("register", "screen-bw-register-view.fxml");
        screensFXML.put("mainMenu", "screen-bw-mainMenu-view.fxml");
        screensFXML.put("newRecipe", "screen-bw-newRecipe-view.fxml");
        screensFXML.put("daySelect", "screen-bw-day-select-view.fxml");
        screensFXML.put("oneDay", "screen-bw-oneDay-view.fxml");
        screensFXML.put("searchNewFood", "screen-bw-search-view.fxml");
        screensFXML.put("shoppingList", "screen-bw-shoppingList-view.fxml");
        screensFXML.put("stats", "screen-bw-stats-view.fxml");
        screensFXML.put("userInfo", "screen-bw-userInfo-view.fxml");
    }

    public static void main(String[] args){
        launch(GraphicControllerBW.class);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("MealPlanner");
        // Set listener to the change of the size of the window
        stage.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            anchorPaneResizeBW.setScaleY(newHeight.doubleValue()/ WINDOW_HEIGHT_DEFAULT_BW);
            anchorPaneResizeBW.setTranslateY(-(WINDOW_HEIGHT_DEFAULT_BW - newHeight.doubleValue())/2);
        });
        stage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            anchorPaneResizeBW.setScaleX(newWidth.doubleValue()/ WINDOW_WIDTH_DEFAULT_BW);
            anchorPaneResizeBW.setTranslateX(-(WINDOW_WIDTH_DEFAULT_BW - newWidth.doubleValue())/2);
        });
        // Start first screen
        startScreenBW(FIRST_SCREEN_TO_SHOW_NAME);
        // Surround the anchor plane that resize with the other one
        AnchorPane anchorPaneSup = new AnchorPane();
        anchorPaneSup.getChildren().setAll(anchorPaneResizeBW);
        Scene scene = new Scene(anchorPaneSup, WINDOW_WIDTH, WINDOW_HEIGHT);
        // Set the scene
        stage.setScene(scene);
        // Show stage
        stage.show();
    }
    @Override
    public void startView(){
        main(null);
    }

    /**
     * Starts a new screen in the stage
     * @param screenName the key name of the screen
     */
    public static void startScreenBW(String screenName){
        thisFxmlLoader = new FXMLLoader(GraphicControllerBW.class.getResource(screensFXML.get(screenName)));
        namespace = thisFxmlLoader.getNamespace();
        try {
            Parent parent = thisFxmlLoader.load();
            // Surround the scene with the anchor plane that resize
            anchorPaneResizeBW.getChildren().setAll(parent);
        } catch (Exception e){
            // No action
        }
    }
    public static void startScreenBW(String screenName, String previousScreen){
        startScreenBW(screenName);
        if (previousScreen != null){
            ((ScreenBWDef)thisFxmlLoader.getController()).setPreviousScreen(previousScreen);
        }
    }
    public static void setMealNameOfLastSelected(String mealNameOfLastSelected1) {
        mealNameOfLastSelected = mealNameOfLastSelected1;
    }
    public static User getThisUser() {
        return thisUser;
    }
    public static void setThisUser(User thisUser1) {
        thisUser = thisUser1;
    }
    public static long getDayToExplore() {
        return dayToExplore;
    }
    public static void setDayToExplore(long dayToExplore1) {
        dayToExplore = dayToExplore1;
    }
    public static Object searchForObjInScene(String name){
        return namespace.get(name);
    }
    public static Recipe getLastRecipeSelected() {
        return lastRecipeSelected;
    }
    public static void setLastRecipeSelected(Recipe lastRecipeSelected1) {
        lastRecipeSelected = lastRecipeSelected1;
    }
    public static void setRecipeToShow(Recipe recipeToShow1) {
        recipeToShow = recipeToShow1;
    }
    public static Recipe getRecipeToShow() {
        return recipeToShow;
    }
    public static String getMealNameOfLastSelected() {
        return mealNameOfLastSelected;
    }
}
