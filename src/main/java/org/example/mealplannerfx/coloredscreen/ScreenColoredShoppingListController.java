package org.example.mealplannerfx.coloredscreen;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.mealplannerfx.control.WrongArgException;

import java.net.URL;
import java.util.*;

public class ScreenColoredShoppingListController extends ScreenColoredDefWithStats implements Initializable {
    @FXML
    private ListView<String> listOfIngredients;
    @FXML
    private Label errorText;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModel(false);
    }
    @Override
    public void onDatesChanged() {
        try {
            Map<String, Map<String, Float>> portionsOfIngredients = getAllPortionsOfIngredientDates();
            List<String> shoppingList = new ArrayList<>();
            int cont = 1;
            for (Map.Entry<String, Map<String, Float>> ingredientName : portionsOfIngredients.entrySet()){
                for (String portionName : portionsOfIngredients.get(ingredientName.getKey()).keySet()){
                    shoppingList.add(cont + ". " + ingredientName.getKey() + "  ->  (" +
                            portionsOfIngredients.get(ingredientName.getKey()).get(portionName) + ") units of (" + portionName +
                            ").");
                    cont++;
                }
            }
            listOfIngredients.getItems().setAll(FXCollections.observableArrayList(shoppingList));
            errorText.setText("");
        } catch (WrongArgException e){
            errorText.setText(e.getWrongArgumentDescription());
        } catch (Exception e){
            errorText.setText(e.getMessage());
        }
    }
}
