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
    private Calendar toShowCalendar;
    @FXML
    private Label monthText;
    private long epochFirstDayMonth;
    private int initialDayOfWeek;

    private static final String THIS_SCREEN_NAME = "mainMenu";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModel(true);
        toShowCalendar = Calendar.getInstance();
        setCalendarButtons();
    }

    private int getActualDayOfWeek(int dayOfWeekToConvert){
        return switch (dayOfWeekToConvert) {
            case 1 -> -5;
            case 3 -> 0;
            case 4 -> -1;
            case 5 -> -2;
            case 6 -> -3;
            case 7 -> -4;
            default -> 1;
        };
    }

    private void setCalendarButtons() {
        monthText.setText(toShowCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.forLanguageTag("en")));
        Integer todayDay = null;
        if (toShowCalendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)){
            todayDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }
        int lenOfMonth = toShowCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        toShowCalendar.set(Calendar.DAY_OF_MONTH, 1);
        epochFirstDayMonth = toShowCalendar.getTime().toInstant().getEpochSecond() / 86400;
        int actualDay = getActualDayOfWeek(toShowCalendar.get(Calendar.DAY_OF_WEEK));
        initialDayOfWeek = actualDay;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                Button thisButton = (Button)getGraphicCC().searchForObjInScene("DayButton_" + i + "_" + j);
                thisButton.setVisible(!(actualDay <= 0 || actualDay > lenOfMonth));
                if (actualDay <= 0 || actualDay > lenOfMonth){
                    Label thisLabel = (Label)getGraphicCC().searchForObjInScene("dayToShowText_" + i + "_" + j);
                    thisLabel.setText(String.valueOf(actualDay));
                    setDayDataOfCalendar(i, j, actualDay, epochFirstDayMonth);
                    setStyle(todayDay, actualDay, thisButton, j);
                }
                actualDay++;
            }
        }
    }

    private static void setStyle(Integer todayDay, int actualDay, Button thisButton, int j) {
        if (todayDay != null && todayDay.equals(actualDay)){
            thisButton.setStyle("-fx-background-color: #7f84b5;");
        } else if(j < 5){
            thisButton.setStyle("-fx-background-color: #d4e6e6;");
        } else {
            thisButton.setStyle("-fx-background-color: #8ac0c0;");
        }
    }

    private void setDayDataOfCalendar(int i, int j, int actualDay, long epochFirstDayMonth){
        DayData dayData = DBController.getSpecificDayData(getGraphicCC().getThisUser().getNickname(),
                epochFirstDayMonth + actualDay - 1);
        Button breakfast = (Button)getGraphicCC().searchForObjInScene("DayButton_Breakfast_" + i + "_" + j);
        Button launch = (Button)getGraphicCC().searchForObjInScene("DayButton_Launch_" + i + "_" + j);
        Button dinner = (Button)getGraphicCC().searchForObjInScene("DayButton_Dinner_" + i + "_" + j);
        if (dayData == null || dayData.getBreakfastId() == null) {
            breakfast.setVisible(false);
        } else {
            breakfast.setVisible(true);
            breakfast.setText(DBController.getRecipe(dayData.getBreakfastId()).getName());
        }
        if (dayData == null || dayData.getLunchId() == null) {
            launch.setVisible(false);
        } else {
            launch.setVisible(true);
            launch.setText(DBController.getRecipe(dayData.getLunchId()).getName());
        }
        if (dayData == null || dayData.getDinnerId() == null) {
            dinner.setVisible(false);
        } else {
            dinner.setVisible(true);
            dinner.setText(DBController.getRecipe(dayData.getDinnerId()).getName());
        }
    }

    public void previousMonthButtonClicked() {
        toShowCalendar.add(Calendar.MONTH, -1);
        setCalendarButtons();
    }

    public void nextMonthButtonClicked() {
        toShowCalendar.add(Calendar.MONTH, 1);
        setCalendarButtons();
    }

    public void calendarDayButtonClicked(ActionEvent actionEvent) {
        String[] values = ((Button)actionEvent.getSource()).getId().split("_");
        int posY = Integer.parseInt(values[1]);
        int posX = Integer.parseInt(values[2]);
        long dayNumber = epochFirstDayMonth + posX + posY * 7L + initialDayOfWeek - 1;
        getGraphicCC().setDayToExplore(dayNumber);
        getGraphicCC().startScreenColored("oneDay", THIS_SCREEN_NAME);
    }

    public void makeShoppingListButtonClicked() {
        getGraphicCC().startScreenColored("shoppingList", THIS_SCREEN_NAME);
    }

    public void viewStatisticsButtonClicked() {
        getGraphicCC().startScreenColored("stats", THIS_SCREEN_NAME);
    }

    public void addNewRecipeButtonClicked() {
        getGraphicCC().startScreenColored("newRecipe", THIS_SCREEN_NAME);
    }
}
