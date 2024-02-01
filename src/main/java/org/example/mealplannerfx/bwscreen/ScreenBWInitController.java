package org.example.mealplannerfx.bwscreen;

import javafx.event.ActionEvent;

public class ScreenBWInitController {

    private final GraphicBWColored graphicCBW = GraphicBWColored.getGBWCInstance();
    public void logInButton(ActionEvent actionEvent) {
        graphicCBW.startScreenBW("login");
    }

    public void registerButton(ActionEvent actionEvent) {
        graphicCBW.startScreenBW("register");
    }
}
