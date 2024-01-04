package org.example.mealplannerfx;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class DBBoundaries extends DBController{
    private final int minNumberOfCharsInRecipeName = 3;
    private final int maxNumberOfCharsInRecipeName = 60;
    private final int minNumberOfCharsInUserNickname = 3;
    private final int maxNumberOfCharsInUserNickname = 60;
    private final int maxNumberOfCharsInRecipeDescription = 600;
    private final int maxNumberOfCharsInRecipeStep = 600;
    private final int minNumberOfCharsInPassword = 3;
    private final int maxNumberOfCharsInPassword = 60;
    private final String emailMatch = ".*@.*[.].*";
    private final int maxNumberOfYearsForUser = 130;
    private final float minHeightInCmForUser = 20f;
    private final float maxHeightInCmForUser = 300f;
    private final float minWeightInKgForUser = 3f;
    private final float maxWeightInKgForUser = 300f;
    private String correctNormalStringLenght(String text, String nameOfString, int minLenght, int maxLenght, String defaultValue) throws WrongArgumentException{
        if(minLenght > 0 && text.isEmpty() && defaultValue == null){
            throw new WrongArgumentException(nameOfString + " can't be empty.");
        } else if (text.length() < minLenght){
            throw new WrongArgumentException(nameOfString + " is too short (at least there should be " + minLenght + " characters).");
        } else if (text.length() > maxLenght){
            throw new WrongArgumentException(nameOfString + " is too long (there should be less than " + maxLenght + " characters).");
        }
        return text;
    }
    @Override
    public String correctRecipeNameString(String name) throws WrongArgumentException{
        return correctNormalStringLenght(name, "Recipe", minNumberOfCharsInRecipeName, maxNumberOfCharsInRecipeName, "Nameless recipe");
    }
    @Override
    public String correctUserNicknameString(String nickname) throws WrongArgumentException{
        return correctNormalStringLenght(nickname, "Nickname", minNumberOfCharsInUserNickname, maxNumberOfCharsInUserNickname, null);
    }
    @Override
    public String correctUserNicknameRegisterString(String nickname) throws WrongArgumentException{
        correctUserNicknameString(nickname);
        if (!checkUserInDB(nickname)){
            throw new WrongArgumentException("This nick name is already took.");
        }
        return nickname;
    }
    @Override
    public String correctEmailString(String email) throws WrongArgumentException{
        if(!email.matches(emailMatch)){
            throw new WrongArgumentException("The email is incorrect, it should be like: examle@domain.com.");
        }
        return email;
    }
    @Override
    public long correctBirthLong(LocalDate birthLocalDate) throws WrongArgumentException{
        try {
            long birth = birthLocalDate.toEpochDay();
            long todayDate = (Long)(Instant.now().getEpochSecond() / 86400);
            if(birth > todayDate){
                throw new WrongArgumentException("Your birth should be a date before today.");
            } else if(birth < todayDate - (maxNumberOfYearsForUser * 365)){
                throw new WrongArgumentException("You can't have more than " + maxNumberOfYearsForUser + " years old.");
            }
            return birth;
        } catch (Exception e) {
            throw new WrongArgumentException("Your birth should be a valid date.");
        }
    }
    @Override
    public float correctHeightFloat(String heightStr) throws WrongArgumentException{
        try{
            float heigh = Float.parseFloat(heightStr);
            if(minHeightInCmForUser > heigh){
                throw new WrongArgumentException("Your height should be greater than " + minHeightInCmForUser + " cm.");
            }
            if(maxHeightInCmForUser < heigh){
                throw new WrongArgumentException("Your height should be lower than " + maxHeightInCmForUser + " cm.");
            }
            return heigh;
        } catch (Exception e){
            throw new WrongArgumentException("Your height should be a valid height.");
        }
    }
    @Override
    public float correctWeightFloat(String weightStr) throws WrongArgumentException{
        try{
            float weigh = Float.parseFloat(weightStr);
            if(minWeightInKgForUser > weigh){
                throw new WrongArgumentException("Your weight should be greater than " + minWeightInKgForUser + " kg.");
            }
            if(maxWeightInKgForUser < weigh){
                throw new WrongArgumentException("Your weight should be lower than " + maxWeightInKgForUser + " kg.");
            }
            return weigh;
        } catch (Exception e){
            throw new WrongArgumentException("Your weight should be a valid weight.");
        }
    }
    @Override
    public String correctPasswordString(String password) throws WrongArgumentException{
        return correctNormalStringLenght(password, "Password", minNumberOfCharsInPassword, maxNumberOfCharsInPassword, null);
    }
    @Override
    public String correctPasswordRegisterString(String password, String passwordRepited) throws WrongArgumentException{
        password = correctPasswordString(password);
        passwordRepited = correctPasswordString(passwordRepited);
        if (!password.equals(passwordRepited)){
            throw new WrongArgumentException("The passwords doesn't match.");
        }
        return password;
    }

    @Override
    public String correctRecipeDescriptionString(String desc) throws WrongArgumentException{
        return correctNormalStringLenght(desc, "Description", 0, maxNumberOfCharsInRecipeDescription, "(No description)");
    }
    @Override
    public void correctIngredients(List<ScreenColoredElementInListMaskController> ingredientsList, List<Ingredient> ingredients, List<Float> ingredientsQuantities, List<String> ingredientsPortionsNames) throws WrongArgumentException{
        for (ScreenColoredElementInListMaskController elemController : ingredientsList) {
            ScreenColoredZZNewIngredientMaskController mCont = (ScreenColoredZZNewIngredientMaskController) elemController;
            String ingredName = mCont.getIngredientName();
            if(!ingredName.isEmpty()){
                String errorIntro = "Ingredient in position " + (mCont.getThisPosition() + 1);
                try {
                    ingredients.add(super.getIngredientByName(ingredName));
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

    @Override
    public List<String> correctSteps(List<ScreenColoredElementInListMaskController> stepsList) throws WrongArgumentException{
        List<String> steps = new ArrayList<String>();
        for (ScreenColoredElementInListMaskController elemController : stepsList) {
            ScreenColoredZZNewStepMaskController mCont = (ScreenColoredZZNewStepMaskController) elemController;
            String stepInfo = mCont.getStepString();
            stepInfo = correctNormalStringLenght(stepInfo, "Step in position " + (mCont.getThisPosition() + 1), 0, maxNumberOfCharsInRecipeStep, "");
            if(!stepInfo.isEmpty()){
                steps.add(stepInfo);
            }
        }
        return steps;
    }

    @Override
    public int correctDuration(String durationStr) throws WrongArgumentException{
        try{
            return Integer.getInteger(durationStr);
        }catch (Exception e){
            throw new WrongArgumentException("Duration should be a valid integer number.");
        }
    }
}
