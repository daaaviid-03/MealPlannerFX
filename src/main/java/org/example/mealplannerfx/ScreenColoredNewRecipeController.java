package org.example.mealplannerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ScreenColoredNewRecipeController implements Initializable {

    @FXML
    private TextField nameText;
    @FXML
    private TextArea descriptionText;
    @FXML
    private VBox ingredientsVBox;
    @FXML
    private Button avatarButton;
    @FXML
    private Label nicknameText;
    private GraphicControllerColored graphicCC = GraphicControllerColored.getGCCInstance();
    private DBController dBController = DBController.getDBControllerInstance();
    private List<ScreenColoredZZNewIngredientMaskController> ingredientsList = new ArrayList<ScreenColoredZZNewIngredientMaskController>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String nickname = graphicCC.getThisUser().getNickname();
        nicknameText.setText(nickname);
        avatarButton.setText(String.valueOf(nickname.toUpperCase().charAt(0)));
        this.createNewIngredientMask(0);
    }

    private void createNewIngredientMask(int position){
        try {
            // Load fxml
            FXMLLoader fxmlLoader = new FXMLLoader(GraphicControllerColored.class.getResource("screen-colored-zz-newIngredient-mask.fxml"));
            AnchorPane ingredElem = fxmlLoader.load();
            // Set super controller of ingredient to this controller
            ScreenColoredZZNewIngredientMaskController sczznimc = fxmlLoader.getController();
            sczznimc.setControllerSup(this);
            // Add to list of ingredients
            ingredientsList.add(position, sczznimc);
            // Correct all the indexes of the following ones
            for (int i = position + 1; i < ingredientsList.size(); i++) {
                ingredientsList.get(i).setThisPositionPlusOne();
            }
            // Add ingredient to list in the fxml
            ingredientsVBox.getChildren().add(position, ingredElem);
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private void deleteIngredientMask(int pos){
        try {
            // Remove item from list of ingredients
            ingredientsList.remove(pos);
            // Correct all the indexes of the following ones
            for (int i = pos; i < ingredientsList.size(); i++) {
                ingredientsList.get(i).setThisPositionMinusOne();
            }
            // Remove ingredient from list in the fxml
            ingredientsVBox.getChildren().remove(pos);
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }



    public void userInfoButtonClicked(ActionEvent actionEvent) {
        graphicCC.startScreenColored("userInfo");
    }

    public void returnButtonClicked(ActionEvent actionEvent) {
        graphicCC.startScreenColored("mainMenu");
    }

    public void deleteIngredient(int pos) {
        if (ingredientsList.size() > 1) {
            deleteIngredientMask(pos);
        }
    }

    public void addIngredient(int pos) {
        createNewIngredientMask(pos);
    }

    public void upArrowStepClicked(int pos) {
    }

    public void downArrowStepClicked(int pos) {
    }

    public void deleteStep(int pos) {
    }

    public void addStep(int pos) {
    }

    public void createRecipeClicked(ActionEvent actionEvent) {
    }

    public void upArrowStepClicked(ActionEvent actionEvent) {
    }

    public void downArrowStepClicked(ActionEvent actionEvent) {
    }

    public void deleteStep(ActionEvent actionEvent) {
    }

    public void addStep(ActionEvent actionEvent) {
    }
}
