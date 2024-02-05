package org.example.mealplannerfx.bwscreen;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.mealplannerfx.control.WrongArgException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ScreenBWShoppingListController extends ScreenBWDefWithStats implements Initializable {
    @FXML
    private Label errorText;
    @FXML
    private ListView<String> listOfIngredients;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModelBW("shoppingList", false);
    }
    @Override
    public void onDatesChangedBW() {
        try {
            Map<String, Map<String, Float>> portionsOfIngredients = getAllPortionsOfIngredientDates();
            List<String> shoppingList = new ArrayList<>();
            int cont = 1;
            for (Map.Entry<String, Map<String, Float>> ingredientName : portionsOfIngredients.entrySet()){
                for (String portionName1 : portionsOfIngredients.get(ingredientName.getKey()).keySet()){
                    shoppingList.add(cont + ". " + ingredientName.getKey() +
                            "  ->  (" + portionsOfIngredients.get(ingredientName.getKey()).get(portionName1) +
                            ") units of (" + portionName1 + ").");

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
