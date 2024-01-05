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

public class ScreenColoredNewRecipeController extends ScreenColoredDefaultModel implements Initializable {

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
    private List<ScreenColoredElementInListMaskController> ingredientsList = new ArrayList<ScreenColoredElementInListMaskController>();
    private List<ScreenColoredElementInListMaskController> stepsList = new ArrayList<ScreenColoredElementInListMaskController>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModel(true);
        addIngredient(0);
        addStep(0);
    }

    private void createNewElementInListMask(int position, List<ScreenColoredElementInListMaskController> elementsInList, VBox elementsInVBox, String sourceFile){
        try {
            // Load fxml
            FXMLLoader fxmlLoader = new FXMLLoader(GraphicControllerColored.class.getResource(sourceFile));
            // Add element to list in the fxml
            elementsInVBox.getChildren().add(position, fxmlLoader.load());
            // Set super controller of element to this controller
            ScreenColoredElementInListMaskController sceilmc = fxmlLoader.getController();
            sceilmc.setControllerSup(this);
            sceilmc.setThisPosition(position);
            // Add to list of elements
            elementsInList.add(position, sceilmc);
            // Correct all the indexes of the following ones
            for (int i = position + 1; i < elementsInList.size(); i++) {
                elementsInList.get(i).setThisPositionPlusOne();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteElementInListMask(int pos, List<ScreenColoredElementInListMaskController> elementsInList, VBox elementsInVBox){
        if (elementsInList.size() > 1) {
            try {
                // Remove item from list
                elementsInList.remove(pos);
                // Correct all the indexes of the following ones
                for (int i = pos; i < elementsInList.size(); i++) {
                    elementsInList.get(i).setThisPositionMinusOne();
                }
                // Remove element from list in the fxml
                elementsInVBox.getChildren().remove(pos);
            } catch (Exception e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }

    private void upArrowElementInListMask(int pos, List<ScreenColoredElementInListMaskController> elementsInList, VBox elementsInVBox){
        if (pos >= 1) {
            try {
                // Change item from list
                ScreenColoredElementInListMaskController elementUp = elementsInList.get(pos - 1);
                elementsInList.remove(pos - 1);
                elementsInList.add(pos, elementUp);
                // Change indexes
                elementsInList.get(pos - 1).setThisPositionMinusOne();
                elementsInList.get(pos).setThisPositionPlusOne();
                // Change item from list in the fxml
                Node nodeUp = elementsInVBox.getChildren().get(pos - 1);
                elementsInVBox.getChildren().remove(pos - 1);
                elementsInVBox.getChildren().add(pos, nodeUp);
            } catch (Exception e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }

    private void downArrowElementInListMask(int pos, List<ScreenColoredElementInListMaskController> elementsInList, VBox elementsInVBox){
        if (pos < elementsInList.size() - 1) {
            try {
                // Change item from list
                ScreenColoredElementInListMaskController elementUp = elementsInList.get(pos);
                elementsInList.remove(pos);
                elementsInList.add(pos + 1, elementUp);
                // Change indexes
                elementsInList.get(pos).setThisPositionMinusOne();
                elementsInList.get(pos + 1).setThisPositionPlusOne();
                // Change item from list in the fxml
                Node nodeUp = elementsInVBox.getChildren().get(pos);
                elementsInVBox.getChildren().remove(pos);
                elementsInVBox.getChildren().add(pos + 1, nodeUp);
            } catch (Exception e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }



    public void deleteIngredient(int pos) {
        deleteElementInListMask(pos, ingredientsList, ingredientsVBox);
    }

    public void addIngredient(int pos) {
        this.createNewElementInListMask(pos, ingredientsList, ingredientsVBox, "screen-colored-zz-newIngredient-mask.fxml");
    }

    public void deleteStep(int pos) {
        deleteElementInListMask(pos, stepsList, stepsVBox);
    }

    public void addStep(int pos) {
        this.createNewElementInListMask(pos, stepsList, stepsVBox, "screen-colored-zz-newStep-mask.fxml");
    }

    public void upArrowStepClicked(int pos) {
        upArrowElementInListMask(pos, stepsList, stepsVBox);
    }

    public void downArrowStepClicked(int pos) {
        downArrowElementInListMask(pos, stepsList, stepsVBox);
    }

    public void createRecipeClicked(ActionEvent actionEvent) {
        try {
            String name = getDBController().correctRecipeNameString(nameText.getText());
            String desc = getDBController().correctRecipeDescriptionString(descriptionText.getText());
            List<Ingredient> ingredients = new ArrayList<Ingredient>();
            List<Float> ingredientsQuantity = new ArrayList<Float>();
            List<String> ingredientsPortionsNames = new ArrayList<String>();
            getDBController().correctIngredients(ingredientsList, ingredients, ingredientsQuantity, ingredientsPortionsNames);
            List<String> steps = getDBController().correctSteps(stepsList);
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
