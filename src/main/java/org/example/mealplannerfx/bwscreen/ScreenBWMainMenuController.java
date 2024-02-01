package org.example.mealplannerfx.bwscreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.entity.DayData;

import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class ScreenBWMainMenuController extends ScreenBWDef implements Initializable {
    private static final String THIS_SCREEN_NAME = "mainMenu";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModel(THIS_SCREEN_NAME, true);
    }

    public void makeShoppingListButtonClicked() {
        getGbwcInstance().startScreenBW("shoppingList", THIS_SCREEN_NAME);
    }

    public void viewStatisticsButtonClicked() {
        getGbwcInstance().startScreenBW("stats", THIS_SCREEN_NAME);
    }

    public void addNewRecipeButtonClicked() {
        getGbwcInstance().startScreenBW("newRecipe", THIS_SCREEN_NAME);
    }

    public void viewOneDayButtonClicked() {
        getGbwcInstance().startScreenBW("daySelect", THIS_SCREEN_NAME);
    }
}
