package org.example.mealplannerfx.coloredscreen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.entity.User;
import org.example.mealplannerfx.control.WrongArgException;

public class ScreenColoredLoginController {
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
            GraphicControllerColored.setThisUser(thisUser);
            GraphicControllerColored.startScreenColored("mainMenu");
        } catch (WrongArgException wrongArgument) {
            errorText.setText(wrongArgument.getWrongArgumentDescription());
        } catch (Exception e){
            errorText.setText("Error: " + e.getMessage());
        }
    }
    @FXML
    private void onRegisterButtonClicked(){
        GraphicControllerColored.startScreenColored("register");
    }

}