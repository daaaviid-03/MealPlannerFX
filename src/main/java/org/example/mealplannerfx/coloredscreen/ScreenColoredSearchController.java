package org.example.mealplannerfx.coloredscreen;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.dao.DBDataBoundary;
import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.control.WrongArgException;
import org.example.mealplannerfx.entity.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ScreenColoredSearchController extends ScreenColoredDefWithElements implements Initializable {
    @FXML
    private CheckBox onlyMinesCheckBox;
    @FXML
    private VBox ingredientsVBox;
    @FXML
    private AnchorPane recipeViewerPlane;
    @FXML
    private ListView<Recipe> listOfFoundedRecipes;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModel(false);
        setIngredientsWithUnits(false);
        addIngredient(0, ingredientsVBox);
        listOfFoundedRecipes.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValueObj, newValueObj) -> selectedElement(newValueObj));
        searchRecipes();
    }

    private void selectedElement(Recipe recipe) {
        lastRecipeSelected = recipe;
        visualizeRecipeInPlane(recipeViewerPlane, recipe);
    }

    public void continueWithSelection() {
        if (lastRecipeSelected != null) {
            GraphicControllerColored.setLastRecipeSelected(lastRecipeSelected);
            returnScreen();
        } else {
            returnButtonClicked();
        }
    }

    public void searchRecipes() {
        try {
            String name = nameToSearch.getText();
            Integer duration = null;
            if (!durationToSearch.getText().isEmpty()){
                duration = DBDataBoundary.correctDuration(durationToSearch.getText());
            }
            List<Ingredient> ingredients = new ArrayList<>();
            DBDataBoundary.correctIngredients(getIngredientsListPosName(), ingredients);
            User thisUser = null;
            if (onlyMinesCheckBox.isSelected()){
                thisUser = GraphicControllerColored.getThisUser();
            }
            boolean[] checkers = {allIngredInComoCheckBox.isSelected(), allRestrictionInCommonCheckBox.isSelected(),
                    greaterEqualCheckBox.isSelected(), lowerEqualCheckBox.isSelected()};
            List<Recipe> recipes = DBController.getRecipesSortedBy(name, exactNameCheckBox.isSelected(), duration, ingredients, thisUser, checkers);
            listOfFoundedRecipes.getItems().setAll(FXCollections.observableArrayList(recipes));
            errorText.setText("");
        } catch (WrongArgException e) {
            errorText.setText(e.getWrongArgumentDescription());
        }
    }
}
