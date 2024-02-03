package org.example.mealplannerfx.control;

public class MealPlannerApp {
    public static void main(String[] args){
        GetGlobalSettings.loadGlobalSettings();
        AppController.startBD();
        AppController.startView();
    }
}
