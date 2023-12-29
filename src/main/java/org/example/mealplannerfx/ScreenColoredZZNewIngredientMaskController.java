package org.example.mealplannerfx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ScreenColoredZZNewIngredientMaskController extends ScreenColoredElementInListMaskController{
    @FXML
    private ComboBox ComboBoxIngredient;
    @FXML
    private TextField QuantityBoxTextIngredient;
    private DBController dBController = DBController.getDBControllerInstance();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComboBoxIngredient.setItems(FXCollections.observableArrayList(dBController.getListOfIngredientsNamesSorted()));
    }

    public void deleteIngredient(ActionEvent actionEvent) {
        super.getControllerSup().deleteIngredient(super.getThisPosition());
    }

    public void addIngredient(ActionEvent actionEvent) {
        super.getControllerSup().addIngredient(super.getThisPosition() + 1);
    }

    public int getQuantityText(){
        return Integer.valueOf(QuantityBoxTextIngredient.getText());
    }
    public String getIngredientName(){
        return ComboBoxIngredient.getValue().toString();
    }

}
