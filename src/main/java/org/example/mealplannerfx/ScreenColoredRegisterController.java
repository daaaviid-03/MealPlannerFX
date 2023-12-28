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
        String correctNick = this.correctNickname();
        String correctEmail = this.correctEmail();
        Long correctBirth = this.correctBirth();
        Float correctHeight = this.correctHeight();
        Float correctWeight = this.correctWeight();
        String correctRepPass = this.correctPassword();
        wrongNicknameText.setVisible(correctNick == null);
        wrongEmailText.setVisible(correctEmail == null);
        wrongBirthDateText.setVisible(correctBirth == null);
        wrongHeightText.setVisible(correctHeight == null);
        wrongWeightText.setVisible(correctWeight == null);
        wrongRepitedPasswordText.setVisible(correctRepPass == null);
        if(correctNick != null && correctEmail != null && correctBirth != null &&
                correctHeight != null && correctWeight != null && correctRepPass != null){
            dBController.createsUserInDB(correctNick,correctRepPass,correctHeight, correctWeight, correctEmail, correctBirth, false);
        }
        User thisUser = dBController.getUserInfo(correctNick);
        graphicCC.setThisUser(thisUser);
        graphicCC.startScreenColored("mainMenu");
    }
    private String correctNickname(){
        String nickname = nicknameText.getText();
        if(!dBController.checkUserInDB(nickname)){
            return nickname;
        }
        return null;
    }
    private String correctEmail(){
        String email = emailText.getText();
        if(email.matches(".*@.*[.].*")){
            return email;
        }
        return null;
    }
    private Long correctBirth(){
        try {
            long instantBirth = birthDate.getValue().toEpochDay();
            return instantBirth;
        } catch (Exception e){
            return null;
        }
    }
    private Float correctHeight(){
        try{
            float heigh = Float.parseFloat(heightText.getText());
            if(!(20 < heigh && heigh < 300)){
                throw new Exception();
            }
            return heigh;
        } catch (Exception e){
            return null;
        }
    }
    private Float correctWeight(){
        try{
            float weight = Float.parseFloat(weightText.getText());
            if(!(5 < weight && weight < 300)){
                throw new Exception();
            }
            return weight;
        } catch (Exception e){
            return null;
        }
    }
    private String correctPassword(){
        if(passwordText.getText().equals(repeatPasswordText.getText())){
            return passwordText.getText();
        }
        return null;
    }

}
