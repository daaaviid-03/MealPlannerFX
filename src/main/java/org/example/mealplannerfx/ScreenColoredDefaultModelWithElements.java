package org.example.mealplannerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public abstract class ScreenColoredDefaultModelWithElements extends ScreenColoredDefaultModel implements Initializable {
    private List<ScreenColoredElementInListMaskController> ingredientsList = new ArrayList<ScreenColoredElementInListMaskController>();
    private List<ScreenColoredElementInListMaskController> stepsList = new ArrayList<ScreenColoredElementInListMaskController>();
    private boolean ingredientsWithUnits = true;
    private ScreenColoredElementInListMaskController createNewElementInListMask(int position, List<ScreenColoredElementInListMaskController> elementsInList, VBox elementsInVBox, String sourceFile, ScreenColoredDefaultModelWithElements thisClass){
        try {
            // Load fxml
            FXMLLoader fxmlLoader = new FXMLLoader(GraphicControllerColored.class.getResource(sourceFile));
            // Add element to list in the fxml
            elementsInVBox.getChildren().add(position, fxmlLoader.load());
            // Set super controller of element to this controller
            ScreenColoredElementInListMaskController sceilmc = fxmlLoader.getController();
            sceilmc.setControllerSup(thisClass);
            sceilmc.setThisVBox(elementsInVBox);
            sceilmc.setThisPosition(position);
            // Add to list of elements
            elementsInList.add(position, sceilmc);
            // Correct all the indexes of the following ones
            for (int i = position + 1; i < elementsInList.size(); i++) {
                elementsInList.get(i).setThisPositionPlusOne();
            }
            return sceilmc;
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

    public void deleteIngredient(int pos, VBox ingredientsVBox) {
        deleteElementInListMask(pos, ingredientsList, ingredientsVBox);
    }

    public void addIngredient(int pos, VBox ingredientsVBox) {
        ScreenColoredElementInListMaskController sceilmc = this.createNewElementInListMask(pos, ingredientsList, ingredientsVBox, "screen-colored-zz-newIngredient-mask.fxml", this);
        if (!ingredientsWithUnits){
            ScreenColoredZZNewIngredientMaskController sczznimc = (ScreenColoredZZNewIngredientMaskController) sceilmc;
            sczznimc.deletePortions();
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

    public List<ScreenColoredElementInListMaskController> getIngredientsList() {
        return ingredientsList;
    }

    public List<ScreenColoredElementInListMaskController> getStepsList() {
        return stepsList;
    }

    public void setIngredientsWithUnits(boolean ingredientsWithUnits) {
        this.ingredientsWithUnits = ingredientsWithUnits;
    }
}
