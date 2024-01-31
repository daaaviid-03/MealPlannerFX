package org.example.mealplannerfx.coloredScreen;

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

    private final static String NO_RECIPE_STYLE = "-fx-background-color: #dbdcdb; -fx-background-radius: 40px;" +
            " -fx-border-radius: 40px; -fx-border-color: #a9a9a9; -fx-border-width: 6px; -fx-border-style: dashed;" +
            " -fx-text-fill: #a9a9a9; -fx-font-size: 65;";
    private final static String YES_RECIPE_STYLE = "-fx-background-color: #98c0f6; -fx-background-radius: 40px;" +
            " -fx-border-radius: 40px; -fx-border-color: #74a7e4; -fx-border-width: 6px; -fx-border-style: solid;" +
            " -fx-text-fill: #336ca5; -fx-font-size: 30;";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModel(false);
        dayNumber = getGraphicCC().getDayToExplore();
        setRecipeSelected();
    }

    private void setRecipeSelected(){
        Recipe recipe = getGraphicCC().getLastRecipeSelected();
        setDayDataFromDayNumber();
        if (recipe != null){
            thisDayData.setMealByName(getGraphicCC().getMealNameOfLastSelected(), recipe);
            getGraphicCC().setLastRecipeSelected(null);
            getGraphicCC().setMealNameOfLastSelected(null);
            DBController.saveDayData(thisDayData);
        }
        setDayDataFromCalendar();
    }


    private void setDayDataFromCalendar() {
        // Set date info
        getGraphicCC().setDayToExplore(dayNumber);
        LocalDate date = LocalDate.ofEpochDay(dayNumber);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'of' MMMM").withLocale(Locale.ENGLISH);
        dayText.setText(date.format(formatter));
        // Set day data info
        setButtonToInfo(breakfast_button, DBController.getRecipe(thisDayData.getBreakfastId()));
        setButtonToInfo(lunch_button, DBController.getRecipe(thisDayData.getLunchId()));
        setButtonToInfo(dinner_button, DBController.getRecipe(thisDayData.getDinnerId()));
    }

    private void setDayDataFromDayNumber() {
        thisDayData = DBController.getSpecificDayData(getGraphicCC().getThisUser().getNickname(), dayNumber);
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
    public void previousDayButtonClicked(ActionEvent actionEvent) {
        dayNumber--;
        setDayDataFromDayNumber();
        setDayDataFromCalendar();
    }

    public void nextDayButtonClicked(ActionEvent actionEvent) {
        dayNumber++;
        setDayDataFromDayNumber();
        setDayDataFromCalendar();
    }

    public void recipeEditButton(ActionEvent actionEvent) {
        getGraphicCC().setMealNameOfLastSelected(getNameOfMealFromActionEvent(actionEvent));
        getGraphicCC().startScreenColored("searchNewFood", "oneDay");
    }

    public void deleteRecipe(ActionEvent actionEvent) {
        thisDayData.setMealByName(getNameOfMealFromActionEvent(actionEvent), null);
        setDayDataFromCalendar();
    }

    public long getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(long dayNumber) {
        this.dayNumber = dayNumber;
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
        visualizeRecipeInPlane(recipeViewerPlane);
    }
}
