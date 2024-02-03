package org.example.mealplannerfx.bwscreen;

import javafx.event.ActionEvent;

public class ScreenBWInitController {

    public void logInButton(ActionEvent actionEvent) {
        GraphicControllerBW.startScreenBW("login");
    }

    public void registerButton(ActionEvent actionEvent) {
        GraphicControllerBW.startScreenBW("register");
    }
}
