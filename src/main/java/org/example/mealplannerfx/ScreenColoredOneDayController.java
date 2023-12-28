package org.example.mealplannerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ScreenColoredOneDayController implements Initializable {

    private GraphicControllerColored graphicCC = GraphicControllerColored.getGCCInstance();
    private DBController dBController = DBController.getDBControllerInstance();
    @FXML
    private Button avatarButton;
    @FXML
    private Label nicknameText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String nickname = graphicCC.getThisUser().getNickname();
        nicknameText.setText(nickname);
        avatarButton.setText(String.valueOf(nickname.toUpperCase().charAt(0)));
    }

    public void userInfoButtonClicked(ActionEvent actionEvent) {
        graphicCC.startScreenColored("userInfo");
    }

    public void returnButtonClicked(ActionEvent actionEvent) {
        graphicCC.startScreenColored("mainMenu");
    }
}
