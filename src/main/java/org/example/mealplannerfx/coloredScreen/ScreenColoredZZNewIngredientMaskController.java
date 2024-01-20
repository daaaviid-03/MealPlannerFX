package org.example.mealplannerfx.coloredScreen;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.mealplannerfx.control.DBController;

import java.net.URL;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.ResourceBundle;

public class ScreenColoredZZNewIngredientMaskController extends ScreenColoredElementInListMaskController{
    private static final int MAX_INGREDIENTS_IN_VIEW = 4;
    @FXML
    private TextField ingredientText;
    @FXML
    private ListView ingredientListText;
    @FXML
    private ComboBox unitComboBox;
    @FXML
    private TextField quantityBoxTextIngredient;
    private final DBController dBController = DBController.getDBControllerInstance();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideListOfElements();
        ingredientText.textProperty().addListener((observable, oldValue, newValue) -> {
            showFilteredElements(newValue);
        });

        ingredientText.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                hideListOfElements();
            } else {
                showFilteredElements(ingredientText.getText());
            }
        });

        ingredientListText.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValueObj, newValueObj) -> {
            selectedElement(newValueObj.toString());
        });
    }

    private void hideListOfElements(){
        ingredientListText.setVisible(false);
        ingredientListText.setMinHeight(0);
        ingredientListText.setPrefHeight(0);
        ingredientListText.setMaxHeight(0);
    }

    private void showFilteredElements(String newValue) {
        List<String> foundedElements = dBController.getListOfIngredientsNamesSortedBy(newValue);
        // If an elements has been selected do not show the list
        boolean toShowElements = !(foundedElements.size() == 1 && foundedElements.getFirst().equals(ingredientText.getText()));
        ingredientListText.setVisible(toShowElements);
        if (!ingredientListText.isVisible()){
            foundedElements = new ArrayList<>();
        }
        // Set View size
        double size = Math.min(MAX_INGREDIENTS_IN_VIEW, foundedElements.size()) * ingredientListText.getFixedCellSize();
        ingredientListText.setMinHeight(size);
        ingredientListText.setPrefHeight(size);
        ingredientListText.setMaxHeight(size);
        ingredientListText.getItems().setAll(FXCollections.observableArrayList(foundedElements));

    }

    private void selectedElement(String newValue){
        ingredientText.setText(newValue);
        try {
            unitComboBox.setItems(FXCollections.observableArrayList(dBController.getIngredientPortionsNames(newValue)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteIngredient(ActionEvent actionEvent) {
        getControllerSup().deleteIngredient(getThisPosition(), getThisVBox());
    }

    public void addIngredient(ActionEvent actionEvent) {
        getControllerSup().addIngredient(getThisPosition() + 1, getThisVBox());
    }

    public float getQuantityText(){
        return Float.parseFloat(quantityBoxTextIngredient.getText());
    }

    public String getIngredientName(){
        return ingredientText.getText().toString();
    }

    public String getPortionName() {
        String text = unitComboBox.getValue().toString();
        if (text.equals(unitComboBox.getPromptText())){
            throw new EmptyStackException();
        } else {
            return text;
        }
    }

    public void deletePortions(){
        unitComboBox.setPrefWidth(0d);
        unitComboBox.setVisible(false);
        quantityBoxTextIngredient.setPrefWidth(0d);
        quantityBoxTextIngredient.setVisible(false);
    }
}