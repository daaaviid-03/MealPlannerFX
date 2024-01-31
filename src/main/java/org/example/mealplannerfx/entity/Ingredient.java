package org.example.mealplannerfx.entity;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class Ingredient implements Serializable {
    private final String name;
    private final float calories;
    private final float carbohydrates;
    private final float proteins;
    private final float fats;
    private final String category;
    private final Map<String, Float> foodPortions;

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

    public float getCalories() {
        return calories;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public float getProteins() {
        return proteins;
    }

    public float getFats() {
        return fats;
    }

    public String getCategory() {
        return category;
    }

    public float getFoodPortionInGrams(String portionName) {
        if (portionName.equals("g")){
            return 1;
        }
        return foodPortions.get(portionName);
    }

    public Set<String> getFoodPortionsNamesList(){
        return foodPortions.keySet();
    }

    @Override
    public String toString(){
        return name;
    }
}
