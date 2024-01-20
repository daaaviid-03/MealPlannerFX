package org.example.mealplannerfx.dao.fs;

import org.example.mealplannerfx.control.DBController;
import org.example.mealplannerfx.entity.DayData;
import org.example.mealplannerfx.entity.Ingredient;
import org.example.mealplannerfx.entity.Recipe;
import org.example.mealplannerfx.entity.User;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class DBFileController extends DBController {
    private final static String USERS_FILE_NAME_DB = "fileData/fileDataBase/usersInfo_DB.usersInfo";
    private final static String RECIPES_FILE_NAME_DB = "fileData/fileDataBase/recipesInfo_DB.recipesInfo";
    private final static String INGREDIENTS_FILE_NAME_DB = "fileData/fileDataBase/ingredientsInfo_DB.ingredientsInfo";
    private final static String DAY_DATA_FILE_NAME_DB = "fileData/fileDataBase/dayDataFromUserInfo_DB.dayDataInfo";
    public DBFileController(){
        super();
        loadDataFromDB();
        super.loadIngredientsFromOriginalDB(true);
    }
    @Override
    public void saveUsersInDB(){
        File thisFile = new File(USERS_FILE_NAME_DB);
        try {
            // Guardar el objeto en el archivo binario
            ObjectOutputStream stateFileObj = new ObjectOutputStream(new FileOutputStream(thisFile));
            for (User user : super.getUsersValues()) {
                stateFileObj.writeObject(user);
            }
            stateFileObj.close();
        } catch (Exception e) {
            // Si no se puede crear el archivo
            System.err.println(e.getMessage());
        }
    }
    @Override
    public void deleteAllDayDataFromUserFromTheDB(String nick){
        Set<DayData> allDayData = new HashSet<>();
        try {
            // Obtain all data from the db
            ObjectInputStream stateFileObj = new ObjectInputStream(new FileInputStream(DAY_DATA_FILE_NAME_DB));
            DayData dayData;
            while((dayData = (DayData)stateFileObj.readObject()) != null){
                if (!dayData.getUserNickname().equals(nick)) {
                    allDayData.add(dayData);
                }
            }
            stateFileObj.close();

            // Save data in the db

            FileOutputStream thisFile = new FileOutputStream(DAY_DATA_FILE_NAME_DB);
            // Guardar el objeto en el archivo binario
            ObjectOutputStream outputFileObj = new ObjectOutputStream(new BufferedOutputStream(thisFile));
            for (DayData thisDayData : allDayData){
                outputFileObj.writeObject(thisDayData);
            }
            outputFileObj.close();

        } catch (Exception e) {
            // Si no existe el archivo
            File thisFile = new File(DAY_DATA_FILE_NAME_DB);
        }
    }



    @Override
    public void saveDayDataFromUserInDB(){
        deleteAllDayDataFromUserFromTheDB(getThisUser().getNickname());
        try {
            FileOutputStream thisFile = new FileOutputStream(DAY_DATA_FILE_NAME_DB, true);
            // Guardar el objeto en el archivo binario
            ObjectOutputStream stateFileObj = new ObjectOutputStream(new BufferedOutputStream(thisFile));
            for (DayData dayData : getUserDayDataValues()){
                stateFileObj.writeObject(dayData);
            }
            stateFileObj.close();
        } catch (Exception e) {
            // Si no se puede crear el archivo
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void loadUsersFromDB(){
        try {
            // Guardar el objeto en el archivo binario
            ObjectInputStream stateFileObj = new ObjectInputStream(new FileInputStream(USERS_FILE_NAME_DB));
            User user;
            while((user = (User)stateFileObj.readObject()) != null){
                super.addUser(user);
            }
            stateFileObj.close();
        } catch (Exception e) {
            // Si no existe el archivo
            this.saveUsersInDB();
        }
    }
    @Override
    public void saveRecipesInDB(){
        File thisFile = new File(RECIPES_FILE_NAME_DB);
        try {
            // Guardar el objeto en el archivo binario
            ObjectOutputStream stateFileObj = new ObjectOutputStream(new FileOutputStream(thisFile));
            for (Recipe recip : super.getRecipesValues()) {
                stateFileObj.writeObject(recip);
            }
            stateFileObj.close();
        } catch (Exception e) {
            // Si no se puede crear el archivo
            System.err.println(e.getMessage());
        }
    }
    @Override
    public void loadRecipesFromDB(){
        try {
            // Guardar el objeto en el archivo binario
            ObjectInputStream stateFileObj = new ObjectInputStream(new FileInputStream(RECIPES_FILE_NAME_DB));
            Recipe recip;
            while((recip = (Recipe)stateFileObj.readObject()) != null){
                super.addRecipe(recip);
            }
            stateFileObj.close();
        } catch (Exception e) {
            // Si no encuantra el archivo
            this.saveRecipesInDB();
        }
    }
    @Override
    public void saveIngredientsInDB(){
        File thisFile = new File(INGREDIENTS_FILE_NAME_DB);
        try {
            // Guardar el objeto en el archivo binario
            ObjectOutputStream stateFileObj = new ObjectOutputStream(new FileOutputStream(thisFile));
            for (Ingredient ingred : super.getIngredientsValues()) {
                stateFileObj.writeObject(ingred);
            }
            stateFileObj.close();
        } catch (Exception e) {
            // Si no se puede crear el archivo
            System.err.println(e.getMessage());
        }
    }
    @Override
    public void loadIngredientsFromDB(){
        try {
            // Guardar el objeto en el archivo binario
            ObjectInputStream stateFileObj = new ObjectInputStream(new FileInputStream(INGREDIENTS_FILE_NAME_DB));
            Ingredient ingred;
            while((ingred = (Ingredient)stateFileObj.readObject()) != null){
                super.addIngredient(ingred);
            }
            stateFileObj.close();
        } catch (Exception e) {
            // Si no encuentra el archivo
            this.saveIngredientsInDB();
        }
    }

    @Override
    public void loadDayDataFromUserFromDB(String nick) {
        try {
            // Guardar el objeto en el archivo binario
            ObjectInputStream stateFileObj = new ObjectInputStream(new FileInputStream(DAY_DATA_FILE_NAME_DB));
            DayData dayData;
            while((dayData = (DayData)stateFileObj.readObject()) != null){
                if (dayData.getUserNickname().equals(nick)) {
                    addUserDayData(dayData);
                }
            }
            stateFileObj.close();
        } catch (Exception e) {
            // Si no existe el archivo
            this.saveDayDataFromUserInDB();
        }
    }
}
