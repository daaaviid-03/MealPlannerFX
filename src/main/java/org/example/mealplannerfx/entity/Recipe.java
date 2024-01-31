package org.example.mealplannerfx.entity;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {
    private final long id;
    private String name;
    private final String description;
    private final String owner;
    private final List<String> steps;
    private int duration;
    private List<Ingredient> ingredients;
    private List<Float> ingredientsQuantity;
    private List<String> ingredientsPortionsNames;
    public Recipe(long id, String name, String description, String owner, List<String> steps, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.steps = steps;
        this.duration = duration;
    }

    public Recipe(long id, String name, String description, String owner, List<String> steps, int duration,
                  List<Ingredient> ingredients, List<Float> ingredientsQuantity, List<String> ingredientsPortionsNames) {
        this(id, name, description, owner, steps, duration);
        this.ingredients = ingredients;
        this.ingredientsQuantity = ingredientsQuantity;
        this.ingredientsPortionsNames = ingredientsPortionsNames;
    }

    public long getId() {
        return id;
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

    public String getOwner() {
        return owner;
    }

    public List<String> getSteps() {
        return steps;
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

    public float getIngredientQuantityInPos(int pos) {
        return ingredientsQuantity.get(pos);
    }

    public void setIngredientsQuantity(List<Float> ingredientsQuantity) {
        this.ingredientsQuantity = ingredientsQuantity;
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
    @Override
    public String toString(){
        return name;
    }
}
