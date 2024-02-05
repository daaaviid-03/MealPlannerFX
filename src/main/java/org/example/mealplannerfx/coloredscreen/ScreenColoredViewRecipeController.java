package org.example.mealplannerfx.coloredscreen;

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

public class ScreenColoredViewRecipeController implements Initializable {
    @FXML
    private ListView<String> ingredientsList;
    @FXML
    private TextField ownerName;
    @FXML
    private ListView<String> stepsList;
    @FXML
    private Label recipeName;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextField durationText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        establishRecipeToShow(GraphicControllerColored.getRecipeToShow());
    }

    public void establishRecipeToShow(Recipe recipe) {
        if (recipe != null){
            recipeName.setText(recipe.getName());
            descriptionTextArea.setText(recipe.getDescription());
            List<String> ingredientListStr = new ArrayList<>();
            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                String ingTxt = i + ". " + recipe.getIngredientInPos(i).toString() + "  ->  (" +
                        recipe.getIngredientQuantityInPos(i) + ") units of (" +
                        recipe.getIngredientPortionNameInPos(i) + ").";
                ingredientListStr.add(ingTxt);
            }
            ingredientsList.getItems().setAll(FXCollections.observableArrayList(ingredientListStr));
            List<String> stepsListStr = new ArrayList<>();
            for (int i = 0; i < recipe.getSteps().size(); i++) {
                stepsListStr.add(i + ". " + recipe.getStepInPos(i));
            }
            stepsList.getItems().setAll(FXCollections.observableArrayList(stepsListStr));
            durationText.setText(String.valueOf(recipe.getDuration()));
            ownerName.setText(recipe.getOwner());
        }
    }
}
