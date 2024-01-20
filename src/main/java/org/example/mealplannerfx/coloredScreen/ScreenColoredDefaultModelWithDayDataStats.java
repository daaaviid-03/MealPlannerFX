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

public abstract class ScreenColoredDefaultModelWithDayDataStats extends ScreenColoredDefaultModel implements Initializable {
    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;

    @Override
    public void initializeDefaultModel(boolean confirmExit){
        super.initializeDefaultModel(confirmExit);
        fromDate.setValue(LocalDate.now());
        toDate.setValue(LocalDate.now().plusDays(7));
        onDatesChanged();
        fromDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            onDatesChanged();
        });
        toDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            onDatesChanged();
        });
    }

    public Map<String, Map<String, Float>> getAllPortionsOfIngredInDates() throws Exception {
        long fromDateLong = fromDate.getValue().toEpochDay();
        long toDateLong = toDate.getValue().toEpochDay();
        if (fromDateLong > toDateLong){
            throw new WrongArgumentException("The first date can't be after the second date.");
        }
        // It's the map of all ingredients in between that days that ar used in recipes, and a map of portions
        // of each ingredient.
        Map<String, Map<String, Float>> portionsOfIngredients = new HashMap<>();
        for (DayData dayData : getDBController().getDaysData(fromDateLong, toDateLong)){
            getIngredientsPorionsFromRecipe(dayData.getBreakfast(), portionsOfIngredients);
            getIngredientsPorionsFromRecipe(dayData.getLunch(), portionsOfIngredients);
            getIngredientsPorionsFromRecipe(dayData.getDinner(), portionsOfIngredients);
        }
        return portionsOfIngredients;
    }

    private static void getIngredientsPorionsFromRecipe(Recipe recipe, Map<String, Map<String, Float>> portionsOfIngredients) {
        if (recipe != null){
            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                String ingredName = recipe.getIngredientInPos(i).getName();
                String portionName = recipe.getIngredientPortionNameInPos(i);
                Float portionQuantity = recipe.getIngredientQuantityInPos(i);
                if (!portionsOfIngredients.containsKey(ingredName)){
                    portionsOfIngredients.put(ingredName, new HashMap<>());
                    portionsOfIngredients.get(ingredName).put(portionName, portionQuantity);
                } else {
                    if (portionsOfIngredients.get(ingredName).containsKey(portionName)){
                        portionsOfIngredients.get(ingredName).put(portionName,
                                portionsOfIngredients.get(ingredName).get(portionName) + portionQuantity);
                    } else {
                        portionsOfIngredients.get(ingredName).put(portionName, portionQuantity);
                    }
                }
            }
        }
    }

    public long getFromDateLong() {
        return fromDate.getValue().toEpochDay();
    }

    public long getToDateLong() {
        return toDate.getValue().toEpochDay();
    }

    public abstract void onDatesChanged();

}
