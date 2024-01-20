package org.example.mealplannerfx.entity;

import org.example.mealplannerfx.control.DBController;

import java.io.Serializable;

public class DayData implements Serializable {
    private String userNickname;
    private long dayNumber;
    private Long breakfast, lunch, dinner;
    public DayData(String userNickname, long dayNumber) {
        this.userNickname = userNickname;
        this.dayNumber = dayNumber;
    }
    public DayData(String userNickname, long dayNumber, Recipe breakfast, Recipe lunch, Recipe dinner) {
        this(userNickname, dayNumber);
        if (breakfast != null) {
            this.breakfast = breakfast.getId();
        }
        if (lunch != null) {
            this.lunch = lunch.getId();
        }
        if (dinner != null) {
            this.dinner = dinner.getId();
        }
    }
    public long getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(long dayNumber) {
        this.dayNumber = dayNumber;
    }
    public Recipe getMealByName(String mealName){
        return switch (mealName){
            case "breakfast" -> getBreakfast();
            case "lunch" -> getLunch();
            case "dinner" -> getDinner();
            default -> null;
        };
    }
    public void setMealByName(String mealName, Recipe recipe){
        switch (mealName){
            case "breakfast" -> setBreakfast(recipe);
            case "lunch" -> setLunch(recipe);
            case "dinner" -> setDinner(recipe);
        }
    }

    private Recipe getRecipeFromId(Long id){
        DBController dbController = DBController.getDBControllerInstance();
        if (id == null || !dbController.hasRecipe(id)){
            return null;
        }
        return dbController.getRecipe(id);
    }

    public Recipe getBreakfast() {
        return getRecipeFromId(breakfast);
    }

    public void setBreakfast(Recipe breakfast) {
        this.breakfast = breakfast.getId();
    }

    public Recipe getLunch() {
        return getRecipeFromId(lunch);
    }

    public void setLunch(Recipe lunch) {
        this.lunch = lunch.getId();
    }

    public Recipe getDinner() {
        return getRecipeFromId(dinner);
    }

    public void setDinner(Recipe dinner) {
        this.dinner = dinner.getId();
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
}