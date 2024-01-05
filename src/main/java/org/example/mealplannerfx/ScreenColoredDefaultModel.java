package org.example.mealplannerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class ScreenColoredDefaultModel implements Initializable {
    private GraphicControllerColored graphicCC = GraphicControllerColored.getGCCInstance();
    private DBController dBController = DBController.getDBControllerInstance();
    @FXML
    private Button avatarButton;
    @FXML
    private Label nicknameText;
    private boolean confirmExit;
    private String previousScreen = "mainMenu";
    private final static String RECIPE_VIEW_SCENE_FXML_FILE_NAME = "screen-colored-zz-viewRecipe.fxml";
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
        if(confirmExit && !showConfirmationScreen("")){
            return;
        }
        graphicCC.startScreenColored(previousScreen);
    }
    private boolean showConfirmationScreen(String stringToConfirm){
        System.out.println("Confirmed");
        return true;
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
            anchorPane.getChildren().set(0, fxmlLoader.load());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
