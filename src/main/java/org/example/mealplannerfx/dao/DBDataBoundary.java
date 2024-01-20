package org.example.mealplannerfx.dao;

import org.example.mealplannerfx.coloredScreen.ScreenColoredElementInListMaskController;
import org.example.mealplannerfx.coloredScreen.ScreenColoredZZNewIngredientMaskController;
import org.example.mealplannerfx.coloredScreen.ScreenColoredZZNewStepMaskController;
import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.control.WrongArgumentException;
import org.example.mealplannerfx.entity.Ingredient;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBDataBoundary {
    private final static int MIN_NUM_OF_CHARS_IN_RECIPE_NAME = 3;
    private final static int MAX_NUMBER_OF_CHARS_IN_RECIPE_NAME = 60;
    private final static int MIN_NUMBER_OF_CHARS_IN_USER_NICKNAME = 3;
    private final static int MAX_NUMBER_OF_CHARS_IN_USER_NICKNAME = 60;
    private final static int MAX_NUMBER_OF_CHARS_IN_RECIPE_DESCRIPTION = 600;
    private final static int MAX_NUMBER_OF_CHARS_IN_RECIPE_STEP = 600;
    private final static int MIN_NUMBER_OF_CHARS_IN_PASSWORD = 3;
    private final static int MAX_NUMBER_OF_CHARS_IN_PASSWORD = 60;
    private final static String EMAIL_MATCH = ".*@.*[.].*";
    private final static int MAX_NUMBER_OF_YEARS_FOR_USER = 130;
    private final static float MIN_HEIGHT_IN_CM_FOR_USER = 20f;
    private final static float MAX_HEIGHT_IN_CM_FOR_USER = 300f;
    private final static float MIN_WEIGHT_IN_KG_FOR_USER = 3f;
    private final static float MAX_WEIGHT_IN_KG_FOR_USER = 300f;
    private static String correctNormalStringLength(String text, String nameOfString, int minLength, int maxLength, String defaultValue) throws WrongArgumentException {
        if(minLength > 0 && text.isEmpty() && defaultValue == null){
            throw new WrongArgumentException(nameOfString + " can't be empty.");
        } else if (text.length() < minLength){
            throw new WrongArgumentException(nameOfString + " is too short (at least there should be " + minLength + " characters).");
        } else if (text.length() > maxLength){
            throw new WrongArgumentException(nameOfString + " is too long (there should be less than " + maxLength + " characters).");
        }
        return text;
    }
    public static String correctRecipeNameString(String name) throws WrongArgumentException{
        return correctNormalStringLength(name, "Recipe name", MIN_NUM_OF_CHARS_IN_RECIPE_NAME, MAX_NUMBER_OF_CHARS_IN_RECIPE_NAME, "Nameless recipe");
    }
    public static String correctUserNicknameString(String nickname) throws WrongArgumentException{
        return correctNormalStringLength(nickname, "Nickname", MIN_NUMBER_OF_CHARS_IN_USER_NICKNAME, MAX_NUMBER_OF_CHARS_IN_USER_NICKNAME, null);
    }
    public static String correctUserNicknameRegisterString(String nickname) throws WrongArgumentException{
        correctUserNicknameString(nickname);
        if (DBController.getDBControllerInstance().checkUserInDB(nickname)){
            throw new WrongArgumentException("This nick name is already took.");
        }
        return nickname;
    }
    public static String correctEmailString(String email) throws WrongArgumentException{
        if(!email.matches(EMAIL_MATCH)){
            throw new WrongArgumentException("The email is incorrect, it should be like: examle@domain.com.");
        }
        return email;
    }
    public static long correctBirthLong(LocalDate birthLocalDate) throws WrongArgumentException{
        try {
            long birth = birthLocalDate.toEpochDay();
            long todayDate = Instant.now().getEpochSecond() / 86400;
            if(birth > todayDate){
                throw new WrongArgumentException("Your birth should be a date before today.");
            } else if(birth < todayDate - (MAX_NUMBER_OF_YEARS_FOR_USER * 365)){
                throw new WrongArgumentException("You can't have more than " + MAX_NUMBER_OF_YEARS_FOR_USER + " years old.");
            }
            return birth;
        } catch (Exception e) {
            throw new WrongArgumentException("Your birth should be a valid date.");
        }
    }
    public static float correctHeightFloat(String heightStr) throws WrongArgumentException{
        try{
            float heigh = Float.parseFloat(heightStr);
            if(MIN_HEIGHT_IN_CM_FOR_USER > heigh){
                throw new WrongArgumentException("Your height should be greater than " + MIN_HEIGHT_IN_CM_FOR_USER + " cm.");
            }
            if(MAX_HEIGHT_IN_CM_FOR_USER < heigh){
                throw new WrongArgumentException("Your height should be lower than " + MAX_HEIGHT_IN_CM_FOR_USER + " cm.");
            }
            return heigh;
        } catch (Exception e){
            throw new WrongArgumentException("Your height should be a valid height.");
        }
    }
    public static float correctWeightFloat(String weightStr) throws WrongArgumentException{
        try{
            float weigh = Float.parseFloat(weightStr);
            if(MIN_WEIGHT_IN_KG_FOR_USER > weigh){
                throw new WrongArgumentException("Your weight should be greater than " + MIN_WEIGHT_IN_KG_FOR_USER + " kg.");
            }
            if(MAX_WEIGHT_IN_KG_FOR_USER < weigh){
                throw new WrongArgumentException("Your weight should be lower than " + MAX_WEIGHT_IN_KG_FOR_USER + " kg.");
            }
            return weigh;
        } catch (Exception e){
            throw new WrongArgumentException("Your weight should be a valid weight.");
        }
    }
    public static String correctPasswordString(String password) throws WrongArgumentException{
        return correctNormalStringLength(password, "Password", MIN_NUMBER_OF_CHARS_IN_PASSWORD, MAX_NUMBER_OF_CHARS_IN_PASSWORD, null);
    }
    public static String correctPasswordRegisterString(String password, String passwordRepited) throws WrongArgumentException{
        password = correctPasswordString(password);
        passwordRepited = correctPasswordString(passwordRepited);
        if (!password.equals(passwordRepited)){
            throw new WrongArgumentException("The passwords doesn't match.");
        }
        return password;
    }

    public static String correctRecipeDescriptionString(String desc) throws WrongArgumentException{
        return correctNormalStringLength(desc, "Description", 0, MAX_NUMBER_OF_CHARS_IN_RECIPE_DESCRIPTION, "(No description)");
    }
    public static void correctIngredients(List<ScreenColoredElementInListMaskController> ingredientsList, List<Ingredient> ingredients, List<Float> ingredientsQuantities, List<String> ingredientsPortionsNames) throws WrongArgumentException{
        for (ScreenColoredElementInListMaskController elemController : ingredientsList) {
            ScreenColoredZZNewIngredientMaskController mCont = (ScreenColoredZZNewIngredientMaskController) elemController;
            String ingredName = mCont.getIngredientName();
            if(!ingredName.isEmpty()){
                String errorIntro = "Ingredient in position " + (mCont.getThisPosition() + 1);
                try {
                    ingredients.add(DBController.getDBControllerInstance().getIngredientByName(ingredName));
                } catch (Exception e) {
                    throw new WrongArgumentException(errorIntro + " doesn't exist in Data Base, please use the provided ones.");
                }
                try {
                    ingredientsQuantities.add(mCont.getQuantityText());
                } catch (Exception e){
                    throw new WrongArgumentException(errorIntro + " quantity's should be a valid number.");
                }
                try {
                    ingredientsPortionsNames.add(mCont.getPortionName());
                } catch (Exception e){
                    ingredientsPortionsNames.add("g");
                }
            }
        }
    }

    public static void correctIngredients(List<ScreenColoredElementInListMaskController> ingredientsList, List<Ingredient> ingredients) throws WrongArgumentException{
        for (ScreenColoredElementInListMaskController elemController : ingredientsList) {
            ScreenColoredZZNewIngredientMaskController mCont = (ScreenColoredZZNewIngredientMaskController) elemController;
            String ingredName = mCont.getIngredientName();
            if(!ingredName.isEmpty()){
                String errorIntro = "Ingredient in position " + (mCont.getThisPosition() + 1);
                try {
                    ingredients.add(DBController.getDBControllerInstance().getIngredientByName(ingredName));
                } catch (Exception e) {
                    throw new WrongArgumentException(errorIntro + " doesn't exist in Data Base, please use the provided ones.");
                }
            }
        }
    }

    public static List<String> correctSteps(List<ScreenColoredElementInListMaskController> stepsList) throws WrongArgumentException{
        List<String> steps = new ArrayList<>();
        for (ScreenColoredElementInListMaskController elemController : stepsList) {
            ScreenColoredZZNewStepMaskController mCont = (ScreenColoredZZNewStepMaskController) elemController;
            String stepInfo = mCont.getStepString();
            stepInfo = correctNormalStringLength(stepInfo, "Step in position " + (mCont.getThisPosition() + 1), 0, MAX_NUMBER_OF_CHARS_IN_RECIPE_STEP, "");
            if(!stepInfo.isEmpty()){
                steps.add(stepInfo);
            }
        }
        return steps;
    }

    public static int correctDuration(String durationStr) throws WrongArgumentException{
        try{
            return Math.abs(Integer.parseInt(durationStr));
        }catch (Exception e){
            throw new WrongArgumentException("Duration should be a valid natural integer number.");
        }
    }
}
