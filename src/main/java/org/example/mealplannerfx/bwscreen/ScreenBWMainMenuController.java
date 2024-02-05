package org.example.mealplannerfx.bwscreen;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ScreenBWMainMenuController extends ScreenBWDef implements Initializable {
    private static final String THIS_SCREEN_NAME = "mainMenu";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModelBW(THIS_SCREEN_NAME, true);
    }

    public void makeShoppingListButtonClicked() {
        GraphicControllerBW.startScreenBW("shoppingList", THIS_SCREEN_NAME);
    }

    public void viewStatisticsButtonClicked() {
        GraphicControllerBW.startScreenBW("stats", THIS_SCREEN_NAME);
    }

    public void addNewRecipeButtonClicked() {
        GraphicControllerBW.startScreenBW("newRecipe", THIS_SCREEN_NAME);
    }

    public void viewOneDayButtonClicked() {
        GraphicControllerBW.startScreenBW("daySelect", THIS_SCREEN_NAME);
    }
}
