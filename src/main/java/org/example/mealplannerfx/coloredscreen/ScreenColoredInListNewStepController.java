package org.example.mealplannerfx.coloredscreen;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ScreenColoredInListNewStepController extends ScreenColoredDefWithList {
    @FXML
    private TextField stepText;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
