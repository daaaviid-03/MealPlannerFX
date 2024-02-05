package org.example.mealplannerfx.bwscreen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.example.mealplannerfx.entity.Recipe;

public abstract class ScreenBWDef implements Initializable {
    @FXML
    private Label nicknameText;
    private boolean confirmExit;
    private String previousScreen = "mainMenu";
    private Boolean alertResponse = null;
    private static final String RECIPE_VIEW_SCENE_FXML_FILE_NAME = "screen-bw-zz-viewRecipe-mask.fxml";
    private String nameThisScreen;
    public void initializeDefaultModelBW(String nameThisScreen, boolean confirmExit) {
        this.nameThisScreen = nameThisScreen;
        this.confirmExit = confirmExit;
        String nickname = GraphicControllerBW.getThisUser().getNickname();
        nicknameText.setText(nickname);
    }
    public void userInfoButtonClicked() {
        GraphicControllerBW.startScreenBW("userInfo", nameThisScreen);
    }
    public void returnButtonClicked() {
        if(!confirmExit || showConfirmationScreen("Exit this screen.", "Cancel", "Exit")){
            returnScreen();
        }
    }
    public void returnScreen() {
        if (previousScreen.equals(nameThisScreen)){
            previousScreen = "init";
        }
        GraphicControllerBW.startScreenBW(previousScreen);
    }
    public boolean showConfirmationScreen(String message, String noActionText, String actionText){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Are you sure you want to do the following action?");
        alert.setHeaderText(message);
        ButtonType noActionOption = new ButtonType(noActionText);
        ButtonType actionOption = new ButtonType(actionText);
        alert.getButtonTypes().setAll(noActionOption, actionOption);
        alert.showAndWait().ifPresent(response -> alertResponse = response == actionOption);
        return alertResponse;
    }
    public void setPreviousScreen(String previousScreenName){
        previousScreen = previousScreenName;
    }
    public void visualizeRecipeInPlane(AnchorPane anchorPane, Recipe recipe){
        try {
            GraphicControllerBW.setRecipeToShow(recipe);
            // Load fxml
            FXMLLoader fxmlLoader = new FXMLLoader(GraphicControllerBW.class.getResource(RECIPE_VIEW_SCENE_FXML_FILE_NAME));
            // Set element in the fxml
            anchorPane.getChildren().setAll((Node)fxmlLoader.load());
        } catch (Exception e) {
            // No action
        }
    }
}
