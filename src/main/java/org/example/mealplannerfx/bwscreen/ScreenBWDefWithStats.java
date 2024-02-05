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
    public void initializeDefaultModelBW(String nameThisScreen, boolean confirmExit){
        super.initializeDefaultModelBW(nameThisScreen, confirmExit);
        toDate.valueProperty().addListener((observable, oldValue, newValue) -> onDatesChangedBW());
        fromDate.setValue(LocalDate.now());
        toDate.setValue(LocalDate.now().plusDays(7));
        onDatesChangedBW();
        fromDate.valueProperty().addListener((observable, oldValue, newValue) -> onDatesChangedBW());
    }

    public Map<String, Map<String, Float>> getAllPortionsOfIngredientDates() throws Exception {
        long toDateLong = toDate.getValue().toEpochDay();
        long fromDateLong = fromDate.getValue().toEpochDay();
        if (fromDateLong > toDateLong){
            throw new WrongArgException("The first date can't be after the second date.");
        }
        // It's the map of all ingredients in between that days that ar used in recipes, and a map of portions
        // of each ingredient.
        Map<String, Map<String, Float>> portionsOfIngredients = new HashMap<>();
        for (DayData dayData : DBController.getDaysData(GraphicControllerBW.getThisUser().getNickname(), fromDateLong, toDateLong)){
            getIngredientsPortionsFromRecipes(DBController.getRecipe(dayData.getDinnerId()), portionsOfIngredients);
            getIngredientsPortionsFromRecipes(DBController.getRecipe(dayData.getBreakfastId()), portionsOfIngredients);
            getIngredientsPortionsFromRecipes(DBController.getRecipe(dayData.getLunchId()), portionsOfIngredients);
        }
        return portionsOfIngredients;
    }

    private static void getIngredientsPortionsFromRecipes(Recipe recipe, Map<String, Map<String,
            Float>> portionsOfIngredients) {
        if (recipe != null){
            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                String ingredientName = recipe.getIngredientInPos(i).getName();
                Float portionQuantity = recipe.getIngredientQuantityInPos(i);
                String portionName = recipe.getIngredientPortionNameInPos(i);
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
    public long getToDateLong() {
        return toDate.getValue().toEpochDay();
    }

    public long getFromDateLong() {
        return fromDate.getValue().toEpochDay();
    }
    public abstract void onDatesChangedBW();

}
