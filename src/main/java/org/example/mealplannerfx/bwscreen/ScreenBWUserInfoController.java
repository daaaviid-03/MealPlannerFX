package org.example.mealplannerfx.bwscreen;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.mealplannerfx.control.AppController;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.control.GetGlobalSettings;
import org.example.mealplannerfx.control.WrongArgException;
import org.example.mealplannerfx.dao.DAOIngredient;
import org.example.mealplannerfx.dao.DAOUser;
import org.example.mealplannerfx.dao.DBDataBoundary;
import org.example.mealplannerfx.entity.User;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ScreenBWUserInfoController extends ScreenBWDef implements Initializable {
    @FXML
    private TextField emailText;
    @FXML
    private Label errorText;
    @FXML
    private Button avatarButton;
    @FXML
    private ComboBox<String> dBMSComboBox;
    @FXML
    private ComboBox<String> gUIComboBox;
    @FXML
    private DatePicker birthDate;
    @FXML
    private TextField heightText;
    @FXML
    private PasswordField oldPasswordText;
    @FXML
    private TextField weightText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private PasswordField repeatPasswordText;
    private User thisUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModelBW("userInfo", false);
        thisUser = GraphicControllerBW.getThisUser();
        emailText.setText(thisUser.getEmail());
        birthDate.setValue(LocalDate.ofEpochDay(thisUser.getBirth()));
        heightText.setText(String.valueOf(thisUser.getHeight()));
        weightText.setText(String.valueOf(thisUser.getWeight()));
        // Global settings
        gUIComboBox.getItems().setAll(FXCollections.observableArrayList(GetGlobalSettings.getViewMsTypes()));
        gUIComboBox.setValue(AppController.getActualViewMS());
        gUIComboBox.valueProperty().addListener(((observableValue, oldValue, newValue) ->
                AppController.setNextViewMS(newValue)));
        dBMSComboBox.getItems().setAll(FXCollections.observableArrayList(GetGlobalSettings.getDbmsTypes()));
        dBMSComboBox.setValue(AppController.getActualDBMS());
        dBMSComboBox.valueProperty().addListener(((observableValue, oldValue, newValue) ->
                AppController.setNextDBMS(newValue)));
        avatarButton.setText(String.valueOf(thisUser.getNickname().toUpperCase().charAt(0)));
    }

    public void appODBUsers() {
        DAOUser.getDaoUserInstance().loadUsersFromOriginalDB();
    }

    public void applyChangesButtonClicked() {
        try {
            String email = DBDataBoundary.correctEmailString(emailText.getText());
            long birth = DBDataBoundary.correctBirthLong(birthDate.getValue());
            float correctWeight = DBDataBoundary.correctWeightFloat(weightText.getText());
            float correctHeight = DBDataBoundary.correctHeightFloat(heightText.getText());
            String correctRepPass;
            if (oldPasswordText.getText().isEmpty()){
                if (passwordText.getText().isEmpty() && repeatPasswordText.getText().isEmpty()){
                    correctRepPass = thisUser.getPassword();
                } else {
                    throw new WrongArgException("You should write the old password to change it.");
                }
            } else if (thisUser.getPassword().equals(oldPasswordText.getText())){
                correctRepPass = DBDataBoundary.correctPasswordRegisterString(passwordText.getText(),
                        repeatPasswordText.getText());
            } else {
                throw new WrongArgException("Old password isn't correct.");
            }
            User newUser = DBController.modifyUser(thisUser.getNickname(), correctRepPass,correctHeight,
                    correctWeight, email, birth);
            GraphicControllerBW.setThisUser(newUser);
            returnScreen();
        } catch (WrongArgException wrongArgument) {
            errorText.setText(wrongArgument.getWrongArgumentDescription());
        } catch (Exception e){
            errorText.setText(e.getMessage());
        }
    }

    public void logOutButtonClicked() {
        if (showConfirmationScreen("Log out.", "Cancel", "Log out")){
            GraphicControllerBW.setThisUser(null);
            GraphicControllerBW.startScreenBW("login");
        }
    }

    public void deleteAccountButtonClicked() {
        if (showConfirmationScreen("DELETE this account forever. (This action can't be undone)",
                "Cancel", "DELETE ACCOUNT")){
            try {
                DBController.deleteUserFromDB(thisUser.getNickname());
                GraphicControllerBW.setThisUser(null);
                GraphicControllerBW.startScreenBW("login");
            } catch (Exception e) {
                errorText.setText("Can't DELETE this account.");
            }
        }
    }

    public void appODBIngredients() {
        DAOIngredient.getDaoIngredientInstance().loadIngredientsFromOriginalDB();
    }

}
