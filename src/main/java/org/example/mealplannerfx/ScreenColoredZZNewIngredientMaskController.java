package org.example.mealplannerfx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ScreenColoredZZNewIngredientMaskController implements Initializable {
    @FXML
    private HBox IngredientDiv;
    @FXML
    private ComboBox ComboBoxIngredient;
    @FXML
    private TextField QuantityBoxTextIngredient;
    @FXML
    private Button deleteIngredientButton;
    @FXML
    private Button addIngredientButton;
    private ScreenColoredNewRecipeController controllerSup;
    private GraphicControllerColored graphicCC = GraphicControllerColored.getGCCInstance();
    private DBController dBController = DBController.getDBControllerInstance();
    private int thisPosition;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Inicializado");
        ComboBoxIngredient.setItems(FXCollections.observableArrayList(dBController.getListOfIngredientsNames()));
    }

    public void deleteIngredient(ActionEvent actionEvent) {
        controllerSup.deleteIngredient(thisPosition);
    }

    public void addIngredient(ActionEvent actionEvent) {
        controllerSup.addIngredient(thisPosition);
    }

    public void setControllerSup(ScreenColoredNewRecipeController controllerSup) {
        this.controllerSup = controllerSup;
    }

    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }
    public void setThisPositionPlusOne() {
        this.thisPosition++;
    }
    public void setThisPositionMinusOne() {
        this.thisPosition--;
    }
    public float getQuantityText(){
        return Float.valueOf(QuantityBoxTextIngredient.getText());
    }
    public Object getIngredientName(){
        return ComboBoxIngredient.getValue();
    }
}
