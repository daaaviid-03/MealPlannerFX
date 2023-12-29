package org.example.mealplannerfx;

import java.io.*;
import java.util.*;

public class DBFileController extends DBController{

    private Map<String, User> users;
    private List<Recipe> recipes;
    private Map<String, Ingredient> ingredients;
    private List<String> listNamesOfIngredientsSorted;
    private final String usersFileNameDB = "fileData/usersFileData/usersInfo_DB.usersInfo";
    private final String recipesFileNameDB = "fileData/usersFileData/recipesInfo_DB.recipesInfo";
    private final String ingredientsFileNameDB = "fileData/usersFileData/ingredientsInfo_DB.ingredientsInfo";
    public DBFileController(){
        super.setDBControllerInstance(this);
        this.loadDataFromDB();
    }

    @Override
    public int checkUserInDB(String nick, String pass){
        if(this.users.containsKey(nick)){
            if(this.users.get(nick).getPassword().equals(pass)){
                if(this.users.get(nick).isAdmin()){
                    return 3;
                } else {
                    return 2;
                }
            }
            return 1;
        } else{
            return 0;
        }
    }
    @Override
    public boolean checkUserInDB(String nick){
        return this.users.containsKey(nick);
    }
    @Override
    public QueryReply createsUserInDB(String nick, String pass, float height, float weight, String email, long birth, Boolean isAdmin){
        try {
            User user = new User(nick, height, weight, birth, email, isAdmin, pass);
            users.put(nick, user);
            this.saveUsersInDB();
        } catch (Exception e){
            return QueryReply.ErrorInQuery;
        }
        return QueryReply.DoneCorrectly;
    }
    @Override
    public User getUserInfo(String nick){
        return this.users.get(nick);
    }
    @Override
    public ArrayList<User> getListOfAllUsers(){
        return (ArrayList<User>) this.users.values();
    }
    @Override
    public QueryReply deleteUserFromDB(String nick){
        try {
            this.deleteAllRecipesFromUser(this.users.get(nick));
            this.saveRecipesInDB();
            this.users.remove(nick);
            this.saveUsersInDB();
        } catch (Exception e){
            return QueryReply.ErrorInQuery;
        }
        return QueryReply.DoneCorrectly;
    }
    private void deleteAllRecipesFromUser(User user){
        int errasedNum = 0;
        for (int i = 0; i < this.recipes.size() - errasedNum; i++) {
            if(this.recipes.get(i).getOwner() == user){
                this.recipes.remove(i);
                i--;
                errasedNum++;
            }
        }
    }
    @Override
    public List<DayData> getUserCalendarInfo(String nick, int fromDate, int toDate){
        return null;
        //-----------------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------------
    }
    @Override
    public QueryReply createNewRecipeDB(String name, String description, User owner, List<String> steps, int duration, List<Ingredient> ingredients, List<Integer> ingredientsQuantity){
        try {
            Recipe recipe = new Recipe(name, description, owner, steps, duration, ingredients, ingredientsQuantity);
            this.recipes.add(recipe);
            this.saveRecipesInDB();
        } catch (Exception e){
            return QueryReply.ErrorInQuery;
        }
        return QueryReply.DoneCorrectly;
    }
    @Override
    public List<Recipe> getRecipesSortedBy(String name, Boolean exactSameName, int duration, Boolean exactNotGrater, Boolean exactNotLower, List<Ingredient> ingredients, Boolean exactThoseIngredients){
        return null;
    }
    @Override
    public QueryReply deleteRecipeFromDB(Recipe recipe){
        try{
            this.recipes.remove(recipe);
            this.saveRecipesInDB();
        }catch (Exception e){
            return QueryReply.ErrorInQuery;
        }
        return QueryReply.DoneCorrectly;
    }
    @Override
    public void loadDataFromDB(){
        this.loadUsersFromDB();
        this.loadRecipesFromDB();
        this.loadIngredientsFromDB();
    }
    @Override
    public void saveDataToDB(){
        this.saveUsersInDB();
        this.saveRecipesInDB();
        this.saveIngredientsInDB();
    }
    @Override
    public List<String> getListOfIngredientsNamesSorted(){
        if (listNamesOfIngredientsSorted == null){
            List<String> listNames = new ArrayList<String>();
            listNames.addAll(ingredients.keySet());
            Collections.sort(listNames);
            listNamesOfIngredientsSorted = listNames;
        }
        return listNamesOfIngredientsSorted;
    }
    @Override
    public Ingredient getIngredientByName(String name) {
        if(ingredients.containsKey(name)){
            return ingredients.get(name);
        } else {
            return null;
        }
    }

    private void saveUsersInDB(){
        File thisFile = new File(usersFileNameDB);
        try {
            // Guardar el objeto en el archivo binario
            ObjectOutputStream stateFileObj = new ObjectOutputStream(new FileOutputStream(thisFile));
            for (User user : this.users.values()) {
                stateFileObj.writeObject(user);
            }
            stateFileObj.close();
        } catch (Exception e) {
            // Si no se puede crear el archivo
            System.err.println(e.getMessage());
        }
    }
    private void loadUsersFromDB(){
        this.users = new HashMap<String, User>();
        try {
            // Guardar el objeto en el archivo binario
            ObjectInputStream stateFileObj = new ObjectInputStream(new FileInputStream(usersFileNameDB));
            User user;
            while((user = (User)stateFileObj.readObject()) != null){
                this.users.put(user.getNickname(), user);
            }
            stateFileObj.close();
        } catch (Exception e) {
            // Si no existe el archivo
            this.saveUsersInDB();
        }
    }
    private void saveRecipesInDB(){
        File thisFile = new File(recipesFileNameDB);
        try {
            // Guardar el objeto en el archivo binario
            ObjectOutputStream stateFileObj = new ObjectOutputStream(new FileOutputStream(thisFile));
            for (Recipe recip : this.recipes) {
                stateFileObj.writeObject(recip);
            }
            stateFileObj.close();
        } catch (Exception e) {
            // Si no se puede crear el archivo
            System.err.println(e.getMessage());
        }
    }
    private void loadRecipesFromDB(){
        this.recipes = new ArrayList<Recipe>();
        try {
            // Guardar el objeto en el archivo binario
            ObjectInputStream stateFileObj = new ObjectInputStream(new FileInputStream(recipesFileNameDB));
            Recipe recip;
            while((recip = (Recipe)stateFileObj.readObject()) != null){
                this.recipes.add(recip);
            }
            stateFileObj.close();
        } catch (Exception e) {
            // Si no encuantra el archivo
            this.saveRecipesInDB();
        }
    }
    private void saveIngredientsInDB(){
        File thisFile = new File(ingredientsFileNameDB);
        try {
            // Guardar el objeto en el archivo binario
            ObjectOutputStream stateFileObj = new ObjectOutputStream(new FileOutputStream(thisFile));
            for (Ingredient ingred : ingredients.values()) {
                stateFileObj.writeObject(ingred);
            }
            stateFileObj.close();
        } catch (Exception e) {
            // Si no se puede crear el archivo
            System.err.println(e.getMessage());
        }
    }
    private void loadIngredientsFromDB(){
        ingredients = new HashMap<String, Ingredient>();
        try {
            // Guardar el objeto en el archivo binario
            ObjectInputStream stateFileObj = new ObjectInputStream(new FileInputStream(ingredientsFileNameDB));
            Ingredient ingred;
            while((ingred = (Ingredient)stateFileObj.readObject()) != null){
                this.ingredients.put(ingred.getName(), ingred);
            }
            stateFileObj.close();
        } catch (Exception e) {
            // Si no encuentra el archivo
            this.saveIngredientsInDB();
        }
    }
}
