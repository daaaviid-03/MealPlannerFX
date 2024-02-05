package org.example.mealplannerfx.bwscreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.control.WrongArgException;
import org.example.mealplannerfx.dao.DBDataBoundary;
import org.example.mealplannerfx.entity.Ingredient;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ScreenBWNewRecipeController extends ScreenBWDefWithElements implements Initializable {
    @FXML
    private TextArea descriptionText;
    @FXML
    private Label errorText;
    @FXML
    private TextField durationText;
    @FXML
    private TextField nameText;
    @FXML
    private VBox ingredientsVBox;
    @FXML
    private VBox stepsVBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModelBW("newRecipe", true);
        addIngredient(0, ingredientsVBox);
        addStep(0, stepsVBox);
    }

    private static void getQuantityAndPortions(List<ScreenBWDefWithList> listIngCon,
                                               List<Float> ingredientsQuantity, List<String> ingredientsPortionsNames) throws WrongArgException {
        for (ScreenBWDefWithList l : listIngCon){
            ScreenBWInListNewIngredientController l1 = (ScreenBWInListNewIngredientController) l;
            String errorIntro = "Ingredient in position " + (l1.getThisPosition() + 1);
            try {
                ingredientsQuantity.add(l1.getQuantityTextValue());
            } catch (Exception e){
                throw new WrongArgException(errorIntro + " quantity's should be a valid number.");
            }
            try {
                ingredientsPortionsNames.add(l1.getPortionNameValue());
            } catch (Exception e){
                ingredientsPortionsNames.add("g");
            }
        }
    }

    public void createRecipeClicked() {
        try {
            String name = DBDataBoundary.correctRecipeNameString(nameText.getText());
            String desc = DBDataBoundary.correctRecipeDescriptionString(descriptionText.getText());
            List<String> ingredientsPortionsNames = new ArrayList<>();
            List<Ingredient> ingredients = new ArrayList<>();
            List<Float> ingredientsQuantity = new ArrayList<>();
            getQuantityAndPortions(getIngredientsList(), ingredientsQuantity, ingredientsPortionsNames);
            DBDataBoundary.correctIngredients(getIngredientsListPosName(), ingredients);
            List<String> steps = DBDataBoundary.correctSteps(getStepsList());
            int duration = DBDataBoundary.correctDuration(durationText.getText());
            String[] nameDescOwn = {name,desc,GraphicControllerBW.getThisUser().getNickname()};
            DBController.createNewRecipeDB(nameDescOwn, steps, duration, ingredients, ingredientsQuantity,
                    ingredientsPortionsNames);
            GraphicControllerBW.startScreenBW("mainMenu");
        } catch (WrongArgException wrongArgument) {
            errorText.setText(wrongArgument.getWrongArgumentDescription());
        } catch (Exception e){
            errorText.setText(e.getMessage());
        }
    }

}
