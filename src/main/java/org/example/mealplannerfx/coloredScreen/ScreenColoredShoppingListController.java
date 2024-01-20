package org.example.mealplannerfx.coloredScreen;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.mealplannerfx.control.WrongArgumentException;
import org.example.mealplannerfx.entity.DayData;
import org.example.mealplannerfx.entity.Recipe;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ScreenColoredShoppingListController extends ScreenColoredDefaultModelWithDayDataStats implements Initializable {
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
            Map<String, Map<String, Float>> portionsOfIngredients = getAllPortionsOfIngredInDates();
            List<String> shoppingList = new ArrayList<>();
            int cont = 1;
            for (String ingredName : portionsOfIngredients.keySet()){
                for (String portionName : portionsOfIngredients.get(ingredName).keySet()){
                    shoppingList.add(cont + ". " + ingredName + "  ->  (" +
                            portionsOfIngredients.get(ingredName).get(portionName) + ") units of (" + portionName +
                            ").");
                    cont++;
                }
            }
            listOfIngredients.getItems().setAll(FXCollections.observableArrayList(shoppingList));
            errorText.setText("");
        } catch (WrongArgumentException e){
            errorText.setText(e.getWrongArgumentDescription());
        } catch (Exception e){
            errorText.setText(e.getMessage());
        }
    }
}
