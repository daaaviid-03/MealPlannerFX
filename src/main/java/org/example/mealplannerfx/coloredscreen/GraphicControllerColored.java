package org.example.mealplannerfx.coloredscreen;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
    private final static double WINDOW_HEIGHT_DEFAULT = 1080;
    private final static double WINDOW_WIDTH_DEFAULT = 1920;
    private final static double WINDOW_HEIGHT = 720;
    private final static double WINDOW_WIDTH = 1280;
    private final static String FIRST_SCREEN_TO_SHOW_NAME = "login";
    private static GraphicControllerColored graphicControllerColoredInstance;
    private final HashMap<String, String> screensFXML = new HashMap<>();
    // Objects of window
    /**
     * The anchorPane that surrounds the scene to be able to resize the screen
     */
    private final AnchorPane anchorPaneResize = new AnchorPane();
    private Map<String, Object> namespace;
    private FXMLLoader thisFxmlLoader;
    // Objects of saved messages
    /**
     * Active user in the system
     */
    private User thisUser;
    /**
     * Active day to explore in the system
     */
    private long dayToExplore;
    /**
     * Last selected recipe from the screen of searching recipes to the one-day screen.
     */
    private Recipe lastRecipeSelected;
    /**
     * The recipe to show, if there is any
     */
    private Recipe recipeToShow;
    /**
     * Whether is breakfast, launch or dinner.
     */
    private String mealNameOfLastSelected;
    private void setGraphicControllerColoredInstance(GraphicControllerColored gcc){
        graphicControllerColoredInstance = gcc;
    }
    public static void main(String[] args){
        launch(GraphicControllerColored.class);
    }
    @Override
    public void init(){
        setGraphicControllerColoredInstance(this);
        this.screensFXML.put(FIRST_SCREEN_TO_SHOW_NAME, "screen-colored-login-view.fxml");
        this.screensFXML.put("register", "screen-colored-register-view.fxml");
        this.screensFXML.put("mainMenu", "screen-colored-mainMenu-view.fxml");
        this.screensFXML.put("newRecipe", "screen-colored-newRecipe-view.fxml");
        this.screensFXML.put("oneDay", "screen-colored-oneDay-view.fxml");
        this.screensFXML.put("searchNewFood", "screen-colored-search-view.fxml");
        this.screensFXML.put("shoppingList", "screen-colored-shoppingList-view.fxml");
        this.screensFXML.put("stats", "screen-colored-stats-view.fxml");
        this.screensFXML.put("userInfo", "screen-colored-userInfo-view.fxml");
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
    public void startScreenColored(String screenName){
        thisFxmlLoader = new FXMLLoader(GraphicControllerColored.class.getResource(this.screensFXML.get(screenName)));
        namespace = thisFxmlLoader.getNamespace();
        try {
            Parent parent = thisFxmlLoader.load();
            // Surround the scene with the anchor plane that resize
            anchorPaneResize.getChildren().setAll(parent);
        } catch (Exception e){
            return;
        }
    }
    public void startScreenColored(String screenName, String previousScreen){
        startScreenColored(screenName);
        if (previousScreen != null){
            ((ScreenColoredDef)thisFxmlLoader.getController()).setPreviousScreen(previousScreen);
        }
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
    public void setRecipeToShow(Recipe recipeToShow) {
        this.recipeToShow = recipeToShow;
    }
    public Recipe getRecipeToShow() {
        return recipeToShow;
    }
    public String getMealNameOfLastSelected() {
        return mealNameOfLastSelected;
    }
    public void setMealNameOfLastSelected(String mealNameOfLastSelected) {
        this.mealNameOfLastSelected = mealNameOfLastSelected;
    }
}
