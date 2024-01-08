package org.example.mealplannerfx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ScreenColoredSearchNewFoodController extends ScreenColoredDefaultModelWithElements implements Initializable {
    @FXML
    private CheckBox onlyMinesCheckBox;
    @FXML
    private VBox ingredientsVBox;
    @FXML
    private AnchorPane recipeViewerPlane;
    @FXML
    private ListView listOfFoundedRecipes;
    @FXML
    private CheckBox allRestrictionInCommonCheckBox;
    @FXML
    private CheckBox lowerEqualCheckBox;
    @FXML
    private CheckBox allIngredInComoCheckBox;
    @FXML
    private CheckBox greaterEqualCheckBox;
    @FXML
    private TextField durationToSearch;
    @FXML
    private CheckBox exactNameCheckBox;
    @FXML
    private TextField nameToSearch;
    @FXML
    private Label errorText;
    private Recipe lastRecipeSelected;
    private final static String NO_ELEMENTS_FOUND_TEXT = "-- No elements found --";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModel(false);
        setIngredientsWithUnits(false);
        addIngredient(0, ingredientsVBox);

        listOfFoundedRecipes.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValueObj, newValueObj) -> {
            selectedElement(newValueObj);
        });
    }

    private void selectedElement(Object recipe) {
        if (recipe.toString().equals(NO_ELEMENTS_FOUND_TEXT)){
            return;
        }
        lastRecipeSelected = (Recipe) recipe;
        getGraphicCC().setDayToShow(lastRecipeSelected);
        visualizeRecipeInPlane(recipeViewerPlane);
    }

    public void continueWithSelection(ActionEvent actionEvent) {
        if (lastRecipeSelected != null) {
            getGraphicCC().setLastRecipeSelected(lastRecipeSelected);
            returnScreen();
        } else {
            returnButtonClicked(actionEvent);
        }
    }

    public void searchRecipes(ActionEvent actionEvent) {
        try {
            String name = nameToSearch.toString();
            int duration = getDBController().correctDuration(durationToSearch.getText());
            List<Ingredient> ingredients = new ArrayList<>();
            getDBController().correctIngredients(getIngredientsList(), ingredients);
            List<Recipe> recipes = getDBController().getRecipesSortedBy(name, exactNameCheckBox.isSelected(), duration,
                    greaterEqualCheckBox.isSelected(), lowerEqualCheckBox.isSelected(), ingredients,
                    allIngredInComoCheckBox.isSelected(), allRestrictionInCommonCheckBox.isSelected(),
                    getGraphicCC().getThisUser(), onlyMinesCheckBox.isSelected());
            if (recipes.isEmpty()){
                // Si no hay elementos mostrar el sin elementos
                listOfFoundedRecipes.getItems().setAll(FXCollections.observableArrayList(NO_ELEMENTS_FOUND_TEXT));
            } else {
                listOfFoundedRecipes.getItems().setAll(FXCollections.observableArrayList(recipes));
            }
            // ----------------------------------------------------------------------------------------------------
        } catch (WrongArgumentException e) {
            errorText.setText(e.getWrongArgumentDescription());
        }
    }
}
