package org.example.mealplannerfx.bwscreen;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.entity.Ingredient;

import java.net.URL;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.ResourceBundle;

public class ScreenBWInListNewIngredientController extends ScreenBWDefWithList {
    private static final int MAX_INGREDIENTS_IN_VIEW = 4;
    @FXML
    private ComboBox<String> unitComboBox;
    @FXML
    private ListView<Ingredient> ingredientListText;
    @FXML
    private TextField ingredientText;
    @FXML
    private TextField quantityBoxTextIngredient;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideListOfElements();

        ingredientListText.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValueObj, newValueObj) -> {
            if (newValueObj != null){
                selectElement(newValueObj.toString());
                hideListOfElements();
            }
        });

        ingredientText.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                hideListOfElements();
            }
        });
    }

    private void hideListOfElements(){
        ingredientListText.setVisible(false);
        ingredientListText.setPrefHeight(0);
        ingredientListText.setMaxHeight(0);
        ingredientListText.setMinHeight(0);
    }

    public void showFilteredElements(String newValue) {
        List<Ingredient> foundedElements = DBController.getListOfAllIngredientsByName(newValue, null);
        boolean toShowElements = !(foundedElements.size() == 1 && foundedElements.getFirst().getName().equals(ingredientText.getText()));
        ingredientListText.setVisible(toShowElements);
        if (!ingredientListText.isVisible()){
            foundedElements = new ArrayList<>();
        }
        // Set View size
        double size = Math.min(MAX_INGREDIENTS_IN_VIEW, foundedElements.size()) * ingredientListText.getFixedCellSize();
        ingredientListText.setPrefHeight(size);
        ingredientListText.setMaxHeight(size);
        ingredientListText.setMinHeight(size);
        ingredientListText.getItems().setAll(FXCollections.observableArrayList(foundedElements));
    }

    private void selectElement(String newValue){
        ingredientText.setText(newValue);
        try {
            unitComboBox.setItems(FXCollections.observableArrayList(DBController.getIngredientPortionsNames(newValue)));
        } catch (Exception e) {
            // No action
        }
    }

    public void deleteIngredients() {
        getControllerSup().deleteIngredient(getThisPosition(), getThisVBox());
    }

    public void addIngredients() {
        getControllerSup().addIngredient(getThisPosition() + 1, getThisVBox());
    }

    public float getQuantityTextValue(){
        return Float.parseFloat(quantityBoxTextIngredient.getText());
    }

    public String getIngredientNameValue(){
        return ingredientText.getText();
    }

    public String getPortionNameValue() {
        String text = unitComboBox.getValue();
        if (text.equals(unitComboBox.getPromptText())){
            throw new EmptyStackException();
        } else {
            return text;
        }
    }

    public void deletePortions(){
        quantityBoxTextIngredient.setPrefWidth(0d);
        quantityBoxTextIngredient.setVisible(false);
        unitComboBox.setPrefWidth(0d);
        unitComboBox.setVisible(false);
    }

    public void searchIngredientsButtonClicked() {
        showFilteredElements(ingredientText.getText());
    }
}
