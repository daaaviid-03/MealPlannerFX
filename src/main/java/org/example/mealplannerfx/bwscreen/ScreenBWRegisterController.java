package org.example.mealplannerfx.bwscreen;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.control.WrongArgException;
import org.example.mealplannerfx.dao.DBDataBoundary;
import org.example.mealplannerfx.entity.User;

public class ScreenBWRegisterController {
    @FXML
    private TextField nicknameText;
    @FXML
    private Label errorText;
    @FXML
    private PasswordField repeatedPasswordText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private TextField weightTxt;
    @FXML
    private TextField heightTxt;
    @FXML
    private DatePicker birthDate;
    @FXML
    private TextField emailText;

    public void onRegisterButtonClicked() {
        try {
            String nick = DBDataBoundary.correctUserNicknameRegisterString(nicknameText.getText());
            String email = DBDataBoundary.correctEmailString(emailText.getText());
            long birth = DBDataBoundary.correctBirthLong(birthDate.getValue());
            float correctHeight = DBDataBoundary.correctHeightFloat(heightTxt.getText());
            float correctWeight = DBDataBoundary.correctWeightFloat(weightTxt.getText());
            String correctRepPass = DBDataBoundary.correctPasswordRegisterString(passwordText.getText(), repeatedPasswordText.getText());

            User thisUser = DBController.createUser(nick,correctRepPass,correctHeight, correctWeight, email, birth);
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
