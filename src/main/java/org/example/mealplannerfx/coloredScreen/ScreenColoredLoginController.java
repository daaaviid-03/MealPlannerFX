package org.example.mealplannerfx.coloredScreen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.entity.User;
import org.example.mealplannerfx.control.WrongArgException;

public class ScreenColoredLoginController {
    private final GraphicControllerColored graphicCC = GraphicControllerColored.getGCCInstance();
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
            graphicCC.setThisUser(thisUser);
            graphicCC.startScreenColored("mainMenu");
        } catch (WrongArgException wrongArgument) {
            errorText.setText(wrongArgument.getWrongArgumentDescription());
        } catch (Exception e){
            errorText.setText(e.getMessage());
        }
    }
    @FXML
    private void onRegisterButtonClicked(){
        this.graphicCC.startScreenColored("register");
    }

}