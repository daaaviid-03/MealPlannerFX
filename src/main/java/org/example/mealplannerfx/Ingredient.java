package org.example.mealplannerfx;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class Ingredient implements Serializable {
    private String name;
    private float calories;
    private float carbohydrates;
    private float proteins;
    private float fats;
    private String category;
    private Map<String, Float> foodPortions;

    public Ingredient(String name, float calories, float carbohydrates, float proteins, float fats, String category, Map<String, Float> foodPortions) {
        this.name = name;
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
        this.fats = fats;
        this.category = category;
        this.foodPortions = foodPortions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public float getProteins() {
        return proteins;
    }

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getFoodPortionInGrams(String portionName) {
        return foodPortions.get(portionName);
    }

    public Set<String> getFoodPortionsNamesList(){
        return foodPortions.keySet();
    }

    public void setFoodPortions(Map<String, Float> foodPortions) {
        this.foodPortions = foodPortions;
    }
}
