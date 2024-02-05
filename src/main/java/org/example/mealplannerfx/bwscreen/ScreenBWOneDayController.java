package org.example.mealplannerfx.bwscreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.entity.DayData;
import org.example.mealplannerfx.entity.Recipe;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class ScreenBWOneDayController extends ScreenBWDef implements Initializable {
    @FXML
    private Label dayText;
    @FXML
    private AnchorPane recipeViewerPlane;
    @FXML
    private Button lunch;
    @FXML
    private Button breakfast;
    @FXML
    private Button dinner;
    private long dayNumber;
    private DayData thisDayData;

    private static final String NO_RECIPE_STYLE = "-fx-background-color: #ffffff; -fx-background-radius: 40px;" +
            " -fx-border-radius: 40px; -fx-border-color: #000000; -fx-border-width: 8px; -fx-border-style: dashed;" +
            " -fx-font-size: 65; -fx-font-weight: bold; -fx-text-fill: #000000; -fx-underline: true;";
    private static final String YES_RECIPE_STYLE = "-fx-background-radius: 40px; -fx-border-radius: 40px;" +
            " -fx-border-color: #000000; -fx-border-width: 8px; -fx-text-fill: #000000; -fx-font-size: 45;" +
            " -fx-font-weight: bold; -fx-underline: true;";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModelBW("oneDay", false);
        dayNumber = GraphicControllerBW.getDayToExplore();
        setRecipeSelected();
    }

    private void setDayDataFromCalendar() {
        // Set date info
        GraphicControllerBW.setDayToExplore(dayNumber);
        LocalDate date = LocalDate.ofEpochDay(dayNumber);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'of' MMMM").withLocale(Locale.ENGLISH);
        dayText.setText(date.format(formatter));
        // Set day data info
        setButtonToInfo(breakfast, DBController.getRecipe(thisDayData.getBreakfastId()));
        setButtonToInfo(lunch, DBController.getRecipe(thisDayData.getLunchId()));
        setButtonToInfo(dinner, DBController.getRecipe(thisDayData.getDinnerId()));
    }

    private void setDayDataFromDayNumber() {
        thisDayData = DBController.getSpecificDayData(GraphicControllerBW.getThisUser().getNickname(), dayNumber);
    }

    private void setButtonToInfo(Button button, Recipe recipe){
        if (recipe != null){
            button.setStyle(YES_RECIPE_STYLE);
            button.setText(recipe.getName());
        } else {
            button.setStyle(NO_RECIPE_STYLE);
            button.setText("+  Recipe");
        }
    }

    public void recipeEditButton(ActionEvent actionEvent) {
        GraphicControllerBW.setMealNameOfLastSelected(getNameOfMealFromActionEvent(actionEvent));
        GraphicControllerBW.startScreenBW("searchNewFood", "oneDay");
    }

    public void deleteRecipe(ActionEvent actionEvent) {
        thisDayData.setMealByName(getNameOfMealFromActionEvent(actionEvent), null);
        setDayDataFromCalendar();
    }

    private String getNameOfMealFromActionEvent(ActionEvent actionEvent){
        return ((Button)actionEvent.getSource()).getId().split("_")[0];
    }

    public void recipeViewButton(ActionEvent actionEvent) {
        Recipe thisRecipe = DBController.getRecipe(thisDayData.getMealByName(getNameOfMealFromActionEvent(actionEvent)));
        if (thisRecipe == null){
            recipeEditButton(actionEvent);
            return;
        }
        visualizeRecipeInPlane(recipeViewerPlane, thisRecipe);
    }
    private void setRecipeSelected(){
        Recipe recipe = GraphicControllerBW.getLastRecipeSelected();
        setDayDataFromDayNumber();
        if (recipe != null){
            thisDayData.setMealByName(GraphicControllerBW.getMealNameOfLastSelected(), recipe);
            GraphicControllerBW.setLastRecipeSelected(null);
            GraphicControllerBW.setMealNameOfLastSelected(null);
            DBController.saveDayData(thisDayData);
        }
        setDayDataFromCalendar();
    }
}
