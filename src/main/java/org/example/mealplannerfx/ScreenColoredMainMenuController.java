package org.example.mealplannerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class ScreenColoredMainMenuController implements Initializable {
    private GraphicControllerColored graphicCC = GraphicControllerColored.getGCCInstance();
    private DBController dBController = DBController.getDBControllerInstance();
    private Calendar toShowCalendar;
    @FXML
    private Label monthText;
    @FXML
    private Label nicknameText;
    @FXML
    private Button avatarButton;
    private long epochFirstDayMonth;
    private int initialDayOfWeek;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String nickname = graphicCC.getThisUser().getNickname();
        nicknameText.setText(nickname);
        avatarButton.setText(String.valueOf(nickname.toUpperCase().charAt(0)));
        toShowCalendar = Calendar.getInstance();
        setCalendarButtons();
    }

    private int getActualDayOfWeek(int dayOfWeekToConvert){
        return switch (dayOfWeekToConvert) {
            case 1 -> -5;
            case 2 -> 1;
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
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                Button thisButton = (Button)graphicCC.searchForObjInScene("DayButton_" + i + "_" + j);
                if(actualDay <= 0 || actualDay > lenOfMonth){
                    thisButton.setVisible(false);
                }else{
                    thisButton.setVisible(true);
                    Label thisLabel = (Label)graphicCC.searchForObjInScene("dayToShowText_" + i + "_" + j);
                    thisLabel.setText(String.valueOf(actualDay));
                    setDayDataOfCalendar(i, j, actualDay, epochFirstDayMonth);
                    if (todayDay != null && todayDay.equals(actualDay)){
                        thisButton.setStyle("-fx-background-color: #7f84b5;");
                    } else if(j < 5){
                        thisButton.setStyle("-fx-background-color: #d4e6e6;");
                    } else {
                        thisButton.setStyle("-fx-background-color: #8ac0c0;");
                    }
                }
                actualDay++;
            }
        }
    }

    private void setDayDataOfCalendar(int i, int j, int actualDay, long epochFirstDayMonth){

        DayData dayData = graphicCC.getThisUser().searchForDayData(epochFirstDayMonth + actualDay - 1);
        Button breakfast = (Button)graphicCC.searchForObjInScene("DayButton_Breakfast_" + i + "_" + j);
        Button launch = (Button)graphicCC.searchForObjInScene("DayButton_Launch_" + i + "_" + j);
        Button dinner = (Button)graphicCC.searchForObjInScene("DayButton_Dinner_" + i + "_" + j);
        if (dayData == null || dayData.getBreakfast() == null) {
            breakfast.setVisible(false);
        } else {
            breakfast.setText(dayData.getBreakfast().getName());
        }
        if (dayData == null || dayData.getLunch() == null) {
            launch.setVisible(false);
        } else {
            launch.setText(dayData.getLunch().getName());
        }
        if (dayData == null || dayData.getDinner() == null) {
            dinner.setVisible(false);
        } else {
            dinner.setText(dayData.getDinner().getName());
        }
    }

    public void previousMonthButtonClicked(ActionEvent actionEvent) {
        toShowCalendar.add(Calendar.MONTH, -1);
        setCalendarButtons();
    }

    public void nextMonthButtonClicked(ActionEvent actionEvent) {
        toShowCalendar.add(Calendar.MONTH, 1);
        setCalendarButtons();
    }


    public void calendarDayButtonClicked(ActionEvent actionEvent) {
        String[] valores = ((Button)actionEvent.getSource()).getId().split("_");
        int posY = Integer.parseInt(valores[1]);
        int posX = Integer.parseInt(valores[2]);
        long dayNumber = epochFirstDayMonth + posX + posY * 7L + initialDayOfWeek - 1;
        graphicCC.setDayToExplore(dayNumber);
        graphicCC.startScreenColored("oneDay", "mainMenu");
    }

    public void userInfoButtonClicked(ActionEvent actionEvent) {
    }

    public void searchForNewFoodButtonClicked(ActionEvent actionEvent) {
    }

    public void makeShoppingListButtonClicked(ActionEvent actionEvent) {
    }

    public void viewStatisticsButtonClicked(ActionEvent actionEvent) {
    }

    public void addNewRecipeButtonClicked(ActionEvent actionEvent) {
        graphicCC.startScreenColored("newRecipe", "mainMenu");
    }
}
