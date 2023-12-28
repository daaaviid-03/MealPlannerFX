package org.example.mealplannerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ScreenColoredLoginController {
    private DBController dBController = DBController.getDBControllerInstance();
    private GraphicControllerColored graphicCC = GraphicControllerColored.getGCCInstance();
    @FXML
    private Label wrongNicknameText;
    @FXML
    private Button LoginButton;
    @FXML
    private Label wrongPasswordText;
    @FXML
    private TextField nicknameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Label loginText;
    @FXML
    private void onLoginButtonClicked(){
        String nick = nicknameText.getText();
        String pass = passwordText.getText();
        int querySol = dBController.checkUserInDB(nick, pass);
        User thisUser;
        switch (querySol){
            case 0:
                wrongNicknameText.setVisible(true);
                break;
            case 1:
                wrongNicknameText.setVisible(false);
                wrongPasswordText.setVisible(true);
                break;
            case 2:
                thisUser = dBController.getUserInfo(nick);
                graphicCC.setThisUser(thisUser);
                graphicCC.startScreenColored("mainMenu");
                break;
            case 3:
                thisUser = dBController.getUserInfo(nick);
                graphicCC.setThisUser(thisUser);
                graphicCC.startScreenColored("mainMenuAdmin");
        }
    }
    @FXML
    private void onRegisterButtonClicked(){
        this.graphicCC.startScreenColored("register");
    }

}