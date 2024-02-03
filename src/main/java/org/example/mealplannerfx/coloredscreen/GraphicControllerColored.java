package org.example.mealplannerfx.coloredscreen;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.entity.User;
import org.example.mealplannerfx.control.GraphicController;

import java.util.HashMap;
import java.util.Map;

public class GraphicControllerColored extends Application implements GraphicController {
    private static final double WINDOW_HEIGHT_DEFAULT = 1080;
    private static final double WINDOW_WIDTH_DEFAULT = 1920;
    private static final double WINDOW_HEIGHT = 720;
    private static final double WINDOW_WIDTH = 1280;
    private static final String FIRST_SCREEN_TO_SHOW_NAME = "login";
    private static final HashMap<String, String> screensFXML = new HashMap<>();
    // Objects of window
    /**
     * The anchorPane that surrounds the scene to be able to resize the screen
     */
    private static final AnchorPane anchorPaneResize = new AnchorPane();
    private static Map<String, Object> namespace;
    private static FXMLLoader thisFxmlLoader;
    // Objects of saved messages
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
    /**
     * Whether is breakfast, launch or dinner.
     */
    private static String mealNameOfLastSelected;
    @Override
    public void init(){
        screensFXML.put(FIRST_SCREEN_TO_SHOW_NAME, "screen-colored-login-view.fxml");
        screensFXML.put("register", "screen-colored-register-view.fxml");
        screensFXML.put("mainMenu", "screen-colored-mainMenu-view.fxml");
        screensFXML.put("newRecipe", "screen-colored-newRecipe-view.fxml");
        screensFXML.put("oneDay", "screen-colored-oneDay-view.fxml");
        screensFXML.put("searchNewFood", "screen-colored-search-view.fxml");
        screensFXML.put("shoppingList", "screen-colored-shoppingList-view.fxml");
        screensFXML.put("stats", "screen-colored-stats-view.fxml");
        screensFXML.put("userInfo", "screen-colored-userInfo-view.fxml");
    }

    public static void main(String[] args){
        launch(GraphicControllerColored.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("MealPlanner");
        // Set listener to the change of the size of the window
        stage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            anchorPaneResize.setScaleX(newWidth.doubleValue()/WINDOW_WIDTH_DEFAULT);
            anchorPaneResize.setTranslateX(-(WINDOW_WIDTH_DEFAULT - newWidth.doubleValue())/2);
        });
        stage.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            anchorPaneResize.setScaleY(newHeight.doubleValue()/WINDOW_HEIGHT_DEFAULT);
            anchorPaneResize.setTranslateY(-(WINDOW_HEIGHT_DEFAULT - newHeight.doubleValue())/2);
        });
        // Start first screen
        startScreenColored(FIRST_SCREEN_TO_SHOW_NAME);
        // Surround the anchor plane that resize with the other one
        AnchorPane anchorPaneSup = new AnchorPane();
        anchorPaneSup.getChildren().setAll(anchorPaneResize);
        Scene scene = new Scene(anchorPaneSup, WINDOW_WIDTH, WINDOW_HEIGHT);
        // Set the scene
        stage.setScene(scene);
        // Show stage
        stage.show();
    }
    @Override
    public void startView(){
        GraphicControllerColored.main(null);
    }

    /**
     * Starts a new screen in the stage
     * @param screenName the key name of the screen
     */
    public static void startScreenColored(String screenName){
        thisFxmlLoader = new FXMLLoader(GraphicControllerColored.class.getResource(screensFXML.get(screenName)));
        namespace = thisFxmlLoader.getNamespace();
        try {
            Node parent = thisFxmlLoader.load();
            // Surround the scene with the anchor plane that resize
            anchorPaneResize.getChildren().setAll(parent);
        } catch (Exception e){
            throw new RuntimeException(e);
            // No action
        }
    }
    public static void startScreenColored(String screenName, String previousScreen){
        startScreenColored(screenName);
        if (previousScreen != null){
            ((ScreenColoredDef)thisFxmlLoader.getController()).setPreviousScreen(previousScreen);
        }
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
    public static void setMealNameOfLastSelected(String mealNameOfLastSelected1) {
        mealNameOfLastSelected = mealNameOfLastSelected1;
    }
}
