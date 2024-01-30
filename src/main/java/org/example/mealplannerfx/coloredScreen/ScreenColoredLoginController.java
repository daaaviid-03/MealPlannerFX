package org.example.mealplannerfx.coloredScreen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.entity.User;
import org.example.mealplannerfx.control.WrongArgumentException;

public class ScreenColoredLoginController {
    private DBController dBController = DBController.getDBControllerInstance();
    private GraphicControllerColored graphicCC = GraphicControllerColored.getGCCInstance();
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
            dBController.checkUserInDB(nick, pass);
            User thisUser = dBController.getUserInfo(nick);
            graphicCC.setThisUser(thisUser);
            graphicCC.startScreenColored("mainMenu");
        } catch (WrongArgumentException wrongArgument) {
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