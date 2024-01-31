package org.example.mealplannerfx.entity;

import java.io.Serializable;

public class DayData implements Serializable {
    private final String userNickname;
    private final long dayNumber;
    private Long breakfast;
    private Long lunch;
    private Long dinner;
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

    public Long getMealByName(String mealName){
        return switch (mealName){
            case "breakfast" -> getBreakfastId();
            case "lunch" -> getLunchId();
            case "dinner" -> getDinnerId();
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

    public void setBreakfast(Recipe breakfast) {
        this.breakfast = breakfast.getId();
    }

    public void setLunch(Recipe lunch) {
        this.lunch = lunch.getId();
    }

    public void setDinner(Recipe dinner) {
        this.dinner = dinner.getId();
    }

    public String getUserNickname() {
        return userNickname;
    }
    public Long getBreakfastId() {
        return breakfast;
    }
    public Long getLunchId() {
        return lunch;
    }
    public Long getDinnerId() {
        return dinner;
    }
}
