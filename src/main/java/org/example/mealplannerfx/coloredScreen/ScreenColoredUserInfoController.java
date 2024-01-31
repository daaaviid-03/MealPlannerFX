package org.example.mealplannerfx.coloredScreen;

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
import java.util.ResourceBundle;
import java.time.LocalDate;

public class ScreenColoredUserInfoController extends ScreenColoredDef implements Initializable {
    @FXML
    private ComboBox<String> dBMSComboBox;
    @FXML
    private ComboBox<String> gUIComboBox;
    @FXML
    private TextField emailText;
    @FXML
    private DatePicker birthDate;
    @FXML
    private TextField heightText;
    @FXML
    private TextField weightText;
    @FXML
    private PasswordField oldPasswordText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private PasswordField repeatPasswordText;
    @FXML
    private Label errorText;
    private User thisUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModel(false);
        thisUser = getGraphicCC().getThisUser();
        emailText.setText(thisUser.getEmail());
        birthDate.setValue(LocalDate.ofEpochDay(thisUser.getBirth()));
        heightText.setText(String.valueOf(thisUser.getHeight()));
        weightText.setText(String.valueOf(thisUser.getWeight()));
        // Global settings
        dBMSComboBox.getItems().setAll(FXCollections.observableArrayList(GetGlobalSettings.DBMS_TYPES));
        dBMSComboBox.setValue(AppController.getAppControllerInstance().getActualDBMS());
        dBMSComboBox.valueProperty().addListener(((observableValue, oldValue, newValue) ->
                AppController.getAppControllerInstance().setNextDBMS(newValue)));

        gUIComboBox.getItems().setAll(FXCollections.observableArrayList(GetGlobalSettings.VIEW_MS_TYPES));
        gUIComboBox.setValue(AppController.getAppControllerInstance().getActualViewMS());
        gUIComboBox.valueProperty().addListener(((observableValue, oldValue, newValue) ->
                AppController.getAppControllerInstance().setNextViewMS(newValue)));
    }

    public void applyChangesButtonClicked() {
        try {
            String email = DBDataBoundary.correctEmailString(emailText.getText());
            long birth = DBDataBoundary.correctBirthLong(birthDate.getValue());
            float correctHeight = DBDataBoundary.correctHeightFloat(heightText.getText());
            float correctWeight = DBDataBoundary.correctWeightFloat(weightText.getText());
            String correctRepPass;
            if (oldPasswordText.getText().isEmpty()){
                if (passwordText.getText().isEmpty() && repeatPasswordText.getText().isEmpty()){
                    correctRepPass = thisUser.getPassword();
                } else {
                    throw new WrongArgException("You should write the old password to change it.");
                }
            } else if (thisUser.getPassword().equals(oldPasswordText.getText())){
                correctRepPass = DBDataBoundary.correctPasswordRegisterString(passwordText.getText(), repeatPasswordText.getText());
            } else {
                throw new WrongArgException("Old password isn't correct.");
            }
            User newUser = DBController.modifyUser(thisUser.getNickname(), correctRepPass,correctHeight, correctWeight, email, birth);
            getGraphicCC().setThisUser(newUser);
            returnScreen();
        } catch (WrongArgException wrongArgument) {
            errorText.setText(wrongArgument.getWrongArgumentDescription());
        } catch (Exception e){
            errorText.setText(e.getMessage());
        }
    }

    public void logOutButtonClicked() {
        if (showConfirmationScreen("Log out.", "Cancel", "Log out")){
            getGraphicCC().setThisUser(null);
            getGraphicCC().startScreenColored("login");
        }
    }

    public void deleteAccountButtonClicked() {
        if (showConfirmationScreen("DELETE this account forever. (This action can't be undone)",
                "Cancel", "DELETE ACCOUNT")){
            try {
                DBController.deleteUserFromDB(thisUser.getNickname());
                getGraphicCC().setThisUser(null);
                getGraphicCC().startScreenColored("login");
            } catch (Exception e) {
                errorText.setText("Can't DELETE this account.");
            }
        }
    }

    public void appODBIngredients() {
        DAOIngredient.getDaoIngredientInstance().loadIngredientsFromOriginalDB();
    }

    public void appODBUsers() {
        DAOUser.getDaoUserInstance().loadUsersFromOriginalDB();
    }
}
