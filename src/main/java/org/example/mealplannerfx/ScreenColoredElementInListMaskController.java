package org.example.mealplannerfx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class ScreenColoredElementInListMaskController implements Initializable {
    @FXML
    private Label idNumberText;
    private ScreenColoredNewRecipeController controllerSup;
    private int thisPosition;

    public void setControllerSup(ScreenColoredNewRecipeController controllerSup) {
        this.controllerSup = controllerSup;
    }
    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
        updateNumberText();
    }
    public void setThisPositionPlusOne() {
        this.thisPosition++;
        updateNumberText();
    }
    public void setThisPositionMinusOne() {
        this.thisPosition--;
        updateNumberText();
    }
    private void updateNumberText(){
        idNumberText.setText((this.thisPosition + 1) + ".");
    }

    public ScreenColoredNewRecipeController getControllerSup() {
        return controllerSup;
    }

    public int getThisPosition() {
        return thisPosition;
    }


}
