package org.example.mealplannerfx.bwscreen;

import javafx.event.ActionEvent;
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
    private Label errorText;
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

    public void onRegisterButtonClicked() {
        try {
            String nick = DBDataBoundary.correctUserNicknameRegisterString(nicknameText.getText());
            String email = DBDataBoundary.correctEmailString(emailText.getText());
            long birth = DBDataBoundary.correctBirthLong(birthDate.getValue());
            float correctHeight = DBDataBoundary.correctHeightFloat(heightText.getText());
            float correctWeight = DBDataBoundary.correctWeightFloat(weightText.getText());
            String correctRepPass = DBDataBoundary.correctPasswordRegisterString(passwordText.getText(), repeatPasswordText.getText());

            User thisUser = DBController.createUser(nick,correctRepPass,correctHeight, correctWeight, email, birth);
            GraphicControllerBW.setThisUser(thisUser);
            GraphicControllerBW.startScreenBW("mainMenu", "init");
        } catch (WrongArgException wrongArgument) {
            errorText.setText(wrongArgument.getWrongArgumentDescription());
        } catch (Exception e){
            errorText.setText(e.getMessage());
        }
    }

    public void returnButtonClicked(ActionEvent actionEvent) {
        GraphicControllerBW.startScreenBW("init");
    }
}
