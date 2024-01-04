package org.example.mealplannerfx;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ScreenColoredRegisterController {
    private DBController dBController = DBController.getDBControllerInstance();
    private GraphicControllerColored graphicCC = GraphicControllerColored.getGCCInstance();
    @FXML
    private Label wrongEmailText;
    @FXML
    private Label wrongBirthDateText;
    @FXML
    private Label wrongHeightText;
    @FXML
    private Label wrongWeightText;
    @FXML
    private Label wrongRepitedPasswordText;
    @FXML
    private Label wrongNicknameText;
    @FXML
    private PasswordField repeatPasswordText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private TextField weightText;
    @FXML
    private TextField heightText;
    @FXML
    private DatePicker birthDate;
    @FXML
    private TextField emailText;
    @FXML
    private TextField nicknameText;

    public void onLoginButtonClicked() {
        this.graphicCC.startScreenColored("login");
    }

    public void onRegisterButtonClicked() {
        try {

            String nick = dBController.correctUserNicknameRegisterString(nicknameText.getText());
            String email = dBController.correctEmailString(emailText.getText());
            long birth = dBController.correctBirthLong(birthDate.getValue());
            float correctHeight = dBController.correctHeightFloat(heightText.getText());
            float correctWeight = dBController.correctWeightFloat(weightText.getText());
            String correctRepPass = dBController.correctPasswordRegisterString(passwordText.getText(), repeatPasswordText.getText());

            dBController.createsUserInDB(nick,correctRepPass,correctHeight, correctWeight, email, birth);
            User thisUser = dBController.getUserInfo(nick);
            graphicCC.setThisUser(thisUser);
            graphicCC.startScreenColored("mainMenu");
        } catch (WrongArgumentException wrongArgument) {
            System.out.println(wrongArgument.getWrongArgumentDescription());
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
