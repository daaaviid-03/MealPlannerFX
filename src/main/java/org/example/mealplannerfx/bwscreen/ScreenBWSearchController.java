package org.example.mealplannerfx.bwscreen;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.control.WrongArgException;
import org.example.mealplannerfx.dao.DBDataBoundary;
import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.entity.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ScreenBWSearchController extends ScreenBWDefWithElements implements Initializable {
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
    private static final String NO_ELEMENTS_FOUND_TEXT = "-- No elements found --";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDefaultModel(false);
        setIngredientsWithUnits(false);
        addIngredient(0, ingredientsVBox);
        listOfFoundedRecipes.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValueObj, newValueObj) -> selectedElement(newValueObj));
        searchRecipes();
    }

    private void selectedElement(Object recipe) {
        if (recipe.toString().equals(NO_ELEMENTS_FOUND_TEXT)){
            return;
        }
        lastRecipeSelected = (Recipe) recipe;
        getGraphicCC().setRecipeToShow(lastRecipeSelected);
        visualizeRecipeInPlane(recipeViewerPlane);
    }

    public void continueWithSelection() {
        if (lastRecipeSelected != null) {
            getGraphicCC().setLastRecipeSelected(lastRecipeSelected);
            returnScreen();
        } else {
            returnButtonClicked();
        }
    }

    public void searchRecipes() {
        try {
            String name = nameToSearch.getText();
            int duration = 0;
            if (!durationToSearch.getText().isEmpty()){
                duration = DBDataBoundary.correctDuration(durationToSearch.getText());
            }
            List<Ingredient> ingredients = new ArrayList<>();
            DBDataBoundary.correctIngredients(getIngredientsListPosName(), ingredients);
            User thisUser = null;
            if (onlyMinesCheckBox.isSelected()){
                thisUser = getGraphicCC().getThisUser();
            }
            List<Recipe> recipes = DBController.getRecipesSortedBy(name, exactNameCheckBox.isSelected(), duration,
                    greaterEqualCheckBox.isSelected(), lowerEqualCheckBox.isSelected(), ingredients,
                    allIngredInComoCheckBox.isSelected(), allRestrictionInCommonCheckBox.isSelected(), thisUser);
            listOfFoundedRecipes.getItems().setAll(FXCollections.observableArrayList(recipes));
            errorText.setText("");
        } catch (WrongArgException e) {
            errorText.setText(e.getWrongArgumentDescription());
        }
    }
}
