package org.example.mealplannerfx.coloredScreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.control.WrongArgException;
import org.example.mealplannerfx.entity.DayData;
import org.example.mealplannerfx.entity.Ingredient;

import java.net.URL;
import java.util.*;

public class ScreenColoredStatsController extends ScreenColoredDefWithStats implements Initializable {
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
            numOfMeals.setText(String.valueOf(getNumberOfMealsInDates()));
            float calTotVal = 0, carbTotVal = 0, proteinTotVal = 0, fatTotVal = 0;
            for (String ingredientName : portionsOfIngredients.keySet()){
                Ingredient ingredient = DBController.getIngredientByName(ingredientName);
                for (String portionName : portionsOfIngredients.get(ingredientName).keySet()){
                    float hundredGramsOfIngredient = ingredient.getFoodPortionInGrams(portionName) *
                            portionsOfIngredients.get(ingredientName).get(portionName) / 100;
                    calTotVal += hundredGramsOfIngredient * ingredient.getCalories();
                    carbTotVal += hundredGramsOfIngredient * ingredient.getCarbohydrates();
                    proteinTotVal += hundredGramsOfIngredient * ingredient.getProteins();
                    fatTotVal += hundredGramsOfIngredient * ingredient.getFats();
                }
            }
            calTot.setText(String.valueOf(calTotVal));
            carbTot.setText(String.valueOf(carbTotVal));
            proteinTot.setText(String.valueOf(proteinTotVal));
            fatTot.setText(String.valueOf(fatTotVal));
            errorText.setText("");
        } catch (WrongArgException e){
            errorText.setText(e.getWrongArgumentDescription());
        } catch (Exception e){
            errorText.setText(e.getMessage());
        }
    }
    private int getNumberOfMealsInDates() {
        int count = 0;
        for (DayData dayData : DBController.getDaysData(getGraphicCC().getThisUser().getNickname(), getFromDateLong(), getToDateLong())){
            if (dayData.getBreakfastId() != null){
                count++;
            }
            if (dayData.getLunchId() != null){
                count++;
            }
            if (dayData.getDinnerId() != null){
                count++;
            }
        }
        return count;
    }
}
