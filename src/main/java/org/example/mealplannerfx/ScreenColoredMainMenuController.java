package org.example.mealplannerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String nickname = graphicCC.getThisUser().getNickname();
        nicknameText.setText(nickname);
        avatarButton.setText(String.valueOf(nickname.toUpperCase().charAt(0)));
        toShowCalendar = Calendar.getInstance();
        setCalendarButtons();
    }
    private void setCalendarButtons() {
        monthText.setText(toShowCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.forLanguageTag("en")));
        Integer todayDay = null;
        if (toShowCalendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)){
            todayDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }
        int lenOfMonth = toShowCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        toShowCalendar.set(Calendar.DAY_OF_MONTH, 1);
        long epochFirstDayMonth = toShowCalendar.getTime().toInstant().getEpochSecond() / 86400;
        int actualDay = switch (toShowCalendar.get(Calendar.DAY_OF_WEEK)) {
            case 1 -> -5;
            case 2 -> 1;
            case 3 -> 0;
            case 4 -> -1;
            case 5 -> -2;
            case 6 -> -3;
            case 7 -> -4;
            default -> 1;
        };
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

        DayData dayData = graphicCC.getThisUser().searchForDayData(epochFirstDayMonth);
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
        int posX = Integer.getInteger(valores[1]);
        int posY = Integer.getInteger(valores[2]);


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
        graphicCC.startScreenColored("newRecipe");
    }
}
