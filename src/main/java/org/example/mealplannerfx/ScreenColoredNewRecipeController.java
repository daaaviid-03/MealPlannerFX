package org.example.mealplannerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ScreenColoredNewRecipeController extends ScreenColoredDefaultModelWithElements implements Initializable {

    @FXML
    private TextField durationText;
    @FXML
    private TextField nameText;
    @FXML
    private TextArea descriptionText;
    @FXML
    private VBox ingredientsVBox;
    @FXML
    private VBox stepsVBox;
    @FXML
    private Label errorText;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModel(true);
        addIngredient(0, ingredientsVBox);
        addStep(0, stepsVBox);
    }

    public void createRecipeClicked(ActionEvent actionEvent) {
        try {
            String name = getDBController().correctRecipeNameString(nameText.getText());
            String desc = getDBController().correctRecipeDescriptionString(descriptionText.getText());
            List<Ingredient> ingredients = new ArrayList<Ingredient>();
            List<Float> ingredientsQuantity = new ArrayList<Float>();
            List<String> ingredientsPortionsNames = new ArrayList<String>();
            getDBController().correctIngredients(getIngredientsList(), ingredients, ingredientsQuantity, ingredientsPortionsNames);
            List<String> steps = getDBController().correctSteps(getStepsList());
            int duration = getDBController().correctDuration(durationText.getText());
            getDBController().createNewRecipeDB(name, desc, getGraphicCC().getThisUser(), steps, duration, ingredients, ingredientsQuantity, ingredientsPortionsNames);
            getGraphicCC().startScreenColored("mainMenu");
        } catch (WrongArgumentException wrongArgument) {
            errorText.setText(wrongArgument.getWrongArgumentDescription());
        } catch (Exception e){
            errorText.setText(e.getMessage());
        }
    }
}
