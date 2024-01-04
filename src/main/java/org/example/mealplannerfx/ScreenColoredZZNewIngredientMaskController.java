package org.example.mealplannerfx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.EmptyStackException;
import java.util.ResourceBundle;

public class ScreenColoredZZNewIngredientMaskController extends ScreenColoredElementInListMaskController{
    @FXML
    private TextField ingredientText;
    @FXML
    private ListView ingredientListText;
    @FXML
    private ComboBox unitComboBox;
    @FXML
    private TextField QuantityBoxTextIngredient;
    private final DBController dBController = DBController.getDBControllerInstance();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ingredientListText.setItems(FXCollections.observableArrayList(dBController.getListOfIngredientsNamesSorted().subList(0, 10)));
        ingredientText.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarElementos(newValue, ingredientListText);
        });
    }

    private void filtrarElementos(String newValue, ListView<String> ingredientListText) {
        System.out.println("Buscando...");
        ingredientListText.setItems(FXCollections.observableArrayList(dBController.getListOfIngredientsNamesSortedBy(newValue, 10)));
        System.out.println("Encontrado");
    }

    public void deleteIngredient(ActionEvent actionEvent) {
        super.getControllerSup().deleteIngredient(super.getThisPosition());
    }

    public void addIngredient(ActionEvent actionEvent) {
        super.getControllerSup().addIngredient(super.getThisPosition() + 1);
    }

    public float getQuantityText(){
        return Float.parseFloat(QuantityBoxTextIngredient.getText());
    }

    public String getIngredientName(){
        return ingredientText.getText().toString();
    }

//    public void ingredientChangedBox(ActionEvent actionEvent) {
//        String text = ComboBoxIngredient.getValue().toString();
//        System.out.println("Texto cambiado a: " + text);
//        if(dBController.getIngredientByName(text) != null){
//            System.out.println("Ingrediente seleccionado");
//            unitComboBox.setItems(FXCollections.observableArrayList(dBController.getIngredientPortionsNames(text)));
//        } else {
//
//        }
//    }

    public String getPortionName() {
        String text = unitComboBox.getValue().toString();
        if (text.equals(unitComboBox.getPromptText())){
            throw new EmptyStackException();
        } else {
            return text;
        }
    }
}
