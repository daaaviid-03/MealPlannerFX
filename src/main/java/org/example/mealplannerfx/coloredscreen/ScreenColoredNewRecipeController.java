package org.example.mealplannerfx.coloredscreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.dao.DBDataBoundary;
import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.control.WrongArgException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ScreenColoredNewRecipeController extends ScreenColoredDefWithElements implements Initializable {

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

    public void createRecipeClicked() {
        try {
            String name = DBDataBoundary.correctRecipeNameString(nameText.getText());
            String desc = DBDataBoundary.correctRecipeDescriptionString(descriptionText.getText());
            List<Ingredient> ingredients = new ArrayList<>();
            List<Float> ingredientsQuantity = new ArrayList<>();
            List<String> ingredientsPortionsNames = new ArrayList<>();


            getQuantityAndPortions(getIngredientsList(), ingredientsQuantity, ingredientsPortionsNames);
            DBDataBoundary.correctIngredients(getIngredientsListPosName(), ingredients);
            List<String> steps = DBDataBoundary.correctSteps(getStepsList());
            int duration = DBDataBoundary.correctDuration(durationText.getText());
            DBController.createNewRecipeDB(name, desc, GraphicControllerColored.getThisUser().getNickname(), steps, duration, ingredients, ingredientsQuantity, ingredientsPortionsNames);
            GraphicControllerColored.startScreenColored("mainMenu");
        } catch (WrongArgException wrongArgument) {
            errorText.setText(wrongArgument.getWrongArgumentDescription());
        } catch (Exception e){
            errorText.setText(e.getMessage());
        }
    }

    private static void getQuantityAndPortions(List<ScreenColoredDefWithList> listIngCon,
                                               List<Float> ingredientsQuantity, List<String> ingredientsPortionsNames) throws WrongArgException {
        for (ScreenColoredDefWithList l : listIngCon){
            ScreenColoredInListNewIngredientController l1 = (ScreenColoredInListNewIngredientController) l;
            String errorIntro = "Ingredient in position " + (l1.getThisPosition() + 1);
            try {
                ingredientsQuantity.add(l1.getQuantityText());
            } catch (Exception e){
                throw new WrongArgException(errorIntro + " quantity's should be a valid number.");
            }
            try {
                ingredientsPortionsNames.add(l1.getPortionName());
            } catch (Exception e){
                ingredientsPortionsNames.add("g");
            }
        }
    }


}
