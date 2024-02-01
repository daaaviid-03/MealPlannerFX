package org.example.mealplannerfx.bwscreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.entity.DayData;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class ScreenBWDaySelectController extends ScreenBWDef implements Initializable {
    @FXML
    private DatePicker dayToExplore;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModel("daySelect", false);
    }

    public void exploreThatDayButton(ActionEvent actionEvent) {
        if (dayToExplore.getValue() != null) {
            getGbwcInstance().setDayToExplore(dayToExplore.getValue().toEpochDay());
            getGbwcInstance().startScreenBW("oneDay", "daySelect");
        }
    }
}
