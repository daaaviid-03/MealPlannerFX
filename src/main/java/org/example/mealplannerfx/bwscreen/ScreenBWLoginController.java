package org.example.mealplannerfx.bwscreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.control.WrongArgException;
import org.example.mealplannerfx.entity.User;

public class ScreenBWLoginController {
    @FXML
    private Label errorText;
    @FXML
    private TextField nicknameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private void onLoginButtonClicked(){
        String nick = nicknameText.getText();
        String pass = passwordText.getText();
        try {
            DBController.checkUserInDB(nick, pass);
            User thisUser = DBController.getUserInfo(nick);
            GraphicControllerBW.setThisUser(thisUser);
            GraphicControllerBW.startScreenBW("mainMenu", "init");
        } catch (WrongArgException wrongArgument) {
            errorText.setText(wrongArgument.getWrongArgumentDescription());
        } catch (Exception e){
            errorText.setText(e.getMessage());
        }
    }

    public void returnButtonClicked() {
        GraphicControllerBW.startScreenBW("init");
    }
}