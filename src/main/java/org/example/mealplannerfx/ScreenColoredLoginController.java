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
        try {
            dBController.checkUserInDB(nick, pass);
            graphicCC.setThisUser(dBController.getUserInfo(nick));
            graphicCC.startScreenColored("mainMenu");
        } catch (WrongArgumentException wrongArgument) {
            System.out.println(wrongArgument.getWrongArgumentDescription());
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    @FXML
    private void onRegisterButtonClicked(){
        this.graphicCC.startScreenColored("register");
    }

}