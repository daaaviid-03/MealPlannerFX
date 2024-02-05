package org.example.mealplannerfx.bwscreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.control.WrongArgException;
import org.example.mealplannerfx.entity.DayData;
import org.example.mealplannerfx.entity.Ingredient;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ScreenBWStatsController extends ScreenBWDefWithStats implements Initializable {
    @FXML
    private Label errorText;
    @FXML
    private TextField numOfMeals;
    @FXML
    private TextField calTot;
    @FXML
    private TextField carbTot;
    @FXML
    private TextField proteinTot;
    @FXML
    private TextField fatTot;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModelBW("stats", false);
    }
    @Override
    public void onDatesChangedBW() {
        try {
            Map<String, Map<String, Float>> portionsOfIngredients = getAllPortionsOfIngredientDates();
            numOfMeals.setText(String.valueOf(getNumberOfMealsInDates()));
            float fatTotVal = 0;
            float calTotVal = 0;
            float carbTotVal = 0;
            float proteinTotVal = 0;

            for (Map.Entry<String, Map<String, Float>> ingredientName : portionsOfIngredients.entrySet()){
                Ingredient ingredient = DBController.getIngredientByName(ingredientName.getKey());
                for (String portionName : portionsOfIngredients.get(ingredientName.getKey()).keySet()){
                    float hundredGramsOfIngredient = ingredient.getFoodPortionInGrams(portionName) *
                            portionsOfIngredients.get(ingredientName.getKey()).get(portionName) / 100;
                    fatTotVal += hundredGramsOfIngredient * ingredient.getFats();
                    calTotVal += hundredGramsOfIngredient * ingredient.getCalories();
                    carbTotVal += hundredGramsOfIngredient * ingredient.getCarbohydrates();
                    proteinTotVal += hundredGramsOfIngredient * ingredient.getProteins();
                }
            }
            fatTot.setText(String.valueOf(fatTotVal));
            calTot.setText(String.valueOf(calTotVal));
            carbTot.setText(String.valueOf(carbTotVal));
            proteinTot.setText(String.valueOf(proteinTotVal));
            errorText.setText("");
        } catch (WrongArgException e){
            errorText.setText(e.getWrongArgumentDescription());
        } catch (Exception e){
            errorText.setText(e.getMessage());
        }
    }
    private int getNumberOfMealsInDates() {
        int count = 0;
        for (DayData dayData : DBController.getDaysData(GraphicControllerBW.getThisUser().getNickname(), getFromDateLong(), getToDateLong())){
            if (dayData.getDinnerId() != null){
                count++;
            }
            if (dayData.getBreakfastId() != null){
                count++;
            }
            if (dayData.getLunchId() != null){
                count++;
            }
        }
        return count;
    }
}
