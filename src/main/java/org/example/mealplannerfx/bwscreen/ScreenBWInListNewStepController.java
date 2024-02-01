package org.example.mealplannerfx.bwscreen;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ScreenBWInListNewStepController extends ScreenBWDefWithList {
    @FXML
    private TextField stepText;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // No need to initialize nothing
    }

    public void upArrowStepClicked() {
        getControllerSup().upArrowStepClicked(getThisPosition(), getThisVBox());
    }

    public void downArrowStepClicked() {
        getControllerSup().downArrowStepClicked(getThisPosition(), getThisVBox());
    }

    public void deleteStep() {
        getControllerSup().deleteStep(getThisPosition(), getThisVBox());
    }

    public void addStep() {
        getControllerSup().addStep(getThisPosition() + 1, getThisVBox());
    }

    public String getStepString(){
        return stepText.getText();
    }

}
