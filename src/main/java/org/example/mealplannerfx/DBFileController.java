package org.example.mealplannerfx;

import java.io.*;
import java.util.*;

public class DBFileController extends DBBoundaries {
    private final String usersFileNameDB = "fileData/fileDataBase/usersInfo_DB.usersInfo";
    private final String recipesFileNameDB = "fileData/fileDataBase/recipesInfo_DB.recipesInfo";
    private final String ingredientsFileNameDB = "fileData/fileDataBase/ingredientsInfo_DB.ingredientsInfo";
    public DBFileController(){
        super();
        loadDataFromDB();
        //super.loadIngredientsFromOriginalDB();
    }
    @Override
    public void saveUsersInDB(){
        File thisFile = new File(usersFileNameDB);
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
    public void loadUsersFromDB(){
        try {
            // Guardar el objeto en el archivo binario
            ObjectInputStream stateFileObj = new ObjectInputStream(new FileInputStream(usersFileNameDB));
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
        File thisFile = new File(recipesFileNameDB);
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
            ObjectInputStream stateFileObj = new ObjectInputStream(new FileInputStream(recipesFileNameDB));
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
        File thisFile = new File(ingredientsFileNameDB);
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
            ObjectInputStream stateFileObj = new ObjectInputStream(new FileInputStream(ingredientsFileNameDB));
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
}
