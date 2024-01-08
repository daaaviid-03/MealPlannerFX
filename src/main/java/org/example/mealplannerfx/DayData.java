package org.example.mealplannerfx;

import java.io.Serializable;

public class DayData implements Serializable {
    private String userNickname;
    private long dayNumber;
    private Recipe breakfast;
    private Recipe lunch;
    private Recipe dinner;

    public DayData(String userNickname, long dayNumber, Recipe breakfast, Recipe lunch, Recipe dinner) {
        this.userNickname = userNickname;
        this.dayNumber = dayNumber;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
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

    public Recipe getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Recipe breakfast) {
        this.breakfast = breakfast;
    }

    public Recipe getLunch() {
        return lunch;
    }

    public void setLunch(Recipe lunch) {
        this.lunch = lunch;
    }

    public Recipe getDinner() {
        return dinner;
    }

    public void setDinner(Recipe dinner) {
        this.dinner = dinner;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
}
