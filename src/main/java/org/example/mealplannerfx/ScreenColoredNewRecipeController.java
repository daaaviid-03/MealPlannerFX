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

public class ScreenColoredNewRecipeController implements Initializable {

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
    private Button avatarButton;
    @FXML
    private Label nicknameText;
    private GraphicControllerColored graphicCC = GraphicControllerColored.getGCCInstance();
    private DBController dBController = DBController.getDBControllerInstance();
    private List<ScreenColoredElementInListMaskController> ingredientsList = new ArrayList<ScreenColoredElementInListMaskController>();
    private List<ScreenColoredElementInListMaskController> stepsList = new ArrayList<ScreenColoredElementInListMaskController>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String nickname = graphicCC.getThisUser().getNickname();
        nicknameText.setText(nickname);
        avatarButton.setText(String.valueOf(nickname.toUpperCase().charAt(0)));
        addIngredient(0);
        addStep(0);
    }

    private void createNewElementInListMask(int position, List<ScreenColoredElementInListMaskController> elementsInList, VBox elementsInVBox, String sourceFile){
        try {
            // Load fxml
            FXMLLoader fxmlLoader = new FXMLLoader(GraphicControllerColored.class.getResource(sourceFile));
            // Add element to list in the fxml
            elementsInVBox.getChildren().add(position + 1, fxmlLoader.load());
            // Set super controller of element to this controller
            ScreenColoredElementInListMaskController sceilmc = fxmlLoader.getController();
            sceilmc.setControllerSup(this);
            sceilmc.setThisPosition(position + 1);
            // Add to list of elements
            elementsInList.add(position + 1, sceilmc);
            // Correct all the indexes of the following ones
            for (int i = position + 2; i < elementsInList.size(); i++) {
                elementsInList.get(i).setThisPositionPlusOne();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
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

    public void userInfoButtonClicked(ActionEvent actionEvent) {
        graphicCC.startScreenColored("userInfo");
    }

    public void returnButtonClicked(ActionEvent actionEvent) {
        graphicCC.startScreenColored("mainMenu");
    }

    public void deleteIngredient(int pos) {
        deleteElementInListMask(pos, ingredientsList, ingredientsVBox);
    }

    public void addIngredient(int pos) {
        this.createNewElementInListMask(0, ingredientsList, ingredientsVBox, "screen-colored-zz-newIngredient-mask.fxml");
    }

    public void deleteStep(int pos) {
        deleteElementInListMask(pos, stepsList, stepsVBox);
    }

    public void addStep(int pos) {
        this.createNewElementInListMask(0, stepsList, stepsVBox, "screen-colored-zz-newStep-mask.fxml");
    }

    public void upArrowStepClicked(int pos) {
        upArrowElementInListMask(pos, stepsList, stepsVBox);
    }

    public void downArrowStepClicked(int pos) {
        downArrowElementInListMask(pos, stepsList, stepsVBox);
    }

    public void createRecipeClicked(ActionEvent actionEvent) {
        String name = correctName();
        String desc = correctDesc();
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        List<Integer> ingredientsQuantity = new ArrayList<Integer>();
        List<String> ingredientsPortionsNames = new ArrayList<String>();
        correctIngredients(ingredients, ingredientsQuantity, ingredientsPortionsNames);
        List<String> steps = correctSteps();
        int duration = correctDuration();
        dBController.createNewRecipeDB(name, desc, graphicCC.getThisUser(), steps, duration, ingredients, ingredientsQuantity, ingredientsPortionsNames);
        graphicCC.startScreenColored("mainMenu");
    }

    private String correctName(){
        if(nameText.getText().isEmpty()){
            return "Nameless recipe";
        } else {
            return nameText.getText();
        }
    }
    private String correctDesc(){
        return descriptionText.getText();
    }
    private void correctIngredients(List<Ingredient> ingredients, List<Integer> ingredientsQuantities, List<String> ingredientsPortionsNames) {
        for (ScreenColoredElementInListMaskController elemController : ingredientsList) {
            ScreenColoredZZNewIngredientMaskController mCont = (ScreenColoredZZNewIngredientMaskController) elemController;
            String ingredName = mCont.getIngredientName();
            if(!ingredName.isEmpty()){
                ingredients.add(dBController.getIngredientByName(ingredName));
                try {
                    ingredientsQuantities.add(mCont.getQuantityText());
                } catch (Exception e){
                    ingredientsQuantities.add(15);
                }
                try {
                    ingredientsPortionsNames.add(mCont.getPortionName());
                } catch (Exception e){
                    ingredientsPortionsNames.add("g");
                }
            }
        }
    }
    private List<String> correctSteps() {
        List<String> steps = new ArrayList<String>();
        for (ScreenColoredElementInListMaskController elemController : stepsList) {
            ScreenColoredZZNewStepMaskController mCont = (ScreenColoredZZNewStepMaskController) elemController;
            String stepInfo = mCont.getStepString();
            if(!stepInfo.isEmpty()){
                steps.add(stepInfo);
            }
        }
        return steps;
    }
    private int correctDuration() {
        try{
            return Integer.getInteger(durationText.getText());
        }catch (Exception e){
            return 20;
        }
    }
}
