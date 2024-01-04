package org.example.mealplannerfx;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {
    private long id;
    private String name;
    private String description;
    private User owner;
    private List<String> steps;
    private int duration;
    private List<Ingredient> ingredients;
    private List<Float> ingredientsQuantity;
    private List<String> ingredientsPortionsNames;
    public Recipe(long id, String name, String description, User owner, List<String> steps, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.steps = steps;
        this.duration = duration;
    }

    public Recipe(long id, String name, String description, User owner, List<String> steps, int duration, List<Ingredient> ingredients, List<Float> ingredientsQuantity, List<String> ingredientsPortionsNames) {
        this(id, name, description, owner, steps, duration);
        this.ingredients = ingredients;
        this.ingredientsQuantity = ingredientsQuantity;
        this.ingredientsPortionsNames = ingredientsPortionsNames;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    public Ingredient getIngredientInPos(int pos) {
        return ingredients.get(pos);
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Float> getIngredientsQuantity() {
        return ingredientsQuantity;
    }
    public float getIngredientQuantityInPos(int pos) {
        return ingredientsQuantity.get(pos);
    }

    public void setIngredientsQuantity(List<Float> ingredientsQuantity) {
        this.ingredientsQuantity = ingredientsQuantity;
    }

    public List<String> getIngredientsPortionsNames() {
        return ingredientsPortionsNames;
    }
    public String getIngredientPortionNameInPos(int pos) {
        return ingredientsPortionsNames.get(pos);
    }

    public void setIngredientsPortionsNames(List<String> ingredientsPortionsNames) {
        this.ingredientsPortionsNames = ingredientsPortionsNames;
    }

    public String getStepInPos(int pos) {
        return steps.get(pos);
    }
}
