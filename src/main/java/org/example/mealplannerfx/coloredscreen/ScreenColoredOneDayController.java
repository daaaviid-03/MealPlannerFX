package org.example.mealplannerfx.coloredscreen;

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

public class ScreenColoredOneDayController extends ScreenColoredDef implements Initializable {

    @FXML
    private AnchorPane recipeViewerPlane;
    @FXML
    private Button lunch_button;
    @FXML
    private Button breakfast_button;
    @FXML
    private Button dinner_button;
    @FXML
    private Label dayText;
    private long dayNumber;
    private DayData thisDayData;

    private static final String NO_RECIPE_STYLE = "-fx-background-color: #dbdcdb; -fx-background-radius: 40px;" +
            " -fx-border-radius: 40px; -fx-border-color: #a9a9a9; -fx-border-width: 6px; -fx-border-style: dashed;" +
            " -fx-text-fill: #a9a9a9; -fx-font-size: 65;";
    private static final String YES_RECIPE_STYLE = "-fx-background-color: #98c0f6; -fx-background-radius: 40px;" +
            " -fx-border-radius: 40px; -fx-border-color: #74a7e4; -fx-border-width: 6px; -fx-border-style: solid;" +
            " -fx-text-fill: #336ca5; -fx-font-size: 30;";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModel(false);
        dayNumber = GraphicControllerColored.getDayToExplore();
        setRecipeSelected();
    }

    private void setRecipeSelected(){
        Recipe recipe = GraphicControllerColored.getLastRecipeSelected();
        setDayDataFromDayNumber();
        if (recipe != null){
            thisDayData.setMealByName(GraphicControllerColored.getMealNameOfLastSelected(), recipe);
            GraphicControllerColored.setLastRecipeSelected(null);
            GraphicControllerColored.setMealNameOfLastSelected(null);
            DBController.saveDayData(thisDayData);
        }
        setDayDataFromCalendar();
    }


    private void setDayDataFromCalendar() {
        // Set date info
        GraphicControllerColored.setDayToExplore(dayNumber);
        LocalDate date = LocalDate.ofEpochDay(dayNumber);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'of' MMMM").withLocale(Locale.ENGLISH);
        dayText.setText(date.format(formatter));
        // Set day data info
        setButtonToInfo(breakfast_button, DBController.getRecipe(thisDayData.getBreakfastId()));
        setButtonToInfo(lunch_button, DBController.getRecipe(thisDayData.getLunchId()));
        setButtonToInfo(dinner_button, DBController.getRecipe(thisDayData.getDinnerId()));
    }

    private void setDayDataFromDayNumber() {
        thisDayData = DBController.getSpecificDayData(GraphicControllerColored.getThisUser().getNickname(), dayNumber);
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
    public void previousDayButtonClicked() {
        dayNumber--;
        setDayDataFromDayNumber();
        setDayDataFromCalendar();
    }

    public void nextDayButtonClicked() {
        dayNumber++;
        setDayDataFromDayNumber();
        setDayDataFromCalendar();
    }

    public void recipeEditButton(ActionEvent actionEvent) {
        GraphicControllerColored.setMealNameOfLastSelected(getNameOfMealFromActionEvent(actionEvent));
        GraphicControllerColored.startScreenColored("searchNewFood", "oneDay");
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
}
