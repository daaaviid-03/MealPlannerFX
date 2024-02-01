package org.example.mealplannerfx.bwscreen;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.example.mealplannerfx.coloredscreen.ScreenColoredDefWithList;
import org.example.mealplannerfx.coloredscreen.ScreenColoredInListNewIngredientController;
import org.example.mealplannerfx.coloredscreen.ScreenColoredInListNewStepController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ScreenBWDefWithElements extends ScreenBWDef implements Initializable {
    private final List<ScreenBWDefWithList> ingredientsList = new ArrayList<>();
    private final List<ScreenBWDefWithList> stepsList = new ArrayList<>();
    private boolean ingredientsWithUnits = true;
    private ScreenBWDefWithList createNewElementInListMask(int position, List<ScreenBWDefWithList> elementsInList, VBox elementsInVBox, String sourceFile, ScreenBWDefWithElements thisClass){
        try {
            // Load fxml
            FXMLLoader fxmlLoader = new FXMLLoader(GraphicBWColored.class.getResource(sourceFile));
            // Add element to list in the fxml
            elementsInVBox.getChildren().add(position, fxmlLoader.load());
            // Set super controller of element to this controller
            ScreenBWDefWithList screenWithList = fxmlLoader.getController();
            screenWithList.setControllerSup(thisClass);
            screenWithList.setThisVBox(elementsInVBox);
            screenWithList.setThisPosition(position);
            // Add to list of elements
            elementsInList.add(position, screenWithList);
            // Correct all the indexes of the following ones
            for (int i = position + 1; i < elementsInList.size(); i++) {
                elementsInList.get(i).setThisPositionPlusOne();
            }
            return screenWithList;
        } catch (Exception e) {
            return null;
        }
    }

    private void deleteElementInListMask(int pos, List<ScreenBWDefWithList> elementsInList, VBox elementsInVBox){
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
                // No action
            }
        }
    }

    private void upArrowElementInListMask(int pos, List<ScreenBWDefWithList> elementsInList, VBox elementsInVBox){
        if (pos >= 1) {
            try {
                // Change item from list
                ScreenBWDefWithList elementUp = elementsInList.get(pos - 1);
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
                // No action
            }
        }
    }

    private void downArrowElementInListMask(int pos, List<ScreenBWDefWithList> elementsInList, VBox elementsInVBox){
        if (pos < elementsInList.size() - 1) {
            try {
                // Change item from list
                ScreenBWDefWithList elementUp = elementsInList.get(pos);
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
                // No action
            }
        }
    }

    public void deleteIngredient(int pos, VBox ingredientsVBox) {
        deleteElementInListMask(pos, ingredientsList, ingredientsVBox);
    }

    public void addIngredient(int pos, VBox ingredientsVBox) {
        ScreenBWDefWithList screenWithList = createNewElementInListMask(pos, ingredientsList, ingredientsVBox, "screen-colored-zz-newIngredient-mask.fxml", this);
        if (screenWithList != null && !ingredientsWithUnits){
            ScreenBWInListNewIngredientController screenIngredient = (ScreenBWInListNewIngredientController) screenWithList;
            screenIngredient.deletePortions();
        }
    }

    public void deleteStep(int pos, VBox stepsVBox) {
        deleteElementInListMask(pos, stepsList, stepsVBox);
    }

    public void addStep(int pos, VBox stepsVBox) {
        this.createNewElementInListMask(pos, stepsList, stepsVBox, "screen-colored-zz-newStep-mask.fxml", this);
    }

    public void upArrowStepClicked(int pos, VBox stepsVBox) {
        upArrowElementInListMask(pos, stepsList, stepsVBox);
    }

    public void downArrowStepClicked(int pos, VBox stepsVBox) {
        downArrowElementInListMask(pos, stepsList, stepsVBox);
    }

    public List<ScreenBWDefWithList> getIngredientsList() {
        return ingredientsList;
    }

    public Map<Integer, String> getIngredientsListPosName() {
        Map<Integer, String> thisMap = new HashMap<>();
        for (ScreenBWDefWithList l: ingredientsList){
            ScreenBWInListNewIngredientController l1 = (ScreenBWInListNewIngredientController) l;
            thisMap.put(l1.getThisPosition(), l1.getIngredientName());
        }
        return thisMap;
    }

    public Map<Integer, String> getStepsList() {
        Map<Integer, String> steps = new HashMap<>();
        for (ScreenBWDefWithList stepCon : this.stepsList){
            ScreenBWInListNewStepController stepCon2 = (ScreenBWInListNewStepController) stepCon;
            steps.put(stepCon.getThisPosition(), stepCon2.getStepString());
        }
        return steps;
    }

    public void setIngredientsWithUnits(boolean ingredientsWithUnits) {
        this.ingredientsWithUnits = ingredientsWithUnits;
    }
}
