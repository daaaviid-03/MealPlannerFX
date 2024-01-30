package org.example.mealplannerfx.coloredScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.example.mealplannerfx.control.DBController;

public abstract class ScreenColoredDef implements Initializable {
    private final GraphicControllerColored graphicCC = GraphicControllerColored.getGCCInstance();
    private final DBController dBController = DBController.getDBControllerInstance();
    @FXML
    private Button avatarButton;
    @FXML
    private Label nicknameText;
    private boolean confirmExit;
    private String previousScreen = "mainMenu";
    private Boolean alertResponse = null;
    private final static String RECIPE_VIEW_SCENE_FXML_FILE_NAME = "screen-colored-zz-viewRecipe-mask.fxml";
    public void initializeDefaultModel(boolean confirmExit) {
        this.confirmExit = confirmExit;
        String nickname = graphicCC.getThisUser().getNickname();
        nicknameText.setText(nickname);
        avatarButton.setText(String.valueOf(nickname.toUpperCase().charAt(0)));
    }
    public void userInfoButtonClicked(ActionEvent actionEvent) {
        graphicCC.startScreenColored("userInfo");
    }
    public void returnButtonClicked(ActionEvent actionEvent) {
        if(!confirmExit || showConfirmationScreen("Exit this screen.", "Cancel", "Exit")){
            returnScreen();
        }
    }
    public void returnScreen() {
        graphicCC.startScreenColored(previousScreen);
    }
    public boolean showConfirmationScreen(String message, String noActionText, String actionText){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Are you sure you want to do the following action?");
        alert.setHeaderText(message);
        ButtonType noActionOption = new ButtonType(noActionText);
        ButtonType actionOption = new ButtonType(actionText);
        alert.getButtonTypes().setAll(noActionOption, actionOption);
        alert.showAndWait().ifPresent(response -> {
            alertResponse = response == actionOption;
        });
        return alertResponse;
    }
    public GraphicControllerColored getGraphicCC() {
        return graphicCC;
    }
    public DBController getDBController() {
        return dBController;
    }
    public void setPreviousScreen(String previousScreenName){
        previousScreen = previousScreenName;
    }
    public void visualizeRecipeInPlane(AnchorPane anchorPane){
        try {
            // Load fxml
            FXMLLoader fxmlLoader = new FXMLLoader(GraphicControllerColored.class.getResource(RECIPE_VIEW_SCENE_FXML_FILE_NAME));
            // Set element in the fxml
            anchorPane.getChildren().setAll((Node)fxmlLoader.load());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
