package org.example.mealplannerfx.bwscreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.control.WrongArgException;
import org.example.mealplannerfx.entity.DayData;
import org.example.mealplannerfx.entity.Recipe;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class ScreenBWDefWithStats extends ScreenBWDef implements Initializable {
    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;

    @Override
    public void initializeDefaultModel(String nameThisScreen, boolean confirmExit){
        super.initializeDefaultModel(nameThisScreen, confirmExit);
        fromDate.setValue(LocalDate.now());
        toDate.setValue(LocalDate.now().plusDays(7));
        onDatesChanged();
        fromDate.valueProperty().addListener((observable, oldValue, newValue) -> onDatesChanged());
        toDate.valueProperty().addListener((observable, oldValue, newValue) -> onDatesChanged());
    }

    public Map<String, Map<String, Float>> getAllPortionsOfIngredientDates() throws Exception {
        long fromDateLong = fromDate.getValue().toEpochDay();
        long toDateLong = toDate.getValue().toEpochDay();
        if (fromDateLong > toDateLong){
            throw new WrongArgException("The first date can't be after the second date.");
        }
        // It's the map of all ingredients in between that days that ar used in recipes, and a map of portions
        // of each ingredient.
        Map<String, Map<String, Float>> portionsOfIngredients = new HashMap<>();
        for (DayData dayData : DBController.getDaysData(GraphicControllerBW.getThisUser().getNickname(), fromDateLong, toDateLong)){
            getIngredientsPortionsFromRecipe(DBController.getRecipe(dayData.getBreakfastId()), portionsOfIngredients);
            getIngredientsPortionsFromRecipe(DBController.getRecipe(dayData.getLunchId()), portionsOfIngredients);
            getIngredientsPortionsFromRecipe(DBController.getRecipe(dayData.getDinnerId()), portionsOfIngredients);
        }
        return portionsOfIngredients;
    }

    private static void getIngredientsPortionsFromRecipe(Recipe recipe, Map<String, Map<String, Float>> portionsOfIngredients) {
        if (recipe != null){
            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                String ingredientName = recipe.getIngredientInPos(i).getName();
                String portionName = recipe.getIngredientPortionNameInPos(i);
                Float portionQuantity = recipe.getIngredientQuantityInPos(i);
                if (!portionsOfIngredients.containsKey(ingredientName)){
                    portionsOfIngredients.put(ingredientName, new HashMap<>());
                    portionsOfIngredients.get(ingredientName).put(portionName, portionQuantity);
                } else {
                    if (portionsOfIngredients.get(ingredientName).containsKey(portionName)){
                        portionsOfIngredients.get(ingredientName).put(portionName,
                                portionsOfIngredients.get(ingredientName).get(portionName) + portionQuantity);
                    } else {
                        portionsOfIngredients.get(ingredientName).put(portionName, portionQuantity);
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
