package org.example.mealplannerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ScreenColoredZZNewStepMaskController extends ScreenColoredElementInListMaskController {
    @FXML
    private TextField stepText;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void upArrowStepClicked(ActionEvent actionEvent) {
        getControllerSup().upArrowStepClicked(getThisPosition(), getThisVBox());
    }

    public void downArrowStepClicked(ActionEvent actionEvent) {
        getControllerSup().downArrowStepClicked(getThisPosition(), getThisVBox());
    }

    public void deleteStep(ActionEvent actionEvent) {
        getControllerSup().deleteStep(getThisPosition(), getThisVBox());
    }

    public void addStep(ActionEvent actionEvent) {
        getControllerSup().addStep(getThisPosition() + 1, getThisVBox());
    }

    public String getStepString(){
        return stepText.getText();
    }

}
