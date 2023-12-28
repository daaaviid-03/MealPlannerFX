package org.example.mealplannerfx;

import java.io.Serializable;

public class DayData implements Serializable {
    private long dayNumber;
    private Recipe breakfast;
    private Recipe lunch;
    private Recipe dinner;

    public DayData(long dayNumber, Recipe breakfast, Recipe lunch, Recipe dinner) {
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
}
