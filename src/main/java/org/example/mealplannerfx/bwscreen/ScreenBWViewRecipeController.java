package org.example.mealplannerfx.bwscreen;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.mealplannerfx.entity.Recipe;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ScreenBWViewRecipeController implements Initializable {
    @FXML
    private TextField durationText;
    @FXML
    private TextField ownerName;
    @FXML
    private Label recipeName;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private ListView<String> ingredientsList;
    @FXML
    private ListView<String> stepsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        establishTheRecipeToShow(GraphicControllerBW.getRecipeToShow());
    }

    public void establishTheRecipeToShow(Recipe recipe) {
        if (recipe != null){
            descriptionTextArea.setText(recipe.getDescription());
            recipeName.setText(recipe.getName());
            ownerName.setText(recipe.getOwner());
            List<String> stepsListStr = new ArrayList<>();
            for (int i = 0; i < recipe.getSteps().size(); i++) {
                stepsListStr.add((i + 1) + ". " + recipe.getStepInPos(i));
            }
            stepsList.getItems().setAll(FXCollections.observableArrayList(stepsListStr));
            List<String> ingredientListStr = new ArrayList<>();
            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                String ingTxt = (i + 1) + ". " + recipe.getIngredientInPos(i).toString() + "  ->  (" +
                        recipe.getIngredientQuantityInPos(i) + ") units of (" +
                        recipe.getIngredientPortionNameInPos(i) + ").";
                ingredientListStr.add(ingTxt);
            }
            ingredientsList.getItems().setAll(FXCollections.observableArrayList(ingredientListStr));
            durationText.setText(String.valueOf(recipe.getDuration()));
        }
    }
}
