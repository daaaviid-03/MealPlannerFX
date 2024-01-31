package org.example.mealplannerfx.dao;

import org.example.mealplannerfx.coloredscreen.ScreenColoredDefWithList;
import org.example.mealplannerfx.coloredscreen.ScreenColoredInListNewIngredientController;
import org.example.mealplannerfx.coloredscreen.ScreenColoredInListNewStepController;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.control.WrongArgException;
import org.example.mealplannerfx.entity.Ingredient;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBDataBoundary {
    private static final int MIN_NUM_OF_CHARS_IN_RECIPE_NAME = 3;
    private static final int MAX_NUMBER_OF_CHARS_IN_RECIPE_NAME = 60;
    private static final int MIN_NUMBER_OF_CHARS_IN_USER_NICKNAME = 3;
    private static final int MAX_NUMBER_OF_CHARS_IN_USER_NICKNAME = 60;
    private static final int MAX_NUMBER_OF_CHARS_IN_RECIPE_DESCRIPTION = 600;
    private static final int MAX_NUMBER_OF_CHARS_IN_RECIPE_STEP = 600;
    private static final int MIN_NUMBER_OF_CHARS_IN_PASSWORD = 3;
    private static final int MAX_NUMBER_OF_CHARS_IN_PASSWORD = 60;
    private static final int MAX_NUMBER_OF_YEARS_FOR_USER = 130;
    private static final float MIN_HEIGHT_IN_CM_FOR_USER = 20f;
    private static final float MAX_HEIGHT_IN_CM_FOR_USER = 300f;
    private static final float MIN_WEIGHT_IN_KG_FOR_USER = 3f;
    private static final float MAX_WEIGHT_IN_KG_FOR_USER = 300f;

    private DBDataBoundary(){}

    private static String correctNormalStringLength(String text, String nameOfString, int minLength, int maxLength,
                                                    String defaultValue) throws WrongArgException {
        if(minLength > 0 && text.isEmpty() && defaultValue == null){
            throw new WrongArgException(nameOfString + " can't be empty.");
        } else if (text.length() < minLength){
            throw new WrongArgException(nameOfString + " is too short (at least there should be " + minLength +
                    " characters).");
        } else if (text.length() > maxLength){
            throw new WrongArgException(nameOfString + " is too long (there should be less than " + maxLength +
                    " characters).");
        }
        return text;
    }
    public static String correctRecipeNameString(String name) throws WrongArgException {
        return correctNormalStringLength(name, "Recipe name", MIN_NUM_OF_CHARS_IN_RECIPE_NAME,
                MAX_NUMBER_OF_CHARS_IN_RECIPE_NAME, "Nameless recipe");
    }
    public static String correctUserNicknameString(String nickname) throws WrongArgException {
        return correctNormalStringLength(nickname, "Nickname", MIN_NUMBER_OF_CHARS_IN_USER_NICKNAME,
                MAX_NUMBER_OF_CHARS_IN_USER_NICKNAME, null);
    }
    public static String correctUserNicknameRegisterString(String nickname) throws WrongArgException {
        correctUserNicknameString(nickname);
        if (DBController.checkUserInDB(nickname)){
            throw new WrongArgException("This nick name is already took.");
        }
        return nickname;
    }
    private static boolean isGoodEmail(String email){
        int correctLevel = 0;
        for (int i = 0; i < email.length(); i++){
            if ((i > 0) && (correctLevel == 0) && (email.charAt(i) == '@')){
                correctLevel = 1;
            } else if ((i > 3) && (correctLevel == 1) && (email.charAt(i) == '.')) {
                correctLevel = 2;
            } else if ((i > 4) && (correctLevel == 2)) {
                correctLevel = 3;
            }
        }
        return correctLevel == 3;
    }
    public static String correctEmailString(String email) throws WrongArgException {
        if(!isGoodEmail(email)){
            throw new WrongArgException("The email is incorrect, it should be like: examle@domain.com.");
        }
        return email;
    }
    public static long correctBirthLong(LocalDate birthLocalDate) throws WrongArgException {
        try {
            long birth = birthLocalDate.toEpochDay();
            long todayDate = Instant.now().getEpochSecond() / 86400;
            if(birth > todayDate){
                throw new WrongArgException("Your birth should be a date before today.");
            } else if(birth < todayDate - (MAX_NUMBER_OF_YEARS_FOR_USER * 365)){
                throw new WrongArgException("You can't have more than " + MAX_NUMBER_OF_YEARS_FOR_USER +
                        " years old.");
            }
            return birth;
        } catch (Exception e) {
            throw new WrongArgException("Your birth should be a valid date.");
        }
    }
    public static float correctHeightFloat(String heightStr) throws WrongArgException {
        try{
            float height = Math.round(Float.parseFloat(heightStr) * 100f) / 100f;
            if(MIN_HEIGHT_IN_CM_FOR_USER > height){
                throw new WrongArgException("Your height should be greater than " + MIN_HEIGHT_IN_CM_FOR_USER +
                        " cm.");
            }
            if(MAX_HEIGHT_IN_CM_FOR_USER < height){
                throw new WrongArgException("Your height should be lower than " + MAX_HEIGHT_IN_CM_FOR_USER +
                        " cm.");
            }
            return height;
        } catch (Exception e){
            throw new WrongArgException("Your height should be a valid height.");
        }
    }
    public static float correctWeightFloat(String weightStr) throws WrongArgException {
        try{
            float weigh = Math.round(Float.parseFloat(weightStr) * 100f) / 100f;
            if(MIN_WEIGHT_IN_KG_FOR_USER > weigh){
                throw new WrongArgException("Your weight should be greater than " + MIN_WEIGHT_IN_KG_FOR_USER +
                        " kg.");
            }
            if(MAX_WEIGHT_IN_KG_FOR_USER < weigh){
                throw new WrongArgException("Your weight should be lower than " + MAX_WEIGHT_IN_KG_FOR_USER +
                        " kg.");
            }
            return weigh;
        } catch (Exception e){
            throw new WrongArgException("Your weight should be a valid weight.");
        }
    }
    public static String correctPasswordString(String password) throws WrongArgException {
        return correctNormalStringLength(password, "Password", MIN_NUMBER_OF_CHARS_IN_PASSWORD,
                MAX_NUMBER_OF_CHARS_IN_PASSWORD, null);
    }
    public static String correctPasswordRegisterString(String password, String passwordRepeated) throws
            WrongArgException {
        correctPasswordString(password);
        correctPasswordString(passwordRepeated);
        if (!password.equals(passwordRepeated)){
            throw new WrongArgException("The passwords doesn't match.");
        }
        return password;
    }

    public static String correctRecipeDescriptionString(String desc) throws WrongArgException {
        return correctNormalStringLength(desc, "Description", 0,
                MAX_NUMBER_OF_CHARS_IN_RECIPE_DESCRIPTION, "(No description)");
    }
    public static void correctIngredients(List<ScreenColoredDefWithList> ingredientsList,
                                          List<Ingredient> ingredients, List<Float> ingredientsQuantities,
                                          List<String> ingredientsPortionsNames) throws WrongArgException {
        for (ScreenColoredDefWithList elemController : ingredientsList) {
            ScreenColoredInListNewIngredientController mCont = (ScreenColoredInListNewIngredientController) elemController;
            String ingredientName = mCont.getIngredientName();
            if(!ingredientName.isEmpty()){
                String errorIntro = "Ingredient in position " + (mCont.getThisPosition() + 1);
                try {
                    ingredients.add(DBController.getIngredientByName(ingredientName));
                } catch (Exception e) {
                    throw new WrongArgException(errorIntro + " doesn't exist in Data Base, please use the provided ones.");
                }
                try {
                    ingredientsQuantities.add(mCont.getQuantityText());
                } catch (Exception e){
                    throw new WrongArgException(errorIntro + " quantity's should be a valid number.");
                }
                try {
                    ingredientsPortionsNames.add(mCont.getPortionName());
                } catch (Exception e){
                    ingredientsPortionsNames.add("g");
                }
            }
        }
    }

    public static void correctIngredients(List<ScreenColoredDefWithList> ingredientsList, List<Ingredient> ingredients) throws WrongArgException {
        for (ScreenColoredDefWithList elemController : ingredientsList) {
            ScreenColoredInListNewIngredientController mCont = (ScreenColoredInListNewIngredientController) elemController;
            String ingredientName = mCont.getIngredientName();
            if(!ingredientName.isEmpty()){
                String errorIntro = "Ingredient in position " + (mCont.getThisPosition() + 1);
                try {
                    ingredients.add(DBController.getIngredientByName(ingredientName));
                } catch (Exception e) {
                    throw new WrongArgException(errorIntro + " doesn't exist in Data Base, please use the provided ones.");
                }
            }
        }
    }

    public static List<String> correctSteps(List<ScreenColoredDefWithList> stepsList) throws WrongArgException {
        List<String> steps = new ArrayList<>();
        for (ScreenColoredDefWithList elemController : stepsList) {
            ScreenColoredInListNewStepController mCont = (ScreenColoredInListNewStepController) elemController;
            String stepInfo = mCont.getStepString();
            correctNormalStringLength(stepInfo, "Step in position " + (mCont.getThisPosition() + 1), 0, MAX_NUMBER_OF_CHARS_IN_RECIPE_STEP, "");
            if(!stepInfo.isEmpty()){
                steps.add(stepInfo);
            }
        }
        return steps;
    }

    public static int correctDuration(String durationStr) throws WrongArgException {
        try{
            return Math.abs(Math.round(Float.parseFloat(durationStr)));
        }catch (Exception e){
            throw new WrongArgException("Duration should be a valid natural integer number.");
        }
    }
}
