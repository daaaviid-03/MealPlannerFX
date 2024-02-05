package org.example.mealplannerfx.bwscreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.util.ResourceBundle;

public class ScreenBWDaySelectController extends ScreenBWDef implements Initializable {
    @FXML
    private DatePicker dayToExplore;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModelBW("daySelect", false);
    }

    public void exploreThatDayButton() {
        if (dayToExplore.getValue() != null) {
            GraphicControllerBW.setDayToExplore(dayToExplore.getValue().toEpochDay());
            GraphicControllerBW.startScreenBW("oneDay", "daySelect");
        }
    }
}
