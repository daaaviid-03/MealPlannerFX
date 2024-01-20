package org.example.mealplannerfx.control;

import org.example.mealplannerfx.entity.DayData;
import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.entity.User;
import org.example.mealplannerfx.coloredScreen.ScreenColoredElementInListMaskController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.*;

public abstract class DBController {
    private final static String INGREDIENTS_ORIGINAL_DB_TXT = "fileData/originalDataToDB/ingredientsOriginalDB.txt";
    private Map<String, User> users = new HashMap<>();
    private Map<Long, Recipe> recipes = new HashMap<>();
    private Map<String, Ingredient> ingredients = new HashMap<>();
    private Map<Long, DayData> userDayData = new HashMap<>();
    private User thisUser;
    private List<String> listNamesOfIngredientsSorted;
    private static DBController dBControllerInstance = null;
    private long nextRecipeId = 0;

    // Singelton GOF
    public DBController(){
        setDBControllerInstance(this);
    }
    private void setDBControllerInstance(DBController dbController){
        if (dBControllerInstance == null) {
            dBControllerInstance = dbController;
        }
    }
    public static DBController getDBControllerInstance(){
        return dBControllerInstance;
    }

    // DB Controller functions

    /**
     * Obtain the next available recipe id and increase it
     * @return the next available recipe id
     */
    public long getNextRecipeId(){
        return ++nextRecipeId;
    }

    /**
     * Set the max id available if it's lower than the provided one
     * @param actualId tha actual id of the recipe
     */
    public void setMaxRecipeId(long actualId){
        if (actualId > nextRecipeId){
            nextRecipeId = actualId;
        }
    }

    /**
     * Delete all recipes from specific user
     * @param user user reference
     */
    private void deleteAllRecipesFromUser(User user){
        recipes.entrySet().removeIf(entry -> entry.getValue().getOwner().equals(user.getNickname()));
    }

    /**
     * Load all the ingredients from the original DB (.txt)
     * @param eraseActualDB whether the old ingredients DB is deleted
     */
    public void loadIngredientsFromOriginalDB(boolean eraseActualDB){
        if (eraseActualDB){
            ingredients = new HashMap<>();
        }
        try {
            BufferedReader in = new BufferedReader(new FileReader(INGREDIENTS_ORIGINAL_DB_TXT));
            String line;
            while((line = in.readLine()) != null){
                Map<String, Float> portions = new HashMap<>();
                String[] s = line.split("\t");
                for (int i = 6; i < s.length; i += 2) {
                    portions.put(s[i], Float.valueOf(s[i + 1]));
                }
                addIngredient(new Ingredient(s[0], Float.parseFloat(s[1]), Float.parseFloat(s[2]), Float.parseFloat(s[3]), Float.parseFloat(s[4]), s[5], portions));
            }
            in.close();
        } catch (Exception e) {
            System.err.println("No original DB file found.");
        }
        saveIngredientsInDB();
    }
    public void checkUserInDB(String nick, String pass) throws WrongArgumentException{
        if(this.users.containsKey(nick)){
            if(!this.users.get(nick).getPassword().equals(pass)){
                throw new WrongArgumentException("Password incorrect");
            }
        } else{
            throw new WrongArgumentException("Nickname doesn't exists");
        }
    }
    public boolean checkUserInDB(String nick){
        return this.users.containsKey(nick);
    }
    public void createsUserInDB(String nick, String pass, float height, float weight, String email, long birth) throws Exception{
        try {
            User user = new User(nick, height, weight, birth, email, pass);
            users.put(nick, user);
            saveUsersInDB();
        } catch (Exception e){
            throw e;
        }
    }
    public User getUserInfo(String nick) throws Exception{
        return this.users.get(nick);
    }
    public List<DayData> getDaysData(long fromDate, long toDate) throws Exception{
        List<DayData> thisDaysData = new ArrayList<>();
        for(DayData dayData : userDayData.values()){
            if (dayData.getDayNumber() >= fromDate && dayData.getDayNumber() < toDate){
                thisDaysData.add(dayData);
            }
        }
        return thisDaysData;
    }
    public void deleteRecipeFromDB(Recipe recipe) throws Exception{
        try{
            this.recipes.remove(recipe);
            saveRecipesInDB();
        }catch (Exception e){
            throw e;
        }
    }
    public void createNewRecipeDB(String name, String description, String owner, List<String> steps, int duration, List<Ingredient> ingredients, List<Float> ingredientsQuantity, List<String> ingredientsPortionsNames) throws Exception{
        try {
            Recipe recipe = new Recipe(getNextRecipeId(), name, description, owner, steps, duration, ingredients, ingredientsQuantity, ingredientsPortionsNames);
            this.recipes.put(recipe.getId(), recipe);
            saveRecipesInDB();
        } catch (Exception e){
            throw e;
        }
    }
    public List<Recipe> getRecipesSortedBy(String name, Boolean exactSameName, Integer duration,
                                           Boolean toBeGraterEqualDuration, Boolean toBeLowerEqualDuration,
                                           List<Ingredient> ingredients, Boolean allOfThoseIngredients,
                                           Boolean allFieldsInCommon, User thisUser, Boolean onlyFromUser) throws WrongArgumentException{
        List<Recipe> correctRecipes = new ArrayList<>();
        for (Recipe recipe : recipes.values()){
            // Declare boolean for each field
            boolean isCandidateForName = true;
            boolean isCandidateForDuration = true;
            boolean isCandidateForIngredients = allFieldsInCommon;
            boolean isCandidateForUser = true;
            // Obtain this recipe info
            String thisName = recipe.getName();
            int thisDuration = recipe.getDuration();
            List<Ingredient> thisIngredients = recipe.getIngredients();
            // Compare recipe with filter
            isCandidateForName = (name == null) || ((exactSameName && thisName.equals(name)) || (!exactSameName && thisName.contains(name)));
            isCandidateForDuration = (duration == null) || ((toBeGraterEqualDuration && !toBeLowerEqualDuration && thisDuration >= duration) ||
                    (!toBeGraterEqualDuration && toBeLowerEqualDuration && thisDuration <= duration) ||
                    (toBeGraterEqualDuration && toBeLowerEqualDuration && thisDuration == duration));
            if (ingredients != null) {
                for (Ingredient ingredient : ingredients) {
                    if (allOfThoseIngredients) {
                        isCandidateForIngredients &= thisIngredients.contains(ingredient);
                    } else {
                        isCandidateForIngredients |= thisIngredients.contains(ingredient);
                    }
                }
            } else {
                isCandidateForIngredients = true;
            }
            if(onlyFromUser){
                isCandidateForUser = recipe.getOwner().equals(thisUser.getNickname());
            }
            // Last filter of fields
            if(isCandidateForUser && ((allFieldsInCommon && isCandidateForName && isCandidateForDuration && isCandidateForIngredients) ||
                    (!allFieldsInCommon && (isCandidateForName || isCandidateForDuration || isCandidateForIngredients)))){
                correctRecipes.add(recipe);
            }
        }
        if (correctRecipes.isEmpty()){
            throw new WrongArgumentException("No recipe matches with that filters.");
        }
        return correctRecipes;
    }
    public void deleteUserFromDB(String nick) throws Exception{
        try {
            deleteAllRecipesFromUser(this.users.get(nick));
            saveRecipesInDB();
            this.users.remove(nick);
            saveUsersInDB();
        } catch (Exception e){
            throw e;
        }
    }

    public DayData getSpecificDayData(long dayNumber){
        try{
            if (!userDayData.containsKey(dayNumber)) {
                DayData thisDayData = new DayData(thisUser.getNickname(), dayNumber);
                userDayData.put(dayNumber, thisDayData);
                saveDayDataFromUserInDB();
            }
            return userDayData.get(dayNumber);
        } catch (Exception e){
            return null;
        }
    }

    public void loadUserInfoFromDB(User user){
        thisUser = user;
        userDayData = new HashMap<>();
        loadDayDataFromUserFromDB(user.getNickname());
    }

    public List<String> getListOfIngredientsNamesSorted(){
        if (listNamesOfIngredientsSorted == null){
            List<String> listNames = new ArrayList<String>(ingredients.keySet());
            Collections.sort(listNames);
            listNamesOfIngredientsSorted = listNames;
        }
        return listNamesOfIngredientsSorted;
    }
    public Ingredient getIngredientByName(String name) throws Exception{
        if(ingredients.containsKey(name)){
            return ingredients.get(name);
        } else {
            throw new Exception("No such ingredient.");
        }
    }
    public List<String> getIngredientPortionsNames(String name) throws Exception {
        List<String> portionsList = new ArrayList<>(getIngredientByName(name).getFoodPortionsNamesList());
        portionsList.add("g");
        return portionsList;
    }
    public List<String> getListOfIngredientsNamesSortedBy(String toSortBy) {
        getListOfIngredientsNamesSorted();
        if (toSortBy.isEmpty()){
            return listNamesOfIngredientsSorted;
        }
        toSortBy = toSortBy.toLowerCase();
        List<String> ingredientsSortedBy = new ArrayList<>();
        for (String ingred : listNamesOfIngredientsSorted){
            boolean toAdd = true;
            String ingredLowercased = ingred.toLowerCase();
            for (String subName : toSortBy.split(" ")){
                if (!ingredLowercased.contains(subName)){
                    toAdd = false;
                }
            }
            if (toAdd){
                ingredientsSortedBy.add(ingred);
            }
        }
        return ingredientsSortedBy;
    }

    public List<User> getUsersValues() {
        return new ArrayList<User>(users.values());
    }

    public void addUser(User user){
        this.users.put(user.getNickname(), user);
    }

    public List<Recipe> getRecipesValues() {
        return new ArrayList<Recipe>(recipes.values());
    }

    public Recipe getRecipe(long id){
        return recipes.get(id);
    }

    public void addRecipe(Recipe recipe){
        this.recipes.put(recipe.getId(), recipe);
        this.setMaxRecipeId(recipe.getId());
    }

    public void createsAndSetsUserInDB(String nick, String pas, float height, float weight, String email, long birth) {
        try {
            createsUserInDB(nick, pas, height, weight, email, birth);
            User thisUser = getUserInfo(nick);
            setThisUser(thisUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ingredient> getIngredientsValues() {
        return new ArrayList<Ingredient>(ingredients.values());
    }

    public void addIngredient(Ingredient ingredient){
        this.ingredients.put(ingredient.getName(), ingredient);
    }

    public List<String> getListNamesOfIngredientsSorted() {
        return listNamesOfIngredientsSorted;
    }

    public void setListNamesOfIngredientsSorted(List<String> listNamesOfIngredientsSorted) {
        this.listNamesOfIngredientsSorted = listNamesOfIngredientsSorted;
    }

    public boolean hasRecipe(Long id) {
        return recipes.containsKey(id);
    }

    public void addUserDayData(DayData userDayData) {
        this.userDayData.put(userDayData.getDayNumber(), userDayData);
    }

    public User getThisUser() {
        return thisUser;
    }

    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
        loadDayDataFromUserFromDB(thisUser.getNickname());
    }
    public Collection<DayData> getUserDayDataValues() {
        return userDayData.values();
    }

    // Boundary functions
//    public abstract String correctRecipeNameString(String name) throws WrongArgumentException;
//    public abstract String correctUserNicknameString(String nickname) throws WrongArgumentException;
//    public abstract String correctUserNicknameRegisterString(String nickname) throws WrongArgumentException;
//    public abstract String correctEmailString(String email) throws WrongArgumentException;
//    public abstract long correctBirthLong(LocalDate birthLocalDate) throws WrongArgumentException;
//    public abstract float correctHeightFloat(String heightStr) throws WrongArgumentException;
//    public abstract float correctWeightFloat(String weightStr) throws WrongArgumentException;
//    public abstract String correctPasswordString(String password) throws WrongArgumentException;
//    public abstract String correctPasswordRegisterString(String password, String passwordRepited) throws WrongArgumentException;
//    public abstract String correctRecipeDescriptionString(String desc) throws WrongArgumentException;
//    public abstract void correctIngredients(List<ScreenColoredElementInListMaskController> ingredientsList, List<Ingredient> ingredients, List<Float> ingredientsQuantities, List<String> ingredientsPortionsNames) throws WrongArgumentException;
//
//    public abstract void correctIngredients(List<ScreenColoredElementInListMaskController> ingredientsList, List<Ingredient> ingredients) throws WrongArgumentException;
//
//    public abstract List<String> correctSteps(List<ScreenColoredElementInListMaskController> stepsList) throws WrongArgumentException;
//    public abstract int correctDuration(String durationStr) throws WrongArgumentException;

    // DB obtainer functions
    public void loadDataFromDB(){
        loadUsersFromDB();
        loadRecipesFromDB();
        loadIngredientsFromDB();
    }
    public void saveDataToDB(){
        saveUsersInDB();
        saveRecipesInDB();
        saveIngredientsInDB();
    }
    public abstract void saveUsersInDB();
    public abstract void saveRecipesInDB();
    public abstract void saveIngredientsInDB();
    public abstract void saveDayDataFromUserInDB();
    public abstract void loadUsersFromDB();
    public abstract void loadRecipesFromDB();
    public abstract void loadIngredientsFromDB();
    public abstract void loadDayDataFromUserFromDB(String nick);
    public abstract void deleteAllDayDataFromUserFromTheDB(String nick);

    public User modifyUser(String nickname, String correctRepPass, float correctHeight, float correctWeight, String email, long birth) {
        thisUser.setPassword(correctRepPass);
        thisUser.setHeight(correctHeight);
        thisUser.setWeight(correctWeight);
        thisUser.setEmail(email);
        thisUser.setBirth(birth);
        return thisUser;
    }
}
