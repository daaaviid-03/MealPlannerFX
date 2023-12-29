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
        super.getControllerSup().upArrowStepClicked(super.getThisPosition());
    }

    public void downArrowStepClicked(ActionEvent actionEvent) {
        super.getControllerSup().downArrowStepClicked(super.getThisPosition());
    }

    public void deleteStep(ActionEvent actionEvent) {
        super.getControllerSup().deleteStep(super.getThisPosition());
    }

    public void addStep(ActionEvent actionEvent) {
        super.getControllerSup().addStep(super.getThisPosition() + 1);
    }

    public String getStepString(){
        return stepText.getText();
    }

}
