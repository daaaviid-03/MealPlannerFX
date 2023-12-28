package org.example.mealplannerfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
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
    private ScrollPane ingredientsScrollPlane;
    @FXML
    private AnchorPane IngredientsInnerSizePlane;
    @FXML
    private HBox IngredientDiv_0;
    @FXML
    private ScrollPane stepsScrollPlane;
    @FXML
    private AnchorPane stepsInnerSizePlane;
    @FXML
    private HBox stepsDiv_0;
    @FXML
    private Button avatarButton;
    @FXML
    private Label nicknameText;
    private GraphicControllerColored graphicCC = GraphicControllerColored.getGCCInstance();
    private DBController dBController = DBController.getDBControllerInstance();
    private HBox IngredientDiv_exaple;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String nickname = graphicCC.getThisUser().getNickname();
        nicknameText.setText(nickname);
        avatarButton.setText(String.valueOf(nickname.toUpperCase().charAt(0)));

        try {
            IngredientsInnerSizePlane.getChildren().set(1, FXMLLoader.load(GraphicControllerColored.class.getResource("screen-colored-zz-newIngredient-mask.fxml")));
            //IngredientsInnerSizePlane.getChildren().set(1, FXMLLoader.load(GraphicControllerColored.class.getResource("screen-colored-zz-newIngredient-mask.fxml")));
            //IngredientsInnerSizePlane.getChildren().set(2, FXMLLoader.load(GraphicControllerColored.class.getResource("screen-colored-zz-newIngredient-mask.fxml")));

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

//        ComboBox comboBox = (ComboBox)graphicCC.searchForObjInScene("ComboBoxIngredient_0");
//        comboBox.setItems(FXCollections.observableArrayList(dBController.getListOfIngredientsNames()));

//        IngredientDiv_exaple = new HBox(IngredientDiv_0);
//        IngredientDiv_exaple.setId("copiaId");
//        IngredientsInnerSizePlane.getChildren().add(IngredientDiv_exaple);

    }

    public void userInfoButtonClicked(ActionEvent actionEvent) {
        graphicCC.startScreenColored("userInfo");
    }

    public void returnButtonClicked(ActionEvent actionEvent) {
        graphicCC.startScreenColored("mainMenu");
    }

    public void deleteIngredient(ActionEvent actionEvent) {
    }

    public void addIngredient(ActionEvent actionEvent) {
    }

    public void upArrowStepClicked(ActionEvent actionEvent) {
    }

    public void downArrowStepClicked(ActionEvent actionEvent) {
    }

    public void deleteStep(ActionEvent actionEvent) {
    }

    public void addStep(ActionEvent actionEvent) {
    }

    public void createRecipeClicked(ActionEvent actionEvent) {
    }
}
