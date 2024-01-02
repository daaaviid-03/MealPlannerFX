package org.example.mealplannerfx;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {
    private String name;
    private String description;
    private User owner;
    private List<String> steps;
    private int duration;
    private List<Ingredient> ingredients;
    private List<Integer> ingredientsQuantity;
    private List<String> ingredientsPortionsNames;

    public Recipe(String name, String description, User owner, List<String> steps, int duration, List<Ingredient> ingredients, List<Integer> ingredientsQuantity, List<String> ingredientsPortionsNames) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.steps = steps;
        this.duration = duration;
        this.ingredients = ingredients;
        this.ingredientsQuantity = ingredientsQuantity;
        this.ingredientsPortionsNames = ingredientsPortionsNames;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Integer> getIngredientsQuantity() {
        return ingredientsQuantity;
    }

    public void setIngredientsQuantity(List<Integer> ingredientsQuantity) {
        this.ingredientsQuantity = ingredientsQuantity;
    }

    public List<String> getIngredientsPortionsNames() {
        return ingredientsPortionsNames;
    }

    public void setIngredientsPortionsNames(List<String> ingredientsPortionsNames) {
        this.ingredientsPortionsNames = ingredientsPortionsNames;
    }
}
