package org.example.mealplannerfx.coloredScreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public abstract class ScreenColoredDefWithList implements Initializable {
    @FXML
    private Label idNumberText;
    private ScreenColoredDefWithElements controllerSup;
    private int thisPosition;
    private VBox thisVBox;

    public void setControllerSup(ScreenColoredDefWithElements controllerSup) {
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

    public ScreenColoredDefWithElements getControllerSup() {
        return controllerSup;
    }

    public int getThisPosition() {
        return thisPosition;
    }

    public void setThisVBox(VBox thisVBox) {
        this.thisVBox = thisVBox;
    }

    public VBox getThisVBox() {
        return thisVBox;
    }
}
